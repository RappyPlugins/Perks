package com.rappytv.perks.listeners;

import com.rappytv.perks.Perks;
import com.rappytv.perks.config.PlayerData;
import com.rappytv.perks.perks.Perk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerListener implements Listener {

    private final Perks plugin;

    public PlayerListener(Perks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null) return;

        Set<String> perks = data.getActivePerks();
        List<Perk> filteredPerks = Perk.perks.stream().filter((perk) -> perks.contains(perk.getId())).toList();
        for(Perk perk : filteredPerks) {
            perk.onEnable(player);
        }
    }

    @EventHandler
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

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData data = PlayerData.get(player);
        if(data == null) return;

        List<Perk> perks = Perk.perks.stream().filter((perk) -> data.getActivePerks().contains(perk.getId())).toList();
        for(Perk perk : perks) {
            perk.onEnable(player);
        }
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

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if(event.getEntity() instanceof Player player) {
            PlayerData data = PlayerData.get(player);
            if(data == null) return;

            event.setCancelled(data.getActivePerks().contains("nohunger"));
        }
    }
}
