package com.rappytv.perks.perks;

import com.rappytv.perks.PerkPlugin;
import com.rappytv.perks.config.PlayerData;
import org.bukkit.Material;
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

    public void addTo(Player player) {
        PlayerData data = PlayerData.get(player);
        if(data == null) {
            data = PlayerData.create(player);
        }
        data.activatePerk(this.id);
        data.save();
        onEnable(player);
    }

    public void removeFrom(Player player) {
        PlayerData data = PlayerData.get(player);
        if(data == null) {
            data = PlayerData.create(player);
        }
        data.deactivatePerk(this.id);
        data.save();
        onDisable(player);
    }

    public void unlockFor(Player player) {
        PlayerData data = PlayerData.get(player);
        if(data == null)
            data = PlayerData.create(player);
        data.unlockPerk(this.id);
        data.save();
        onEnable(player);
    }

    public void lockFor(Player player) {
        PlayerData data = PlayerData.get(player);
        if(data == null)
            data = PlayerData.create(player);
        data.lockPerk(this.id);
        data.save();
        onDisable(player);
    }

    public ItemStack getPane(Player player) {
        PlayerData data = PlayerData.get(player);
        if(data == null) {
            data = PlayerData.create(player);
            data.save();
        }
        if(!data.getUnlockedPerks().contains(id))
            return new Pane(Pane.Type.NOPERMISSION);
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

        @SuppressWarnings("ConstantConditions")
        public Pane(Type type) {
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
