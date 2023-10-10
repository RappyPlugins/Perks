package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class JumpBoost extends PotionEffectPerk {

    public JumpBoost() {
        super("jumpboost", "Höher Springen");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.RABBIT_FOOT);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.JUMP;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }
}
