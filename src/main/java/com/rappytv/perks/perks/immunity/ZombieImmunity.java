package com.rappytv.perks.perks.immunity;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ZombieImmunity extends Perk {

    public ZombieImmunity() {
        super("zombieimmunity");
    }

    @Override
    public Material getMaterial() {
        return Material.ZOMBIE_SPAWN_EGG;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
