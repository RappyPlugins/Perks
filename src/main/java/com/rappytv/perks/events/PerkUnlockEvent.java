package com.rappytv.perks.events;

import com.rappytv.perks.perks.Perk;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;

@SuppressWarnings("unused")
public class PerkUnlockEvent extends PerkEvent implements Cancellable {

    private final OfflinePlayer player;
    private final Perk perk;
    private final boolean forced;
    private boolean cancelled;

    public PerkUnlockEvent(OfflinePlayer player, Perk perk, boolean forced) {
        this.player = player;
        this.perk = perk;
        this.forced = forced;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public Perk getPerk() {
        return perk;
    }

    public boolean isForced() {
        return forced;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
