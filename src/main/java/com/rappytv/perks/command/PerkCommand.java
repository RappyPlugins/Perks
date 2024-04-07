package com.rappytv.perks.command;

import com.rappytv.perks.Perks;
import com.rappytv.perks.util.Util;
import com.rappytv.rylib.RyLib;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PerkCommand extends com.rappytv.rylib.util.Command<Perks> {

    public PerkCommand(String name, Perks plugin) {
        super(name, plugin);
    }

    @Override
    public void execute(CommandSender sender, String prefix, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(RyLib.get().i18n().translate("onlyPlayer"));
            return;
        }

        if(!player.hasPermission("perks.command")) {
            player.sendMessage(RyLib.get().i18n().translate("noPermission"));
            return;
        }

        Util.openPerkGUI(plugin, player, 0);
    }

    @Override
    public List<String> complete(CommandSender sender, String prefix, String[] args) {
        return null;
    }
}
