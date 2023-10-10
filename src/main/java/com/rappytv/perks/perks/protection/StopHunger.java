package com.rappytv.perks.perks.protection;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StopHunger extends Perk {

    public StopHunger() {
        super("nohunger", "Kein Hunger");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.COOKED_BEEF);
    }
}
