package com.rappytv.perks.util;

import com.rappytv.perks.Perks;
import com.rappytv.perks.listeners.InventoryListener;
import com.rappytv.perks.perks.Perk;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class Util {

    public static String message(String key, Object... objects) {
        String message = Perks.messages.getString(key);
        if(message == null) return key;
        return String.format(ChatColor.translateAlternateColorCodes('&', message), objects);
    }

    public static void openPerkGUI(Perks plugin, Player player, int page) {
        Inventory inventory = Bukkit.createInventory(null, 36, message("menuTitle"));
        InventoryListener.pages.put(player, page);
        int perkIndex = 0;
        perkIndex += page * 7;
        for(int i = 0; i < inventory.getSize(); i++) {
            if(i > 9 && i < 17) {
                if(Perk.perks.size() > perkIndex) {
                    Perk perk = Perk.perks.get(perkIndex);
                    if(perk == null) continue;
                    inventory.setItem(i, perk.getItem());
                    inventory.setItem(i + 9, perk.getPane(player));
                    perkIndex++;
                }
            } else if(i == 29 || i == 33) {
                ItemStack arrow = new ItemStack(Material.ARROW);
                ItemMeta meta = arrow.getItemMeta();
                meta.setDisplayName(i == 29 ? message("back") : message("continue"));
                arrow.setItemMeta(meta);
                inventory.setItem(i, arrow);
            } else if(i == 31) {
                boolean hasAllPerks = Perk.perks.stream().allMatch((perk -> player.hasPermission("perks." + perk.getId())));

                Economy economy = plugin.getEconomy();

                ItemStack item = null;
                ItemMeta meta;
                if(hasAllPerks) {
                    item = new ItemStack(Material.DIAMOND_BLOCK);
                    meta = item.getItemMeta();
                    meta.setDisplayName(message("allPerks"));
                    item.setItemMeta(meta);
                } else if(economy != null) {
                    item = new ItemStack(Material.GOLD_BLOCK);
                    meta = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    for(String message : Perks.messages.getStringList("messages.buyPerkLore")) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', String.format(message, economy.format(plugin.getConfig().getDouble("economy.perks")))));
                    }
                    meta.setDisplayName(message("buyPerk"));
                    meta.setLore(lore);
                }

                if(item != null) inventory.setItem(i, item);
            }
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, new Perk.Pane(Perk.Pane.Type.DECORATION));
            }
        }

        player.openInventory(inventory);
    }
}
