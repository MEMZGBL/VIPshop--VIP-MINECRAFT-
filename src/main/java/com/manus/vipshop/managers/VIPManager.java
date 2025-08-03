package com.manus.vipshop.managers;

import com.manus.vipshop.VIPShop;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class VIPManager {

    private final VIPShop plugin;
    private File dataFile;
    private FileConfiguration dataConfig;

    public VIPManager(VIPShop plugin) {
        this.plugin = plugin;
        setupDataFile();
    }

    private void setupDataFile() {
        dataFile = new File(plugin.getDataFolder(), plugin.getConfigManager().getStorageFile());
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("无法创建数据文件: " + e.getMessage());
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("无法保存数据文件: " + e.getMessage());
        }
    }

    public void purchaseVIP(Player player, String duration, String paymentType) {
        int price;
        String priceType;

        if (paymentType.equals("coin")) {
            price = plugin.getConfigManager().getCoinPrice(duration);
            priceType = "金币";
        } else {
            price = plugin.getConfigManager().getPointsPrice(duration);
            priceType = "点券";
        }

        // 检查余额
        boolean hasEnough;
        if (paymentType.equals("coin")) {
            hasEnough = plugin.getEconomyManager().hasCoins(player, price);
        } else {
            hasEnough = plugin.getEconomyManager().hasPlayerPoints(player, price);
        }

        if (!hasEnough) {
            String message = paymentType.equals("coin") ? 
                plugin.getConfigManager().getMessage("insufficient-coins") :
                plugin.getConfigManager().getMessage("insufficient-points");
            player.sendMessage(message.replace("&", "§").replace("{amount}", String.valueOf(price)));
            player.closeInventory();
            return;
        }

        // 扣除金币/点券
        boolean success;
        if (paymentType.equals("coin")) {
            success = plugin.getEconomyManager().takeCoins(player, price);
        } else {
            success = plugin.getEconomyManager().takePlayerPoints(player, price);
        }

        if (!success) {
            player.sendMessage(plugin.getConfigManager().getMessage("purchase-failed").replace("&", "§"));
            player.closeInventory();
            return;
        }

        // 计算到期时间
        long expiryTime = calculateExpiryTime(duration);

        // 设置VIP权限
        setVIPGroup(player);

        // 保存VIP数据
        saveVIPData(player.getUniqueId(), expiryTime);

        // 发送成功消息
        String expiryDate = duration.equals("permanent") ? "永久" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(expiryTime));
        String message = plugin.getConfigManager().getMessage("purchase-success")
                .replace("&", "§")
                .replace("{expiry}", expiryDate);
        player.sendMessage(message);
        player.closeInventory();
    }

    private long calculateExpiryTime(String duration) {
        if (duration.equals("permanent")) {
            return -1; // -1 表示永久
        }

        Calendar calendar = Calendar.getInstance();
        switch (duration) {
            case "week":
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case "month":
                calendar.add(Calendar.MONTH, 1);
                break;
        }
        return calendar.getTimeInMillis();
    }

    private void setVIPGroup(Player player) {
        String vipGroup = plugin.getConfigManager().getVIPGroup();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
            "lp user " + player.getName() + " parent set " + vipGroup);
    }

    private void removeVIPGroup(OfflinePlayer player) {
        String defaultGroup = plugin.getConfigManager().getDefaultGroup();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
            "lp user " + player.getName() + " parent set " + defaultGroup);
    }

    private void saveVIPData(UUID playerUUID, long expiryTime) {
        dataConfig.set("vips." + playerUUID.toString() + ".expiry", expiryTime);
        saveData();
    }

    public void checkExpiredVIPs() {
        if (dataConfig.getConfigurationSection("vips") == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        for (String uuidString : dataConfig.getConfigurationSection("vips").getKeys(false)) {
            long expiryTime = dataConfig.getLong("vips." + uuidString + ".expiry");
            
            // 跳过永久VIP
            if (expiryTime == -1) {
                continue;
            }

            if (currentTime > expiryTime) {
                UUID playerUUID = UUID.fromString(uuidString);
                OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
                
                // 移除VIP权限
                removeVIPGroup(player);
                
                // 从数据中移除
                dataConfig.set("vips." + uuidString, null);
                
                // 如果玩家在线，发送消息
                if (player.isOnline()) {
                    ((Player) player).sendMessage(plugin.getConfigManager().getMessage("vip-expired").replace("&", "§"));
                }
                
                plugin.getLogger().info("玩家 " + player.getName() + " 的VIP已过期并被移除");
            }
        }
        saveData();
    }

    public boolean isVIP(UUID playerUUID) {
        if (dataConfig.getConfigurationSection("vips") == null) {
            return false;
        }
        return dataConfig.contains("vips." + playerUUID.toString());
    }

    public long getVIPExpiry(UUID playerUUID) {
        return dataConfig.getLong("vips." + playerUUID.toString() + ".expiry", 0);
    }
}

