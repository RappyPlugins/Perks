package com.rappytv.perks.perks.misc;

import com.rappytv.essentials.perks.Perk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVision extends Perk {

    private final PotionEffect effect = new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 0, false, false, false);

    public NightVision() {
        super("nightvision", "Nachtsicht");
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
        return getItem(Material.CARVED_PUMPKIN);
    }
}
