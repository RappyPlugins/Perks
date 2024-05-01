package com.rappytv.perks.listeners;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.perks.PerkManager;
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
                    if(page == Math.ceil((double) PerkManager.getPerks().size() / 7) - 1) return;
                    page++;
                } else {
                    if(page == 0) return;
                    page--;
                }
                Util.openPerkGUI(plugin, player, page);
            } else if(item.getType() == Material.LIME_STAINED_GLASS_PANE) {
                Perk perk = null;
                for(Perk p : PerkManager.getPerks()) {
                    if(p.getItem().equals(inventory.getItem(event.getSlot() - 9))) perk = p;
                }
                if(perk == null) return;
                perk.removeFrom(player);
                inventory.setItem(event.getSlot(), new Perk.Pane(Perk.Pane.Type.DEACTIVATED));
            } else if(item.getType() == Material.RED_STAINED_GLASS_PANE) {
                Perk perk = null;
                for(Perk p : PerkManager.getPerks()) {
                    if(p.getItem().equals(inventory.getItem(event.getSlot() - 9))) perk = p;
                }
                if(perk == null) return;
                perk.addTo(player);
                inventory.setItem(event.getSlot(), new Perk.Pane(Perk.Pane.Type.ACTIVATED));
            } else if(item.getType() == Material.BARRIER) {
                if(!player.hasPermission("perks.quickUnlock")) return;
                Perk perk = null;
                for(Perk p : PerkManager.getPerks()) {
                    if(p.getItem().equals(inventory.getItem(event.getSlot() - 9))) perk = p;
                }
                if(perk == null) return;
                perk.unlockFor(player);
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

                List<Perk> perks = PerkManager.getPerks();
                boolean hasAllPerks = true;
                for(Perk perk : perks)
                    if(!data.getUnlockedPerks().contains(perk.getId())) {
                        hasAllPerks = false;
                        break;
                    }

                if(hasAllPerks) {
                    player.closeInventory();
                    player.sendMessage(plugin.i18n().translate("alreadyAllPerks"));
                    return;
                }

                List<Perk> missingPerks = new ArrayList<>();
                for(Perk perk : perks) {
                    if(!data.getUnlockedPerks().contains(perk.getId())) missingPerks.add(perk);
                }

                economy.withdrawPlayer(player, price);
                if(missingPerks.size() == 1) {
                    Perk perk = missingPerks.get(0);
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

                new SpinManager(player, missingPerks, plugin).spin(() -> perkPlayers.remove(player.getUniqueId()));
            }
        }
    }
}
