package com.rappytv.perks.perks.immunity;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EndermanImmunity extends Perk {

    public EndermanImmunity() {
        super("endermanimmunity");
    }

    @Override
    public Material getMaterial() {
        return Material.ENDERMAN_SPAWN_EGG;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
