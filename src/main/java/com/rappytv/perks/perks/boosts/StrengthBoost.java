package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class StrengthBoost extends PotionEffectPerk {

    public StrengthBoost() {
        super("strengthboost", "Stärke");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.IRON_SWORD);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.INCREASE_DAMAGE;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }
}
