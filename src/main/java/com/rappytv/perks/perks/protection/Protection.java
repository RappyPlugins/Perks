package com.rappytv.perks.perks.protection;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Protection extends Perk {

    private final PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, -1, 1, false, false, false);

    public Protection() {
        super("protection", "Panzer");
    }

    @Override
    public void onEnable(Player player) {
        player.addPotionEffect(effect);
    }

    @Override
    public void onDisable(Player player) {
        player.removePotionEffect(effect.getType());
    }

    @Override
    public ItemStack getItem() {
        return getItem(Material.BEDROCK);
    }
}
