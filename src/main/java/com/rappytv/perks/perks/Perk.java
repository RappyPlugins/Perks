package com.rappytv.perks.perks;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public abstract class Perk {

    public static final List<Perk> perks = new ArrayList<>();
    protected static PerkPlugin plugin;

    private final String id;
    private final String name;

    public Perk(String id) {
        this.id = id;
        this.name = plugin.i18n().translate("perks." + id + ".name");
        perks.add(this);
    }

    public static void setPlugin(PerkPlugin plugin) {
        Perk.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public abstract Material getMaterial();
    public abstract void onEnable(Player player);
    public abstract void onDisable(Player player);

    public void addTo(OfflinePlayer player) {
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null) {
            data = PlayerData.create(player);
        }
        data.activatePerk(this.id);
        data.save();
        if(player.isOnline()) onEnable((Player) player);
    }

    public void removeFrom(OfflinePlayer player) {
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null) {
            data = PlayerData.create(player);
        }
        data.deactivatePerk(this.id);
        data.save();
        if(player.isOnline()) onDisable((Player) player);
    }

    public void unlockFor(OfflinePlayer player) {
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null)
            data = PlayerData.create(player);
        data.unlockPerk(this.id);
        data.save();
    }

    public void lockFor(OfflinePlayer player) {
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null)
            data = PlayerData.create(player);
        data.lockPerk(this.id);
        data.save();
        if(player.isOnline()) onDisable((Player) player);
    }

    public boolean isPerkActive(OfflinePlayer player) {
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null) return false;
        return data.getActivePerks().contains(id);
    }

    public boolean isPerkUnlocked(OfflinePlayer player) {
        PlayerData data = PlayerData.get(player.getUniqueId());
        if(data == null) return false;
        return data.getUnlockedPerks().contains(id);
    }

    public ItemStack getPane(Player player) {
        PlayerData data = PlayerData.get(player);
        if(data == null) {
            data = PlayerData.create(player);
            data.save();
        }
        if(!data.getUnlockedPerks().contains(id))
            return new Pane(Pane.Type.NOPERMISSION, player.hasPermission("perks.quickUnlock"));
        return new Pane(data.getActivePerks().contains(id) ? Pane.Type.ACTIVATED : Pane.Type.DEACTIVATED);
    }

    @SuppressWarnings("ConstantConditions")
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b" + name);
        List<String> lore = new ArrayList<>();
        lore.add("§7" + plugin.i18n().translate("perks." + id + ".description"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static class Pane extends ItemStack {

        public Pane(Type type) {
            this(type, false);
        }

        @SuppressWarnings("ConstantConditions")
        public Pane(Type type, boolean quick) {
            super(type == Type.ACTIVATED ? Material.LIME_STAINED_GLASS_PANE : type == Type.DEACTIVATED ? Material.RED_STAINED_GLASS_PANE : type == Type.NOPERMISSION ? Material.BARRIER : Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta meta = this.getItemMeta();
            List<String> lore = new ArrayList<>();
            String title;
            String description = switch (type) {
                case ACTIVATED -> {
                    title = plugin.i18n().translate("pane.activated.title");
                    yield plugin.i18n().translate("pane.activated.description");
                }
                case DEACTIVATED -> {
                    title = plugin.i18n().translate("pane.deactivated.title");
                    yield plugin.i18n().translate("pane.deactivated.description");
                }
                case NOPERMISSION -> {
                    title = plugin.i18n().translate("pane.noPermission.title");
                    yield plugin.i18n().translate("pane.noPermission.description");
                }
                case DECORATION -> {
                    title = "§e";
                    yield null;
                }
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
            meta.setDisplayName(title);
            lore.add(description);
            if(type == Type.NOPERMISSION && quick) {
                lore.add("");
                lore.add(plugin.i18n().translate("quickUnlockPerk"));
            }
            meta.setLore(lore);
            this.setItemMeta(meta);
        }

        public enum Type {
            ACTIVATED,
            DEACTIVATED,
            NOPERMISSION,
            DECORATION
        }
    }

}
