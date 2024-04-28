package com.rappytv.perks.perks.protection;

import com.rappytv.perks.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class StopHunger extends Perk {

    public StopHunger() {
        super("nohunger");
    }

    @Override
    public Material getMaterial() {
        return Material.COOKED_BEEF;
    }

    @Override
    public void onEnable(Player player) {}

    @Override
    public void onDisable(Player player) {}
}
