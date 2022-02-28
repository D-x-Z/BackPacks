package net.danh.backpacks.utils;

import net.danh.backpacks.Backpacks;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    private static File configFile;
    private static FileConfiguration config;


    public static void createfiles() {
        configFile = new File(Backpacks.get().getDataFolder(), "config.yml");

        if (!configFile.exists()) Backpacks.get().saveResource("config.yml", false);
        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getconfig() {
        return config;
    }


    public static void reloadfiles() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

}
