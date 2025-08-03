package com.manus.vipshop.commands;

import com.manus.vipshop.VIPShop;
import com.manus.vipshop.managers.GUIManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetVIPCommand implements CommandExecutor {

    private final VIPShop plugin;
    private final GUIManager guiManager;

    public GetVIPCommand(VIPShop plugin) {
        this.plugin = plugin;
        this.guiManager = new GUIManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c只有玩家才能使用这个命令！");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("vipshop.use")) {
            player.sendMessage(plugin.getConfigManager().getMessage("no-permission").replace("&", "§"));
            return true;
        }

        player.openInventory(guiManager.createMainMenu());
        return true;
    }
}

