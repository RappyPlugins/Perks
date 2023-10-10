package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class SpeedBoost extends PotionEffectPerk {

    public SpeedBoost() {
        super("speedboost", "Schneller Laufen");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.IRON_BOOTS);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.SPEED;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }
}
