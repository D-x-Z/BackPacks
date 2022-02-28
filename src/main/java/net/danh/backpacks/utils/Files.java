package net.danh.backpacks.utils;

import net.danh.backpacks.Backpacks;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {


    private static Files instance;
    private File configFile;
    private FileConfiguration config;

    public static Files getInstance() {

        if (instance == null) {
            instance = new Files();
        }
        return instance;
    }

    public void createconfig() {
        configFile = new File(Backpacks.get().getDataFolder(), "config.yml");

        if (!configFile.exists()) Backpacks.get().saveResource("config.yml", false);
        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getconfig() {
        return config;
    }


    public void reloadconfig() {
        saveconfig();
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveconfig() {
        try {
            config.save(configFile);
        } catch (IOException ignored) {
        }
    }

}
