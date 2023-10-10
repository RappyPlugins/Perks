package com.rappytv.perks.listeners;

import com.rappytv.perks.Perks;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.util.SpinManager;
import com.rappytv.perks.util.Util;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryListener implements Listener {

    private final Perks plugin;
    public static final Map<Player, Integer> pages = new HashMap<>();
    private final Set<UUID> perkPlayers = new HashSet<>();

    public InventoryListener(Perks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) return;
        Inventory inventory = event.getClickedInventory();
        if(inventory == null) return;

        if(event.getView().getTitle().equals("§5Perk freischalten")) {
            if(inventory == event.getView().getBottomInventory()) return;
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null) return;
            if(item.getType() == Material.ARROW)
                Util.openPerkGUI(plugin, player, 0);
        } else if(event.getView().getTitle().equals("§bPerks")) {
            if(inventory == event.getView().getBottomInventory()) return;
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null) return;
            if(item.getType() == Material.ARROW) {
                int page = pages.getOrDefault(player, 0);
                if(item.getItemMeta() != null && item.getItemMeta().getDisplayName().endsWith("Weiter")) {
                    if(page == Math.ceil((double) Perk.perks.size() / 7) - 1) return;
                    page++;
                } else {
                    if(page == 0) return;
                    page--;
                }
                Util.openPerkGUI(plugin, player, page);
            } else if(item.getType() == Material.LIME_STAINED_GLASS_PANE) {
                Optional<Perk> perk = Perk.perks.stream().filter((p) ->
                        p.getItem().equals(inventory.getItem(event.getSlot() - 9))
                ).findFirst();
                if(perk.isEmpty()) return;
                perk.get().removeFrom(player);
                inventory.setItem(event.getSlot(), new Perk.Pane(Perk.Pane.Type.DEACTIVATED));
            } else if(item.getType() == Material.RED_STAINED_GLASS_PANE) {
                Optional<Perk> perk = Perk.perks.stream().filter((p) ->
                        p.getItem().equals(inventory.getItem(event.getSlot() - 9))
                ).findFirst();
                if(perk.isEmpty()) return;
                perk.get().addTo(player);
                inventory.setItem(event.getSlot(), new Perk.Pane(Perk.Pane.Type.ACTIVATED));
            } else if(item.getType() == Material.GOLD_BLOCK) {
                Economy economy = plugin.getEconomy();
                if(economy == null) return;
                double price = plugin.getConfig().getDouble("economy.price");

                if(!economy.has(player, price)) {
                    player.closeInventory();
                    player.sendMessage(Perks.prefix + "§cDu hast nicht genug Geld!");
                    return;
                }

                if(perkPlayers.contains(player.getUniqueId())) {
                    player.sendMessage(Perks.prefix + "§cDu schaltest gerade bereits ein Perk frei!");
                    player.closeInventory();
                    return;
                }
                perkPlayers.add(player.getUniqueId());

                List<Perk> perks = Perk.perks;
                boolean hasAllPerks = perks.stream().allMatch((perk -> player.hasPermission("esntls.perks." + perk.getId())));

                if(hasAllPerks) {
                    player.closeInventory();
                    player.sendMessage(Perks.prefix + "§cDu hast bereits alle Perks!");
                    return;
                }

                perks = perks
                        .stream()
                        .filter((perk -> !player.hasPermission("esntls.perks." + perk.getId())))
                        .collect(Collectors.toList());

                economy.withdrawPlayer(player, price);
                if(perks.size() == 1) {
                    Perk perk = perks.get(0);
                    perkPlayers.remove(player.getUniqueId());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set esntls.perks." + perk.getId());
                    player.sendMessage(Perks.prefix + Util.message("perkUnlocked", perk.getName()));
                    player.closeInventory();
                    return;
                }

                new SpinManager(player, perks, plugin).spin(() -> perkPlayers.remove(player.getUniqueId()));
            }
        }
    }
}
