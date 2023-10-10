package com.rappytv.perks.perks.misc;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KeepInventory extends Perk {

    public KeepInventory() {
        super("keepinventory", "Inventar behalten");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.CHEST);
    }
}
