package com.rappytv.perks.perks.misc;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KeepExperience extends Perk {

    public KeepExperience() {
        super("keepxp");
    }

    @Override
    public Material getMaterial() {
        return Material.EXPERIENCE_BOTTLE;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
