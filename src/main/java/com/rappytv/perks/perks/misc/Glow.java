package com.rappytv.perks.perks.misc;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class Glow extends PotionEffectPerk {

    public Glow() {
        super("glow");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.GLOWING;
    }

    @Override
    public int getEffectAplifier() {
        return 0;
    }

    @Override
    public Material getMaterial() {
        return Material.SPECTRAL_ARROW;
    }
}
