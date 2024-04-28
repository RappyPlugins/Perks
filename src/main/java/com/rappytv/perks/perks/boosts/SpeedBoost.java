package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class SpeedBoost extends PotionEffectPerk {

    public SpeedBoost() {
        super("speedboost");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.SPEED;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_BOOTS;
    }
}
