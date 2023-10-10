package com.rappytv.perks.perks.misc;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Glow extends PotionEffectPerk {

    public Glow() {
        super("glow", "Glow");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.SPECTRAL_ARROW);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.GLOWING;
    }

    @Override
    public int getEffectAplifier() {
        return 0;
    }
}
