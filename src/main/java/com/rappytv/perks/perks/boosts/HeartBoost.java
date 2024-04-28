package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class HeartBoost extends Perk {

    public HeartBoost() {
        super("morehearts");
    }

    @Override
    public Material getMaterial() {
        return Material.ENCHANTED_GOLDEN_APPLE;
    }

    @Override
    public void onEnable(Player player) {
        player.setMaxHealth(40);
    }

    @Override
    public void onDisable(Player player) {
        player.setMaxHealth(20);
    }
}
