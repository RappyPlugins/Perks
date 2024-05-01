package com.rappytv.perks;

import com.rappytv.perks.command.PerkCommand;
import com.rappytv.perks.listeners.DamageListener;
import com.rappytv.perks.listeners.InventoryListener;
import com.rappytv.perks.listeners.PlayerListener;
import com.rappytv.perks.perks.Perk;
import com.rappytv.perks.perks.PerkManager;
import com.rappytv.perks.perks.boosts.*;
import com.rappytv.perks.perks.immunity.*;
import com.rappytv.perks.perks.misc.Glow;
import com.rappytv.perks.perks.misc.KeepExperience;
import com.rappytv.perks.perks.misc.KeepInventory;
import com.rappytv.perks.perks.misc.NightVision;
import com.rappytv.perks.perks.protection.*;
import com.rappytv.rylib.util.I18n;
import com.rappytv.rylib.util.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;

public class PerkPlugin extends JavaPlugin {

    public static File dataFolder;
    private I18n i18n;
    private Economy economy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        i18n = new I18n(this);
        new UpdateChecker<>(
                this,
                () -> getConfig().getBoolean("checkForUpdates")
        ).setArtifactFormat(
                "ci.rappytv.com",
                getName(),
                "com.rappytv",
                "Minecraft Plugins"
        );

        // Setup economy
        if(getServer().getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if(rsp != null) {
                economy = rsp.getProvider();
            }
            getLogger().info("Vault is not installed. Economy options won't be available.");
        }

        // Register perks
        Perk.setPlugin(this);
        registerPerks();

        // Set data folder
        dataFolder = getDataFolder();

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DamageListener(), this);
        pm.registerEvents(new InventoryListener(this), this);
        pm.registerEvents(new PlayerListener(this), this);
        new PerkCommand("perks", this);
    }

    public I18n i18n() {
        return i18n;
    }

    @Nullable
    public Economy getEconomy() {
        return economy;
    }

    private void registerPerks() {
        PerkManager.register(new Glow());
        PerkManager.register(new PreventFallDamage());
        PerkManager.register(new PreventDrowning());
        PerkManager.register(new StopHunger());
        PerkManager.register(new NightVision());
        PerkManager.register(new FireProtection());
        PerkManager.register(new Protection());
        PerkManager.register(new KeepInventory());
        PerkManager.register(new KeepExperience());
        PerkManager.register(new Haste());
        PerkManager.register(new ExperienceBoost());
        PerkManager.register(new HeartBoost());
        PerkManager.register(new SpeedBoost());
        PerkManager.register(new JumpBoost());
        PerkManager.register(new StrengthBoost());
        PerkManager.register(new ZombieImmunity());
        PerkManager.register(new SkeletonImmunity());
        PerkManager.register(new SpiderImmunity());
        PerkManager.register(new CreeperImmunity());
        PerkManager.register(new EndermanImmunity());
        PerkManager.register(new BlazeImmunity());
        PerkManager.register(new GhastImmunity());
    }
}
