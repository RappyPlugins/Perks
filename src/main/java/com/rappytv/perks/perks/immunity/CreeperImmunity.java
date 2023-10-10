package com.rappytv.perks.perks.immunity;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CreeperImmunity extends Perk {

    public CreeperImmunity() {
        super("creeperimmunity", "Immun gegen Creeper");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.CREEPER_SPAWN_EGG);
    }
}
