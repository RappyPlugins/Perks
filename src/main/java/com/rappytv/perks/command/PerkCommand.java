package com.rappytv.perks.command;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.util.Util;
import com.rappytv.rylib.RyLib;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PerkCommand extends com.rappytv.rylib.util.Command<PerkPlugin> {

    public PerkCommand(String name, PerkPlugin plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(RyLib.get().i18n().translate("onlyPlayer"));
            return;
        }

        if(!player.hasPermission("perks.menu")) {
            player.sendMessage(RyLib.get().i18n().translate("noPermission"));
            return;
        }

        Util.openPerkGUI(plugin, player, 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1) {
            if(sender.hasPermission("perks.manage.add")) list.add("add");
            if(sender.hasPermission("perks.manage.remove")) {
                list.add("remove");
                list.add("clear");
            }
            return tab(args[0], list);
        } else if(args.length == 2) {
            boolean add = sender.hasPermission("perks.manage.add") && args[0].equalsIgnoreCase("add");
            boolean remove = sender.hasPermission("perks.manage.remove") && args[0].equalsIgnoreCase("remove");
            boolean clear = sender.hasPermission("perks.manage.remove") && args[0].equalsIgnoreCase("clear");

            if(add || remove || clear) return players;
        } else if(args.length == 3) {
            boolean add = sender.hasPermission("perks.manage.add") && args[0].equalsIgnoreCase("add");
            boolean remove = sender.hasPermission("perks.manage.remove") && args[0].equalsIgnoreCase("remove");
            PlayerData data = PlayerData.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
            if(data == null) return null;

            if(add) {
                for(Perk perk : Perk.perks) {
                    if(!data.getActivePerks().contains(perk.getId()))
                        list.add(perk.getId());
                }
            } else if(remove) {
                for(Perk perk : Perk.perks) {
                    if(data.getActivePerks().contains(perk.getId()))
                        list.add(perk.getId());
                }
            }
            return tab(args[2], list);
        }
        return null;
    }
}
