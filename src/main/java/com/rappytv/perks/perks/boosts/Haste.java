package com.rappytv.perks.perks.boosts;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Haste extends Perk {

    private final PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING, -1, 1, false, false, false);

    public Haste() {
        super("haste", "Schneller Abbauen");
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
        return getItem(Material.IRON_PICKAXE);
    }
}
