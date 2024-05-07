package com.rappytv.perks.events;

import com.rappytv.perks.perks.Perk;
import org.bukkit.event.Cancellable;

public class PerkRegisterEvent extends PerkEvent implements Cancellable {

    private final Perk perk;
    private boolean cancelled;

    public PerkRegisterEvent(Perk perk) {
        this.perk = perk;
    }

    public Perk getPerk() {
        return perk;
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
