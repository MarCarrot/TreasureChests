package me.marcarrots.treasurechests.commands;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.saves.Data;
import me.marcarrots.treasurechests.utils.LocationHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Teleport extends SubCommand {
    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public String getDescription() {
        return "Teleports to the location of a treasure chest.";
    }

    @Override
    public String getSyntax() {
        return "/ticket tp <treasure chest name>";
    }

    @Override
    public String getPermission() {
        return "treasures.admin";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length != 2) {
            player.sendMessage(getSyntax() + " - " + getDescription());
            return;
        }

        Data data = TreasureChests.getPlugin().getDataFile();
        String locationString = data.get().getString("Treasures." + args[1] + ".location");

        if (locationString == null) {
            player.sendMessage("This treasure chest does not exist.");
            return;
        }

        try {
            player.teleport(LocationHandler.getLocationFromString(locationString));
        } catch (NullPointerException e) {
            player.sendMessage("Error whilst teleporting you. ");
        }

        player.sendMessage("§3You have teleported!");


    }

    @Override
    public List<String> getTabSuggester(Player player, int argsLength) {
        return null;
    }
}
