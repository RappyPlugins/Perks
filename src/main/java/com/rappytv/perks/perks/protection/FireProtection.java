package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class FireProtection extends PotionEffectPerk {

    public FireProtection() {
        super("fireprotection");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.FIRE_RESISTANCE;
    }

    @Override
    public int getEffectAplifier() {
        return 0;
    }

    @Override
    public Material getMaterial() {
        return Material.BLAZE_POWDER;
    }
}
