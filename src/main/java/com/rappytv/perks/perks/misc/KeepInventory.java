package com.rappytv.perks.perks.misc;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KeepInventory extends Perk {

    public KeepInventory() {
        super("keepinventory");
    }

    @Override
    public Material getMaterial() {
        return Material.CHEST;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
