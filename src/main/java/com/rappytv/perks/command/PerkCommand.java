package com.rappytv.perks.command;

import com.rappytv.perks.Perks;
import com.rappytv.perks.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PerkCommand implements CommandExecutor, TabExecutor {

    private final Perks plugin;

    public PerkCommand(Perks plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(Perks.prefix + Util.message("console"));
            return false;
        }

        if(!player.hasPermission("perks.command")) {
            player.sendMessage(Perks.prefix + Util.message("noPermission", "perks.command"));
            return false;
        }

        Util.openPerkGUI(plugin, player, 0);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
