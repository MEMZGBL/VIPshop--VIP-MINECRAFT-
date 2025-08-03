package com.manus.vipshop.managers;

import com.manus.vipshop.VIPShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GUIManager {

    private final VIPShop plugin;

    public GUIManager(VIPShop plugin) {
        this.plugin = plugin;
    }

    public Inventory createMainMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, plugin.getConfigManager().getGUITitle("main"));

        // 金币购买VIP
        ItemStack coinItem = new ItemStack(Material.GOLD_INGOT);
        ItemMeta coinMeta = coinItem.getItemMeta();
        coinMeta.setDisplayName("§e金币购买VIP");
        coinMeta.setLore(Arrays.asList("§7点击进入金币购买界面"));
        coinItem.setItemMeta(coinMeta);
        inv.setItem(2, coinItem);

        // 点券购买VIP
        ItemStack pointsItem = new ItemStack(Material.DIAMOND);
        ItemMeta pointsMeta = pointsItem.getItemMeta();
        pointsMeta.setDisplayName("§b点券购买VIP");
        pointsMeta.setLore(Arrays.asList("§7点击进入点券购买界面"));
        pointsItem.setItemMeta(pointsMeta);
        inv.setItem(6, pointsItem);

        return inv;
    }

    public Inventory createCoinShopMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, plugin.getConfigManager().getGUITitle("coin-shop"));

        // 购买一星期
        ItemStack weekItem = new ItemStack(Material.CLOCK);
        ItemMeta weekMeta = weekItem.getItemMeta();
        weekMeta.setDisplayName("§a购买一星期VIP");
        weekMeta.setLore(Arrays.asList("§7价格: " + plugin.getConfigManager().getCoinPrice("week") + " 金币", "§7点击购买"));
        weekItem.setItemMeta(weekMeta);
        inv.setItem(2, weekItem);

        // 购买一个月
        ItemStack monthItem = new ItemStack(Material.CHEST);
        ItemMeta monthMeta = monthItem.getItemMeta();
        monthMeta.setDisplayName("§a购买一个月VIP");
        monthMeta.setLore(Arrays.asList("§7价格: " + plugin.getConfigManager().getCoinPrice("month") + " 金币", "§7点击购买"));
        monthItem.setItemMeta(monthMeta);
        inv.setItem(4, monthItem);

        // 购买永久
        ItemStack permanentItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta permanentMeta = permanentItem.getItemMeta();
        permanentMeta.setDisplayName("§a购买永久VIP");
        permanentMeta.setLore(Arrays.asList("§7价格: " + plugin.getConfigManager().getCoinPrice("permanent") + " 金币", "§7点击购买"));
        permanentItem.setItemMeta(permanentMeta);
        inv.setItem(6, permanentItem);

        return inv;
    }

    public Inventory createPointsShopMenu() {
        Inventory inv = Bukkit.createInventory(null, 9, plugin.getConfigManager().getGUITitle("points-shop"));

        // 购买一星期
        ItemStack weekItem = new ItemStack(Material.CLOCK);
        ItemMeta weekMeta = weekItem.getItemMeta();
        weekMeta.setDisplayName("§b购买一星期VIP");
        weekMeta.setLore(Arrays.asList("§7价格: " + plugin.getConfigManager().getPointsPrice("week") + " 点券", "§7点击购买"));
        weekItem.setItemMeta(weekMeta);
        inv.setItem(2, weekItem);

        // 购买一个月
        ItemStack monthItem = new ItemStack(Material.CHEST);
        ItemMeta monthMeta = monthItem.getItemMeta();
        monthMeta.setDisplayName("§b购买一个月VIP");
        monthMeta.setLore(Arrays.asList("§7价格: " + plugin.getConfigManager().getPointsPrice("month") + " 点券", "§7点击购买"));
        monthItem.setItemMeta(monthMeta);
        inv.setItem(4, monthItem);

        // 购买永久
        ItemStack permanentItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta permanentMeta = permanentItem.getItemMeta();
        permanentMeta.setDisplayName("§b购买永久VIP");
        permanentMeta.setLore(Arrays.asList("§7价格: " + plugin.getConfigManager().getPointsPrice("permanent") + " 点券", "§7点击购买"));
        permanentItem.setItemMeta(permanentMeta);
        inv.setItem(6, permanentItem);

        return inv;
    }
}

