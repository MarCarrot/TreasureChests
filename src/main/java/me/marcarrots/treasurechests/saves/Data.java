package me.marcarrots.treasurechests.saves;

import me.marcarrots.treasurechests.TreasureChests;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {

    private File file;
    private FileConfiguration dataFile;


    public void setup() {
        try {
            if (!TreasureChests.getPlugin().getDataFolder().exists()) {
                TreasureChests.getPlugin().getDataFolder().mkdirs();
            }
            file = new File(TreasureChests.getPlugin().getDataFolder(), "data.yml");
            if (!file.exists()) { //Check if it exists
                TreasureChests.getPlugin().getLogger().info("data.yml not found, creating!");
                TreasureChests.getPlugin().saveResource("data.yml", true);
            } else {
                TreasureChests.getPlugin().getLogger().info("data.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataFile = YamlConfiguration.loadConfiguration(file);
    }


    public FileConfiguration get() {
        return dataFile;
    }


    public  void save() {
        try {
            dataFile.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning("CRITICAL: Failed to save data.yml'!");
        }
    }


    public void reload() {
        try {
            dataFile = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            Bukkit.getLogger().warning("CRITICAL: Failed to reload 'data.yml'!");
        }
    }





}
