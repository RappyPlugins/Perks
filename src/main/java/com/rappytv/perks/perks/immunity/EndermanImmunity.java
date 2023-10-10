package com.rappytv.perks.perks.immunity;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EndermanImmunity extends Perk {

    public EndermanImmunity() {
        super("endermanimmunity", "Immun gegen Enderman");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.ENDERMAN_SPAWN_EGG);
    }
}
