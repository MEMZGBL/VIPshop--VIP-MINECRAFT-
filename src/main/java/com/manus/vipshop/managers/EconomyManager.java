package com.manus.vipshop.managers;

import com.manus.vipshop.VIPShop;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

    private final VIPShop plugin;
    private Economy econ = null;
    private Object playerPointsAPI;

    public EconomyManager(VIPShop plugin) {
        this.plugin = plugin;
        setupEconomy();
        setupPlayerPoints();
    }

    private void setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    private void setupPlayerPoints() {
        try {
            Class<?> playerPointsClass = Class.forName("org.black_ixx.playerpoints.PlayerPoints");
            Object playerPointsInstance = playerPointsClass.getMethod("getInstance").invoke(null);
            playerPointsAPI = playerPointsClass.getMethod("getAPI").invoke(playerPointsInstance);
        } catch (Exception e) {
            plugin.getLogger().warning("PlayerPoints not found or API could not be accessed. Point purchases will be disabled.");
            playerPointsAPI = null;
        }
    }

    public boolean hasPlayerPoints(OfflinePlayer player, double amount) {
        if (playerPointsAPI == null) return false;
        try {
            // PlayerPoints API look method returns int, so cast amount to int
            return (int) playerPointsAPI.getClass().getMethod("look", String.class).invoke(playerPointsAPI, player.getName()) >= amount;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean takePlayerPoints(OfflinePlayer player, double amount) {
        if (playerPointsAPI == null) return false;
        try {
            // PlayerPoints API take method expects int, so cast amount to int
            return (boolean) playerPointsAPI.getClass().getMethod("take", String.class, int.class).invoke(playerPointsAPI, player.getName(), (int) amount);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasCoins(OfflinePlayer player, double amount) {
        return econ.has(player, amount);
    }

    public boolean takeCoins(OfflinePlayer player, double amount) {
        return econ.withdrawPlayer(player, amount).transactionSuccess();
    }
}


