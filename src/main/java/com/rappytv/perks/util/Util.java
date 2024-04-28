package com.rappytv.perks.util;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.listeners.InventoryListener;
import com.rappytv.perks.perks.Perk;
import com.rappytv.rylib.util.I18n;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class Util {

    public static void openPerkGUI(PerkPlugin plugin, Player player, int page) {
        PlayerData data = PlayerData.get(player);
        if(data == null) data = PlayerData.create(player).save();

        Inventory inventory = Bukkit.createInventory(null, 36, plugin.i18n().translate("menuTitle"));
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
                meta.setDisplayName(i == 29 ? plugin.i18n().translate("back") : plugin.i18n().translate("continue"));
                arrow.setItemMeta(meta);
                inventory.setItem(i, arrow);
            } else if(i == 31) {
                final PlayerData finalData = data;
                boolean hasAllPerks = Perk.perks.stream().allMatch((perk -> finalData.getUnlockedPerks().contains(perk.getId())));

                Economy economy = plugin.getEconomy();

                ItemStack item = null;
                ItemMeta meta = null;
                if(hasAllPerks) {
                    item = new ItemStack(Material.DIAMOND_BLOCK);
                    meta = item.getItemMeta();
                    meta.setDisplayName(plugin.i18n().translate("allPerks"));
                } else if(economy != null) {
                    item = new ItemStack(Material.GOLD_BLOCK);
                    meta = item.getItemMeta();
                    String loreString = plugin.i18n().translate(
                            "buyPerkLore",
                            new I18n.Argument("price", economy.format(plugin.getConfig().getDouble("economy.perks")))
                    );
                    List<String> lore = new ArrayList<>(Arrays.asList(loreString.split("\n")));
                    meta.setDisplayName(plugin.i18n().translate("buyPerk"));
                    meta.setLore(lore);
                }

                if(item != null) {
                    item.setItemMeta(meta);
                    inventory.setItem(i, item);
                }
            }
            if(inventory.getItem(i) == null) {
                inventory.setItem(i, new Perk.Pane(Perk.Pane.Type.DECORATION));
            }
        }

        player.openInventory(inventory);
    }
}
