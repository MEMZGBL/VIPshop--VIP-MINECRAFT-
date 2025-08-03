package com.manus.vipshop.placeholders;

import com.manus.vipshop.VIPShop;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VIPPlaceholderExpansion extends PlaceholderExpansion {

    private final VIPShop plugin;

    public VIPPlaceholderExpansion(VIPShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "vip";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // 这样即使PlaceholderAPI重载，扩展也会保持注册状态
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        // %vip_time% - 显示VIP到期时间
        if (params.equalsIgnoreCase("time")) {
            if (!plugin.getVIPManager().isVIP(player.getUniqueId())) {
                return "无VIP";
            }

            long expiryTime = plugin.getVIPManager().getVIPExpiry(player.getUniqueId());
            
            // 永久VIP
            if (expiryTime == -1) {
                return "forever";
            }

            // 检查是否已过期
            if (System.currentTimeMillis() > expiryTime) {
                return "已过期";
            }

            // 返回格式化的到期时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date(expiryTime));
        }

        // %vip_remaining% - 显示剩余时间
        if (params.equalsIgnoreCase("remaining")) {
            if (!plugin.getVIPManager().isVIP(player.getUniqueId())) {
                return "无VIP";
            }

            long expiryTime = plugin.getVIPManager().getVIPExpiry(player.getUniqueId());
            
            // 永久VIP
            if (expiryTime == -1) {
                return "forever";
            }

            long currentTime = System.currentTimeMillis();
            
            // 检查是否已过期
            if (currentTime > expiryTime) {
                return "已过期";
            }

            // 计算剩余时间
            long remainingTime = expiryTime - currentTime;
            return formatRemainingTime(remainingTime);
        }

        // %vip_status% - 显示VIP状态
        if (params.equalsIgnoreCase("status")) {
            if (!plugin.getVIPManager().isVIP(player.getUniqueId())) {
                return "非VIP";
            }

            long expiryTime = plugin.getVIPManager().getVIPExpiry(player.getUniqueId());
            
            // 永久VIP
            if (expiryTime == -1) {
                return "永久VIP";
            }

            // 检查是否已过期
            if (System.currentTimeMillis() > expiryTime) {
                return "VIP已过期";
            }

            return "VIP用户";
        }

        return null; // 占位符不存在
    }

    private String formatRemainingTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + "天" + (hours % 24) + "小时";
        } else if (hours > 0) {
            return hours + "小时" + (minutes % 60) + "分钟";
        } else if (minutes > 0) {
            return minutes + "分钟";
        } else {
            return seconds + "秒";
        }
    }
}

