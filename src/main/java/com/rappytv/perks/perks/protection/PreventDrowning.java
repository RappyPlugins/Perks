package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.PotionEffectPerk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PreventDrowning extends PotionEffectPerk {

    public PreventDrowning() {
        super("nodrowning", "Kein Ertrinken");
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.WATER_BUCKET);
    }

    @Override
    public PotionEffectType getEffectType() {
        return PotionEffectType.WATER_BREATHING;
    }

    @Override
    public int getEffectAplifier() {
        return 0;
    }
}
