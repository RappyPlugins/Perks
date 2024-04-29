package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class StrengthBoost extends PotionEffectPerk {

    public StrengthBoost() {
        super("strengthboost");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.INCREASE_DAMAGE;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_SWORD;
    }
}
