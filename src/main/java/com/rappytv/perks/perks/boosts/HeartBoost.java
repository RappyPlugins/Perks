package com.rappytv.perks.perks.boosts;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HeartBoost extends Perk {

    public HeartBoost() {
        super("morehearts", "Notch Apple");
    }

    @Override
    public void onEnable(Player player) {
        player.setMaxHealth(40);
    }

    @Override
    public void onDisable(Player player) {
        player.setMaxHealth(20);
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.ENCHANTED_GOLDEN_APPLE);
    }
}
