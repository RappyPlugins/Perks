package com.rappytv.perks.perks;

import com.rappytv.perks.events.PerkRegisterEvent;
import com.rappytv.perks.events.PerkUnregisterEvent;
import org.bukkit.Bukkit;

import java.util.*;

@SuppressWarnings("unused")
public class PerkManager {

    private static final List<Perk> perks = new ArrayList<>();

    public static void register(Perk perk) {
        PerkRegisterEvent event = new PerkRegisterEvent(perk);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled())
            perks.add(perk);
    }

    public static void unregister(String perkId) {
        PerkUnregisterEvent event = new PerkUnregisterEvent(perkId);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled())
            perks.removeIf(perk -> perk.getId().equalsIgnoreCase(perkId));
    }

    public static List<Perk> getPerks() {
        return Collections.unmodifiableList(perks);
    }
}
