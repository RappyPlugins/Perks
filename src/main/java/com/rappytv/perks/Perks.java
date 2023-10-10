package com.rappytv.perks;

import com.rappytv.perks.command.PerkCommand;
import com.rappytv.perks.listeners.InventoryListener;
import com.rappytv.perks.perks.boosts.*;
import com.rappytv.perks.perks.immunity.*;
import com.rappytv.perks.perks.misc.Glow;
import com.rappytv.perks.perks.misc.KeepExperience;
import com.rappytv.perks.perks.misc.KeepInventory;
import com.rappytv.perks.perks.misc.NightVision;
import com.rappytv.perks.perks.protection.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

public final class Perks extends JavaPlugin {

    public static String prefix;
    public static ConfigurationSection messages;
    public static File dataFolder;
    private Economy economy;

    @Override
    public void onEnable() {
        // Load prefix
        String prfx = getConfig().getString("messages.prefix");
        if(prfx == null) {
            getLogger().severe("Prefix is not set!");
            prefix = "§bPerks §8» §7";
        } else prefix = ChatColor.translateAlternateColorCodes('&', prfx);

        // Setup economy
        if(getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if(rsp != null) {
                economy = rsp.getProvider();
            }
            getLogger().info("Vault is not installed. Economy options won't be available.");
        }

        // Load messages
        messages = getConfig().getConfigurationSection("messages");

        // Register perks
        registerPerks();

        // Set data folder
        dataFolder = getDataFolder();

        Objects.requireNonNull(getCommand("perks")).setExecutor(new PerkCommand(this));
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListener(this), this);
    }

    @Override
    public void onDisable() {}

    @Nullable
    public Economy getEconomy() {
        return economy;
    }

    private void registerPerks() {
        new Glow();
        new PreventFallDamage();
        new PreventDrowning();
        new StopHunger();
        new NightVision();
        new FireProtection();
        new Protection();
        new KeepInventory();
        new KeepExperience();
        new Haste();
        new ExperienceBoost();
        new HeartBoost();
        new SpeedBoost();
        new JumpBoost();
        new StrengthBoost();
        new ZombieImmunity();
        new SkeletonImmunity();
        new SpiderImmunity();
        new CreeperImmunity();
        new EndermanImmunity();
        new BlazeImmunity();
        new GhastImmunity();
    }
}
