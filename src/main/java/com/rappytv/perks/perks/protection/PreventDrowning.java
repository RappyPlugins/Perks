package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class PreventDrowning extends PotionEffectPerk {

    public PreventDrowning() {
        super("nodrowning");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.WATER_BREATHING;
    }

    @Override
    public int getEffectAplifier() {
        return 0;
    }

    @Override
    public Material getMaterial() {
        return Material.WATER_BUCKET;
    }
}
