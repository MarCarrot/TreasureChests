package me.marcarrots.treasurechests.commands;

import me.marcarrots.treasurechests.TreasureChests;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SubCommand {

    protected TreasureChests plugin = TreasureChests.getPlugin();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract void perform(Player player, String[] args);

    public abstract List<String> getTabSuggester(Player player, int argsLength);

}
