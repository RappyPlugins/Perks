package com.rappytv.perks.events;

import org.bukkit.event.Cancellable;

@SuppressWarnings("unused")
public class PerkUnregisterEvent extends PerkEvent implements Cancellable {

    private final String perkId;
    private boolean cancelled;

    public PerkUnregisterEvent(String perkId) {
        this.perkId = perkId;
    }

    public String getPerkId() {
        return perkId;
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
