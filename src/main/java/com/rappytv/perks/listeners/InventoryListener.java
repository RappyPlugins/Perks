package com.rappytv.perks.listeners;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.util.SpinManager;
import com.rappytv.perks.util.Util;
import com.rappytv.rylib.util.I18n;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryListener implements Listener {

    private final PerkPlugin plugin;
    public static final Map<Player, Integer> pages = new HashMap<>();
    private final Set<UUID> perkPlayers = new HashSet<>();

    public InventoryListener(PerkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player)) return;
        Inventory inventory = event.getClickedInventory();
        if(inventory == null) return;

        if(event.getView().getTitle().equals(plugin.i18n().translate("buyPerkTitle"))) {
            if(inventory == event.getView().getBottomInventory()) return;
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null) return;
            if(item.getType() == Material.ARROW)
                Util.openPerkGUI(plugin, player, 0);
        } else if(event.getView().getTitle().equals(plugin.i18n().translate("menuTitle"))) {
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
            } else if(item.getType() == Material.BARRIER) {
                if(!player.hasPermission("perks.quickUnlock")) return;
                Optional<Perk> perk = Perk.perks.stream().filter((p) ->
                        p.getItem().equals(inventory.getItem(event.getSlot() - 9))
                ).findFirst();
                if(perk.isEmpty()) return;
                perk.get().unlockFor(player);
                inventory.setItem(event.getSlot(), new Perk.Pane(Perk.Pane.Type.DEACTIVATED));
            } else if(item.getType() == Material.GOLD_BLOCK) {
                Economy economy = plugin.getEconomy();
                if(economy == null) return;
                double price = plugin.getConfig().getDouble("economy.price");

                if(!economy.has(player, price)) {
                    player.closeInventory();
                    player.sendMessage(plugin.i18n().translate("notEnoughMoney"));
                    return;
                }

                if(perkPlayers.contains(player.getUniqueId())) {
                    player.sendMessage(plugin.i18n().translate("alreadyUnlockingPerk"));
                    player.closeInventory();
                    return;
                }
                perkPlayers.add(player.getUniqueId());

                PlayerData data = PlayerData.get(player);
                if(data == null) data = PlayerData.create(player).save();
                PlayerData finalData = data;

                List<Perk> perks = Perk.perks;
                boolean hasAllPerks = perks.stream().allMatch((perk) -> finalData.getUnlockedPerks().contains(perk.getId()));

                if(hasAllPerks) {
                    player.closeInventory();
                    player.sendMessage(plugin.i18n().translate("alreadyAllPerks"));
                    return;
                }

                perks = perks
                        .stream()
                        .filter((perk) -> !finalData.getUnlockedPerks().contains(perk.getId()))
                        .collect(Collectors.toList());

                economy.withdrawPlayer(player, price);
                if(perks.size() == 1) {
                    Perk perk = perks.get(0);
                    perkPlayers.remove(player.getUniqueId());
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    perk.unlockFor(player);
                    player.sendMessage(plugin.i18n().translate(
                            "perkUnlocked",
                            new I18n.Argument("perk", perk.getName())
                    ));
                    player.closeInventory();
                    return;
                }

                new SpinManager(player, perks, plugin).spin(() -> perkPlayers.remove(player.getUniqueId()));
            }
        }
    }
}
