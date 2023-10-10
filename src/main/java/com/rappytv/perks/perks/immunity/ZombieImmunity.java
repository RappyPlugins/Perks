package com.rappytv.perks.perks.immunity;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ZombieImmunity extends Perk {

    public ZombieImmunity() {
        super("zombieimmunity", "Immun gegen Zombie");
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}

    @Override
    public ItemStack getItem() {
        return getItem(Material.ZOMBIE_SPAWN_EGG);
    }
}
