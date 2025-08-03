package com.manus.vipshop.tasks;

import com.manus.vipshop.VIPShop;
import org.bukkit.scheduler.BukkitRunnable;

public class VIPExpiryTask extends BukkitRunnable {

    private final VIPShop plugin;

    public VIPExpiryTask(VIPShop plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getVIPManager().checkExpiredVIPs();
    }
}

