package com.rappytv.perks.command;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.perks.PerkManager;
import com.rappytv.perks.util.Util;
import com.rappytv.rylib.RyLib;
import com.rappytv.rylib.util.I18n;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PerkCommand extends com.rappytv.rylib.util.Command<PerkPlugin> {

    public PerkCommand(String name, PerkPlugin plugin) {
        super(name, plugin);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(args.length == 0) {
            openMenu(sender);
            return;
        }
        if(args.length < 2) {
            sender.sendMessage(RyLib.get().i18n().translate("enterPlayerName"));
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if(!target.hasPlayedBefore()) {
            sender.sendMessage(RyLib.get().i18n().translate("playerNotFound"));
            return;
        }
        if(args[0].equalsIgnoreCase("clear")) {
            if(!sender.hasPermission("perks.manage.clear")) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }

            List<Perk> perks = new ArrayList<>();
            for(Perk perk : PerkManager.getPerks())
                if(perk.isPerkUnlocked(target)) perks.add(perk);

            if(perks.isEmpty()) {
                sender.sendMessage(plugin.i18n().translate(
                        "perksEmpty",
                        new I18n.Argument("player", target.getName())
                ));
            }
            for(Perk perk : perks)
                perk.lockFor(target, true);
            sender.sendMessage(plugin.i18n().translate(
                    "updatedPerks",
                    new I18n.Argument("player", target.getName())
            ));
        } else {
            if(!sender.hasPermission("perks.manage." + args[0])) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }
            if(args.length < 3) {
                sender.sendMessage(plugin.i18n().translate("enterPerk"));
                return;
            }
            Perk perk = null;
            for(Perk p : PerkManager.getPerks()) {
                if(p.getId().equalsIgnoreCase(args[2])) {
                    perk = p;
                    break;
                }
            }
            if(perk == null) {
                sender.sendMessage(RyLib.get().i18n().translate("perkNotFound"));
                return;
            }
            if(args[0].equalsIgnoreCase("add")) {
                if(perk.isPerkUnlocked(target)) {
                    sender.sendMessage(plugin.i18n().translate(
                            "perkAlreadyUnlocked",
                            new I18n.Argument("player", target.getName())
                    ));
                    return;
                }
                perk.unlockFor(target, true);
                sender.sendMessage(plugin.i18n().translate(
                        "updatedPerks",
                        new I18n.Argument("player", target.getName())
                ));
            } else if(args[0].equalsIgnoreCase("remove")) {
                if(!perk.isPerkUnlocked(target)) {
                    sender.sendMessage(plugin.i18n().translate(
                            "perkNotUnlocked",
                            new I18n.Argument("player", target.getName())
                    ));
                    return;
                }
                perk.lockFor(target, true);
                sender.sendMessage(plugin.i18n().translate(
                        "updatedPerks",
                        new I18n.Argument("player", target.getName())
                ));
            } else if(args[0].equalsIgnoreCase("enable")) {
                if(perk.isPerkActive(target)) {
                    sender.sendMessage(plugin.i18n().translate(
                            "perkAlreadyActive",
                            new I18n.Argument("player", target.getName())
                    ));
                    return;
                }
                perk.addTo(target, true);
                sender.sendMessage(plugin.i18n().translate(
                        "updatedPerks",
                        new I18n.Argument("player", target.getName())
                ));
            } else if(args[0].equalsIgnoreCase("disable")) {
                if(!perk.isPerkActive(target)) {
                    sender.sendMessage(plugin.i18n().translate(
                            "perkNotActive",
                            new I18n.Argument("player", target.getName())
                    ));
                    return;
                }
                perk.removeFrom(target, true);
                sender.sendMessage(plugin.i18n().translate(
                        "updatedPerks",
                        new I18n.Argument("player", target.getName())
                ));
            } else openMenu(sender);
        }
    }

    private void openMenu(CommandSender sender) {
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
            if(sender.hasPermission("perks.manage.enable")) list.add("enable");
            if(sender.hasPermission("perks.manage.disable")) list.add("disable");
            return tab(args[0], list);
        } else if(args.length == 2) {
            boolean add = sender.hasPermission("perks.manage.add") && args[0].equalsIgnoreCase("add");
            boolean remove = sender.hasPermission("perks.manage.remove") && args[0].equalsIgnoreCase("remove");
            boolean clear = sender.hasPermission("perks.manage.remove") && args[0].equalsIgnoreCase("clear");
            boolean enable = sender.hasPermission("perks.manage.enable") && args[0].equalsIgnoreCase("enable");
            boolean disable = sender.hasPermission("perks.manage.disable") && args[0].equalsIgnoreCase("disable");

            if(add || remove || clear || enable || disable) return players;
        } else if(args.length == 3) {
            boolean add = sender.hasPermission("perks.manage.add") && args[0].equalsIgnoreCase("add");
            boolean remove = sender.hasPermission("perks.manage.remove") && args[0].equalsIgnoreCase("remove");
            boolean enable = sender.hasPermission("perks.manage.enable") && args[0].equalsIgnoreCase("enable");
            boolean disable = sender.hasPermission("perks.manage.disable") && args[0].equalsIgnoreCase("disable");
            PlayerData data = PlayerData.get(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
            if(data == null) return null;

            if(add) {
                for(Perk perk : PerkManager.getPerks()) {
                    if(!data.getActivePerks().contains(perk.getId()))
                        list.add(perk.getId());
                }
            } else if(remove || enable || disable) {
                for(Perk perk : PerkManager.getPerks()) {
                    if(data.getActivePerks().contains(perk.getId()))
                        list.add(perk.getId());
                }
            }
            return tab(args[2], list);
        }
        return null;
    }
}
