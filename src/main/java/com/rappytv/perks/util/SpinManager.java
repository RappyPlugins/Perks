package com.rappytv.perks.util;

import com.rappytv.perks.Perks;
import com.rappytv.perks.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class SpinManager {

    private final Player player;
    private final Inventory inventory;
    private final List<Perk> perks;
    private final Perks plugin;
    private int itemIndex = 0;

    public SpinManager(Player player, List<Perk> perks, Perks plugin) {
        this.player = player;
        this.perks = perks;
        this.inventory = Bukkit.createInventory(null, 27, Util.message("buyPerkTitle"));
        this.plugin = plugin;
    }

    public void setupInventory() {
        ItemStack hopper = new ItemStack(Material.HOPPER);
        ItemMeta meta = hopper.getItemMeta();
        meta.setDisplayName("§e");
        hopper.setItemMeta(meta);
        inventory.setItem(4, hopper);

        int startSlot = new Random().nextInt(perks.size());

        for(int i = 0; i < startSlot; i++) {
            for(int items = 9; items < 18; items++) {
                inventory.setItem(items, perks.get((items + itemIndex) % perks.size()).getItem());
            }
            itemIndex++;
        }
    }

    public void spin(Runnable runnable) {
        setupInventory();
        player.openInventory(inventory);

        Random random = new Random();
        double seconds = 7 + (15 - 7) * random.nextDouble();

        new BukkitRunnable() {
            double delay = 0;
            int ticks = 0;
            boolean done = false;

            @Override
            public void run() {
                if(done) return;
                ticks++;
                delay += 1 / (20 * seconds);

                if(ticks > delay * 10) {
                    ticks = 0;

                    for(int items = 9; items < 18; items++) {
                        inventory.setItem(items, perks.get((items + itemIndex) % perks.size()).getItem());
                    }
                    itemIndex++;

                    if(delay >= 0.5) {
                        done = true;

                        ItemStack item = inventory.getItem(13);
                        Optional<Perk> optionalPerk = perks.stream().filter((p -> p.getItem().equals(item))).findFirst();

                        if(optionalPerk.isEmpty()) {
                            player.sendMessage(Perks.prefix + Util.message("error"));
                            return;
                        }
                        Perk perk = optionalPerk.get();

                        ItemStack arrow = new ItemStack(Material.ARROW);
                        ItemMeta meta = arrow.getItemMeta();
                        meta.setDisplayName(Util.message("back"));
                        arrow.setItemMeta(meta);
                        inventory.setItem(18, arrow);
                        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        perk.unlockFor(player);
                        player.sendMessage(Perks.prefix + Util.message("perkUnlocked", perk.getName()));
                        runnable.run();
                        cancel();
                    } else player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }
}
