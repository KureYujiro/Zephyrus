package me.zeph.zephyrus;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.CoreAbility;

import me.zeph.zephyrus.config.Config;
import me.zeph.zephyrus.listeners.AbilityListener;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;



public final class Zephyrus extends JavaPlugin {
	
    public static Zephyrus plugin;

    @Override
    public void onEnable() {
        plugin = this;

        new Config(this);
        CoreAbility.registerPluginAbilities(plugin, "me.zeph.zephyrus.abilities");
        //CoreAbility.registerPluginAbilities(plugin, "me.zeph.zephyrus.abilities.combos");
        this.registerListeners();

        plugin.getLogger().info("Successfully enabled Zephyrus.");
    }

    @Override
    public void onDisable() {
        plugin.getLogger().info("Successfully disabled Zephyrus.");
    }

    public static Zephyrus getInstance() {
        return plugin;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new AbilityListener(), this);
    }
}