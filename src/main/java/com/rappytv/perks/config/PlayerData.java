package com.rappytv.perks.config;

import com.rappytv.perks.Perks;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerData {

    private static final File folder = new File(Perks.dataFolder, "playerdata");
    private final File file;
    private final FileConfiguration config;

    private String name;
    private UUID uuid;
    private final Set<String> activePerks;
    private final Set<String> unlockedPerks;

    private PlayerData(UUID uuid) {
        if(!folder.exists())
            folder.mkdirs();

        file = new File(folder, uuid.toString() + ".yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        config = YamlConfiguration.loadConfiguration(file);

        this.uuid = uuid;
        this.name = config.getString("name");
        this.activePerks = new HashSet<>(config.getStringList("perks.active"));
        this.unlockedPerks = new HashSet<>(config.getStringList("perks.unlocked"));
    }

    public static PlayerData create(Player player) {
        PlayerData data = new PlayerData(player.getUniqueId());
        data.setUuid(player.getUniqueId());
        data.setName(player.getName());
        return data;
    }

    public static PlayerData create(OfflinePlayer player) {
        PlayerData data = new PlayerData(player.getUniqueId());
        data.setUuid(player.getUniqueId());
        data.setName(player.getName());
        return data;
    }

    public static PlayerData get(Player player) {
        if(!exists(player.getUniqueId())) return null;
        return new PlayerData(player.getUniqueId());
    }

    public static PlayerData get(UUID uuid) {
        if(!exists(uuid)) return null;
        return new PlayerData(uuid);
    }

    public static PlayerData get(String name) {
        if(!folder.exists()) return null;
        for(String filename : folder.list()) {
            PlayerData data = get(UUID.fromString(filename.substring(0, filename.length() - 4)));
            if(data == null) continue;

            if(data.getName().equals(name)) return data;
        }

        return null;
    }

    public static boolean exists(UUID uuid) {
        return new File(folder, uuid.toString() + ".yml").exists();
    }

    public PlayerData save() {
        try {
            config.save(file);
        } catch (IOException ignored) {
        }
        return this;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        config.set("name", name);
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        config.set("uuid", uuid.toString());
    }

    public Set<String> getActivePerks() {
        return activePerks;
    }
    public void activatePerk(String perk) {
        activePerks.add(perk);
        config.set("perks.active", new ArrayList<>(activePerks));
    }
    public void deactivatePerk(String perk) {
        activePerks.remove(perk);
        config.set("perks.active", new ArrayList<>(activePerks));
    }

    public Set<String> getUnlockedPerks() {
        return unlockedPerks;
    }
    public void unlockPerk(String perk) {
        activePerks.add(perk);
        config.set("perks.unlocked", new ArrayList<>(unlockedPerks));
    }
    public void lockPerk(String perk) {
        activePerks.remove(perk);
        config.set("perks.unlocked", new ArrayList<>(unlockedPerks));
    }
}
