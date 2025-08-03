package com.manus.vipshop;

import com.manus.vipshop.commands.GetVIPCommand;
import com.manus.vipshop.listeners.GUIListener;
import com.manus.vipshop.managers.ConfigManager;
import com.manus.vipshop.managers.VIPManager;
import com.manus.vipshop.managers.EconomyManager;
import com.manus.vipshop.tasks.VIPExpiryTask;
import org.bukkit.plugin.java.JavaPlugin;

public class VIPShop extends JavaPlugin {
    
    private static VIPShop instance;
    private ConfigManager configManager;
    private VIPManager vipManager;
    private EconomyManager economyManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // 初始化管理器
        configManager = new ConfigManager(this);
        vipManager = new VIPManager(this);
        economyManager = new EconomyManager(this);
        
        // 检查依赖插件
        if (!setupDependencies()) {
            getLogger().severe("缺少必要的依赖插件！插件将被禁用。");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // 注册命令
        getCommand("getvip").setExecutor(new GetVIPCommand(this));
        
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        
        new VIPExpiryTask(this).runTaskTimer(this, 20L * 60L, 20L * 60L); // 每分钟检查一次
        
        // 注册PlaceholderAPI扩展
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new com.manus.vipshop.placeholders.VIPPlaceholderExpansion(this).register();
        }
        
        getLogger().info("VIPShop插件已启用！");
    }
    
    @Override
    public void onDisable() {
        if (vipManager != null) {
            vipManager.saveData();
        }
        getLogger().info("VIPShop插件已禁用！");
    }
    
    private boolean setupDependencies() {
        // 检查Vault
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("未找到Vault插件！");
            return false;
        }
        
        // 检查PlayerPoints
        if (getServer().getPluginManager().getPlugin("PlayerPoints") == null) {
            getLogger().severe("未找到PlayerPoints插件！");
            return false;
        }
        
        // 检查LuckPerms
        if (getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            getLogger().severe("未找到LuckPerms插件！");
            return false;
        }
        
        return true;
    }
    
    public static VIPShop getInstance() {
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public VIPManager getVIPManager() {
        return vipManager;
    }
    
    public EconomyManager getEconomyManager() {
        return economyManager;
    }
}

