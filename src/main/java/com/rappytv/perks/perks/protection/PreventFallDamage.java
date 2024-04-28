package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PreventFallDamage extends Perk {

    public PreventFallDamage() {
        super("nofalldamage");
    }

    @Override
    public Material getMaterial() {
        return Material.FEATHER;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
