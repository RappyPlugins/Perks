package com.rappytv.perks.listeners;

import com.rappytv.perks.config.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            PlayerData data = PlayerData.get(player);
            if(data == null) return;

            if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(data.getActivePerks().contains("nofalldamage"));
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player) {
            PlayerData data = PlayerData.get(player);
            if(data == null) return;

            switch (event.getDamager().getType()) {
                case BLAZE:
                    event.setCancelled(data.getActivePerks().contains("blazeimmunity"));
                    break;
                case CREEPER:
                    event.setCancelled(data.getActivePerks().contains("creeperimmunity"));
                    break;
                case ENDERMAN:
                    event.setCancelled(data.getActivePerks().contains("endermanimmunity"));
                    break;
                case GHAST:
                    event.setCancelled(data.getActivePerks().contains("ghastimmunity"));
                    break;
                case SKELETON:
                    event.setCancelled(data.getActivePerks().contains("skeletonimmunity"));
                    break;
                case SPIDER:
                    event.setCancelled(data.getActivePerks().contains("spiderimmunity"));
                    break;
                case ZOMBIE:
                    event.setCancelled(data.getActivePerks().contains("zombieimmunity"));
                    break;
            }
        }
    }
}
