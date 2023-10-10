package com.rappytv.perks.perks.immunity;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkeletonImmunity extends Perk {

    public SkeletonImmunity() {
        super("skeletonimmunity", "Immun gegen Skelett");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.SKELETON_SPAWN_EGG);
    }
}
