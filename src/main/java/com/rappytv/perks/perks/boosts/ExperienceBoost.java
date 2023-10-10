package com.rappytv.perks.perks.boosts;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExperienceBoost extends Perk {

    public ExperienceBoost() {
        super("xpboost", "Mehr XP");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.EXPERIENCE_BOTTLE);
    }
}
