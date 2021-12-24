package me.zeph.zephyrus.config;



import org.bukkit.configuration.file.FileConfiguration;

import com.projectkorra.projectkorra.configuration.ConfigManager;

import me.zeph.zephyrus.Zephyrus;



public class Config {

    private static ConfigFile main;
    static Zephyrus plugin;

    public Config(Zephyrus plugin) {
        Config.plugin = plugin;
        main = new ConfigFile("config");
        loadConfig();
    }

    public static FileConfiguration getConfig() {
        return main.getConfig();
    }

    public void loadConfig() {
        FileConfiguration config = Zephyrus.plugin.getConfig();
        FileConfiguration rankConfig = ConfigManager.languageConfig.get();
        FileConfiguration langConfig = config;
        
        //Ability configuration
        
		config.addDefault("Zephyrus.Air.AirBall.Cooldown", Long.valueOf(2000));
		config.addDefault("Zephyrus.Air.AirBall.Damage", Double.valueOf(2.0));
		config.addDefault("Zephyrus.Air.AirBall.Range", Double.valueOf(20.0));
		config.addDefault("Zephyrus.Air.AirBall.Radius", Double.valueOf(0.4));
		config.addDefault("Zephyrus.Air.AirBall.Speed", Double.valueOf(1.2));

		config.addDefault("Zephyrus.Air.AirStrike.Cooldown", Long.valueOf(2000));
		config.addDefault("Zephyrus.Air.AirStrike.Damage", Double.valueOf(2.0));
		config.addDefault("Zephyrus.Air.AirStrike.Range", Double.valueOf(20.0));
		config.addDefault("Zephyrus.Air.AirStrike.Radius", Double.valueOf(0.4));
		config.addDefault("Zephyrus.Air.AirStrike.Speed", Double.valueOf(1.2));
		config.addDefault("Zephyrus.Air.AirStrike.Shots", Integer.valueOf(4));
		
		config.addDefault("Zephyrus.Air.SpiritualProjection.Cooldown", Long.valueOf(5000));
		config.addDefault("Zephyrus.Air.SpiritualProjection.Duration", Long.valueOf(20000));
		config.addDefault("Zephyrus.Air.SpiritualProjection.Range", Double.valueOf(50.0));
		
		config.addDefault("Zephyrus.Air.AirPropulsion.Cooldown", Long.valueOf(2000));
		config.addDefault("Zephyrus.Air.AirPropulsion.Duration", Long.valueOf(4000));
		config.addDefault("Zephyrus.Air.AirPropulsion.Speed", Double.valueOf(1.2));
		
        ConfigManager.languageConfig.save();
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }
}











































