package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ExperienceBoost extends Perk {

    public ExperienceBoost() {
        super("xpboost");
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
