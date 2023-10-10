package com.rappytv.perks.perks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class PotionEffectPerk extends Perk {

    public PotionEffectPerk(String id, String name) {
        super(id, name);
    }

    @Override
    public abstract ItemStack getItem();

    public abstract PotionEffectType getEffectType();
    public abstract int getEffectAplifier();

    @Override
    public void onEnable(Player player) {
        player.addPotionEffect(new PotionEffect(getEffectType(), -1, getEffectAplifier(), false, false, false));
    }

    @Override
    public void onDisable(Player player) {
        player.removePotionEffect(getEffectType());
    }
}
