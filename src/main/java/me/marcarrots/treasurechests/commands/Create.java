package me.marcarrots.treasurechests.commands;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.saves.Data;
import me.marcarrots.treasurechests.utils.LocationHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Create extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a treasure chest at the chest you're looking at.";
    }

    @Override
    public String getSyntax() {
        return "/treasurechests create <name>";
    }

    @Override
    public String getPermission() {
        return "treasurechests.create";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (!player.hasPermission(getPermission())) {
            player.sendMessage("§cYou do not have permission to create treasure chests.");
            return;
        }

        if (args.length != 2) {

            player.sendMessage(getSyntax() + " - " + getDescription());
            return;
        }

        if (args[1].length() > 30) {
            player.sendMessage("Please use a shorter name.");
            return;
        }

        String treasureName = args[1];

        Block targetBlock = player.getTargetBlockExact(10);

        if (targetBlock == null) {
            return;
        }

        player.sendMessage(targetBlock.getType().toString());
        player.sendMessage(String.valueOf(targetBlock.getLocation()));

        if (plugin.getMaterialSet().contains(targetBlock.getType())) {

            Data data = plugin.getDataFile();
            data.get().set("Treasures." + treasureName + ".location", LocationHandler.getStringFromLocation(targetBlock.getLocation()));
            data.get().set("Treasures." + treasureName + ".creator", player.getUniqueId().toString());
            data.get().set("Treasures." + treasureName + ".date", System.currentTimeMillis());
            data.save();
        } else {
            player.sendMessage("§cYou may only create treasure chests for chests, trapped chests, or barrels.");
            return;
        }

        player.sendMessage("§3This chest has been added as a treasure chest. Use /treasurechests key to assign this chest a key.");

    }

    @Override
    public List<String> getTabSuggester(Player player, int argsLength) {
        return null;
    }


}
