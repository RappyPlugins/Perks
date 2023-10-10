package com.rappytv.perks.perks.boosts;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class Haste extends PotionEffectPerk {

    public Haste() {
        super("haste", "Schneller Abbauen");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.IRON_PICKAXE);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.FAST_DIGGING;
    }

    @Override
    public int getEffectAplifier() {
        return 1;
    }
}
