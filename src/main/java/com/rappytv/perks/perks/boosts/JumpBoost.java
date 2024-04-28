package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;

public class JumpBoost extends PotionEffectPerk {

    public JumpBoost() {
        super("jumpboost");
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.JUMP;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }

    @Override
    public Material getMaterial() {
        return Material.RABBIT_FOOT;
    }
}
