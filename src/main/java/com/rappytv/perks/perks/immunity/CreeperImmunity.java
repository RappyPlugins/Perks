package com.rappytv.perks.perks.immunity;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CreeperImmunity extends Perk {

    public CreeperImmunity() {
        super("creeperimmunity");
    }

    @Override
    public Material getMaterial() {
        return Material.CREEPER_SPAWN_EGG;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
