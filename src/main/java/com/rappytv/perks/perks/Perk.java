package com.rappytv.perks.perks;

import com.rappytv.perks.config.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class Perk {

    public static final List<Perk> perks = new ArrayList<>();

    private final String id;
    private final String name;

    public Perk(String id, String name) {
        this.id = id;
        this.name = name;
        perks.add(this);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public abstract ItemStack getItem();
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
        if(!player.hasPermission("esntls.perks." + id))
            return new Pane(Pane.Type.NOPERMISSION);
        return new Pane(data.getActivePerks().contains(id) ? Pane.Type.ACTIVATED : Pane.Type.DEACTIVATED);
    }

    public ItemStack getItem(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b" + name);
        item.setItemMeta(meta);
        return item;
    }

    public static class Pane extends ItemStack {

        public Pane(Type type) {
            super(type == Type.ACTIVATED ? Material.LIME_STAINED_GLASS_PANE : type == Type.DEACTIVATED ? Material.RED_STAINED_GLASS_PANE : Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta meta = this.getItemMeta();
            List<String> lore = new ArrayList<>();
            String title;
            String description = switch (type) {
                case ACTIVATED -> {
                    title = "§aAktiviert";
                    yield "§bKlicke zum §cdeaktivieren§b!";
                }
                case DEACTIVATED -> {
                    title = "§cDeaktiviert";
                    yield "§bKlicke zum §aaktivieren§b!";
                }
                case NOPERMISSION -> {
                    title = "§cNicht freigeschaltet";
                    yield "§bDu hast dieses Perk noch nicht freigeschaltet!";
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
