package com.rappytv.perks.listeners;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.perks.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private final PerkPlugin plugin;

    public PlayerListener(PerkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null) return;

        for(Perk perk : PerkManager.getPerks()) {
            if(data.getActivePerks().contains(perk.getId()))
                perk.onEnable(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickupXP(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerData.get(player);
        if(data == null) data = PlayerData.create(player);
        data.save();

        double multiplier = plugin.getConfig().getDouble("perks.xpboostMultiplier");
        if(multiplier == 0.0) multiplier = 2.5;

        if(data.getActivePerks().contains("xpboost"))
            event.setAmount((int) Math.ceil(event.getAmount() * multiplier));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerData.get(player);
        if(data == null) return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for(Perk perk : PerkManager.getPerks()) {
                if(data.getActivePerks().contains(perk.getId()))
                    perk.onEnable(player);
            }
        }, 1);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerData data = PlayerData.get(player);
        if(data == null) return;

        if(data.getActivePerks().contains("keepinventory")) {
            event.setKeepInventory(true);
            event.getDrops().clear();
        }
        if(data.getActivePerks().contains("keepxp")) {
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHunger(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player player) {
            PlayerData data = PlayerData.get(player);
            if(data == null) return;

            event.setCancelled(data.getActivePerks().contains("nohunger"));
        }
    }
}
