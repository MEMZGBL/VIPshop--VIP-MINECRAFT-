package com.manus.vipshop.listeners;

import com.manus.vipshop.VIPShop;
import com.manus.vipshop.managers.GUIManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final VIPShop plugin;
    private final GUIManager guiManager;

    public GUIListener(VIPShop plugin) {
        this.plugin = plugin;
        this.guiManager = new GUIManager(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        // 检查是否是VIP商店界面
        if (!title.equals(plugin.getConfigManager().getGUITitle("main")) &&
            !title.equals(plugin.getConfigManager().getGUITitle("coin-shop")) &&
            !title.equals(plugin.getConfigManager().getGUITitle("points-shop"))) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }

        String itemName = clickedItem.getItemMeta().getDisplayName();

        // 主菜单点击处理
        if (title.equals(plugin.getConfigManager().getGUITitle("main"))) {
            if (itemName.equals("§e金币购买VIP")) {
                player.openInventory(guiManager.createCoinShopMenu());
            } else if (itemName.equals("§b点券购买VIP")) {
                player.openInventory(guiManager.createPointsShopMenu());
            }
        }
        // 金币商店点击处理
        else if (title.equals(plugin.getConfigManager().getGUITitle("coin-shop"))) {
            if (itemName.equals("§a购买一星期VIP")) {
                plugin.getVIPManager().purchaseVIP(player, "week", "coin");
            } else if (itemName.equals("§a购买一个月VIP")) {
                plugin.getVIPManager().purchaseVIP(player, "month", "coin");
            } else if (itemName.equals("§a购买永久VIP")) {
                plugin.getVIPManager().purchaseVIP(player, "permanent", "coin");
            }
        }
        // 点券商店点击处理
        else if (title.equals(plugin.getConfigManager().getGUITitle("points-shop"))) {
            if (itemName.equals("§b购买一星期VIP")) {
                plugin.getVIPManager().purchaseVIP(player, "week", "points");
            } else if (itemName.equals("§b购买一个月VIP")) {
                plugin.getVIPManager().purchaseVIP(player, "month", "points");
            } else if (itemName.equals("§b购买永久VIP")) {
                plugin.getVIPManager().purchaseVIP(player, "permanent", "points");
            }
        }
    }
}

