package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class Haste extends PotionEffectPerk {

    public Haste() {
        super("haste");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.FAST_DIGGING;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }

    @Override
    public Material getMaterial() {
        return Material.IRON_PICKAXE;
    }
}
