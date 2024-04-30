package com.rappytv.perks.perks;

import java.util.*;

@SuppressWarnings("unused")
public class PerkManager {

    private static final List<Perk> perks = new ArrayList<>();

    public static void register(Perk perk) {
        perks.add(perk);
    }

    public static void unregister(String perkId) {
        perks.removeIf(perk -> perk.getId().equalsIgnoreCase(perkId));
    }

    public static List<Perk> getPerks() {
        return Collections.unmodifiableList(perks);
    }
}
