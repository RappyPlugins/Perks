package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PreventFallDamage extends Perk {

    public PreventFallDamage() {
        super("nofalldamage", "Kein Fallschaden");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.GRASS_BLOCK);
    }
}
