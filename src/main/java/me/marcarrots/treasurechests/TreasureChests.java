package me.marcarrots.treasurechests;

import me.marcarrots.treasurechests.commands.CommandManager;
import me.marcarrots.treasurechests.listeners.OnUseContainer;
import me.marcarrots.treasurechests.saves.Data;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class TreasureChests extends JavaPlugin {

    protected static Set<Material> materialSet = new HashSet<>();

    private static TreasureChests plugin;
    private Data dataFile;
    public static TreasureChests getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        dataFile = new Data();

        dataFile.setup();
        setMaterials();

        getServer().getPluginManager().registerEvents(new OnUseContainer(), this);

        getCommand("treasurechests").setExecutor(new CommandManager());

        // Plugin startup logic

    }

    private void setMaterials() {

        materialSet.add(Material.CHEST);
        materialSet.add(Material.TRAPPED_CHEST);
        materialSet.add(Material.BARREL);

    }


    public Set<Material> getMaterialSet() {
        return materialSet;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Data getDataFile() {
        return dataFile;
    }

}
