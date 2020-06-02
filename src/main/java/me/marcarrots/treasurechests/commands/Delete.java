package me.marcarrots.treasurechests.commands;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.saves.Data;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class Delete extends SubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletes a treasure chest.";
    }

    @Override
    public String getSyntax() {
        return "/treasurechests delete <treasure chest name>";
    }

    @Override
    public String getPermission() {
        return "treasurechests.admin";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (!player.hasPermission(getPermission())) {
            player.sendMessage("§cYou do not have permission to create treasure chest keys.");
            return;
        }

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

        data.get().set("Treasures." + args[1], null);
        player.sendMessage("§cThe treasure chest §4" + args[1] + " §chas been deleted.");
        data.save();

    }

    @Override
    public List<String> getTabSuggester(Player player, int argsLength) {
        return null;
    }
}
