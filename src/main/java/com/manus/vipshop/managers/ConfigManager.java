package com.manus.vipshop.managers;

import com.manus.vipshop.VIPShop;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    
    private final VIPShop plugin;
    private FileConfiguration config;
    
    public ConfigManager(VIPShop plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
    
    public void saveConfig() {
        plugin.saveConfig();
    }
    
    public int getCoinPrice(String duration) {
        return config.getInt("coin-prices." + duration, 0);
    }
    
    public int getPointsPrice(String duration) {
        return config.getInt("points-prices." + duration, 0);
    }
    
    public String getVIPGroup() {
        return config.getString("vip-group", "vip1");
    }
    
    public String getDefaultGroup() {
        return config.getString("default-group", "default");
    }
    
    public String getGUITitle(String type) {
        return config.getString("gui." + type + "-title", "VIP商店");
    }
    
    public String getMessage(String key) {
        return config.getString("messages." + key, "消息未找到: " + key);
    }
    
    public String getStorageType() {
        return config.getString("storage.type", "yaml");
    }
    
    public String getStorageFile() {
        return config.getString("storage.file", "vip_data.yml");
    }
}

