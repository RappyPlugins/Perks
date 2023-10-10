package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Protection extends PotionEffectPerk {

    public Protection() {
        super("protection", "Panzer");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.BEDROCK);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.DAMAGE_RESISTANCE;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }
}
