package com.rappytv.perks.perks.misc;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class NightVision extends PotionEffectPerk {

    public NightVision() {
        super("nightvision");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.NIGHT_VISION;
    }

    @Override
    public int getEffectAplifier() {
        return 0;
    }

    @Override
    public Material getMaterial() {
        return Material.CARVED_PUMPKIN;
    }
}
