package com.rappytv.perks.command;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.util.Util;
import com.rappytv.rylib.RyLib;
import com.rappytv.rylib.util.I18n;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PerkCommand extends com.rappytv.rylib.util.Command<PerkPlugin> {

    public PerkCommand(String name, PerkPlugin plugin) {
        super(name, plugin);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(args.length == 0) {
            if(!(sender instanceof Player player)) {
                sender.sendMessage(RyLib.get().i18n().translate("onlyPlayer"));
                return;
            }

            if(!player.hasPermission("perks.menu")) {
                player.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }

            Util.openPerkGUI(plugin, player, 0);
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
        if(args[0].equalsIgnoreCase("add")) {
            if(!sender.hasPermission("perks.manage.add")) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }
            if(args.length < 3) {
                sender.sendMessage(plugin.i18n().translate("enterPerk"));
                return;
            }

            Optional<Perk> optionalPerk = Perk.perks.stream().filter((p -> p.getId().equalsIgnoreCase(args[2]))).findFirst();
            if(optionalPerk.isEmpty()) {
                sender.sendMessage(RyLib.get().i18n().translate("perkNotFound"));
                return;
            }
            optionalPerk.get().unlockFor(target);
            sender.sendMessage(plugin.i18n().translate(
                    "updatedPerks",
                    new I18n.Argument("player", target.getName())
            ));
        } else if(args[0].equalsIgnoreCase("remove")) {
            if(!sender.hasPermission("perks.manage.remove")) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }
            if(args.length < 3) {
                sender.sendMessage(plugin.i18n().translate("enterPerk"));
                return;
            }

            Optional<Perk> optionalPerk = Perk.perks.stream().filter((p -> p.getId().equalsIgnoreCase(args[2]))).findFirst();
            if(optionalPerk.isEmpty()) {
                sender.sendMessage(RyLib.get().i18n().translate("perkNotFound"));
                return;
            }
            optionalPerk.get().lockFor(target);
            sender.sendMessage(plugin.i18n().translate(
                    "updatedPerks",
                    new I18n.Argument("player", target.getName())
            ));
        } else if(args[0].equalsIgnoreCase("clear")) {
            if(!sender.hasPermission("perks.manage.clear")) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }

            List<Perk> perks = Perk.perks.stream().filter((p) -> p.isPerkUnlocked(target)).toList();
            for(Perk perk : perks)
                perk.lockFor(target);
            sender.sendMessage(plugin.i18n().translate(
                    "updatedPerks",
                    new I18n.Argument("player", target.getName())
            ));
        } else if(args[0].equalsIgnoreCase("enable")) {
            if(!sender.hasPermission("perks.manage.enable")) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }
            if(args.length < 3) {
                sender.sendMessage(plugin.i18n().translate("enterPerk"));
                return;
            }

            Optional<Perk> optionalPerk = Perk.perks.stream().filter((p -> p.getId().equalsIgnoreCase(args[2]))).findFirst();
            if(optionalPerk.isEmpty()) {
                sender.sendMessage(RyLib.get().i18n().translate("perkNotFound"));
                return;
            }
            optionalPerk.get().addTo(target);
            sender.sendMessage(plugin.i18n().translate(
                    "updatedPerks",
                    new I18n.Argument("player", target.getName())
            ));
        } else if(args[0].equalsIgnoreCase("disable")) {
            if(!sender.hasPermission("perks.manage.disable")) {
                sender.sendMessage(RyLib.get().i18n().translate("noPermission"));
                return;
            }
            if(args.length < 3) {
                sender.sendMessage(plugin.i18n().translate("enterPerk"));
                return;
            }

            Optional<Perk> optionalPerk = Perk.perks.stream().filter((p -> p.getId().equalsIgnoreCase(args[2]))).findFirst();
            if(optionalPerk.isEmpty()) {
                sender.sendMessage(RyLib.get().i18n().translate("perkNotFound"));
                return;
            }
            optionalPerk.get().removeFrom(target);
            sender.sendMessage(plugin.i18n().translate(
                    "updatedPerks",
                    new I18n.Argument("player", target.getName())
            ));
        } else {
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
                for(Perk perk : Perk.perks) {
                    if(!data.getActivePerks().contains(perk.getId()))
                        list.add(perk.getId());
                }
            } else if(remove || enable || disable) {
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
