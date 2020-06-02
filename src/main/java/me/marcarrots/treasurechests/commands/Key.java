package me.marcarrots.treasurechests.commands;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.saves.Data;
import me.marcarrots.treasurechests.utils.GetTreasureSet;
import me.marcarrots.treasurechests.utils.LocationHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Key extends SubCommand {

    @Override
    public String getName() {
        return "key";
    }

    @Override
    public String getDescription() {
        return "Assigns a chest a key that players must have in order to open the chest.";
    }

    @Override
    public String getSyntax() {
        return "/treasurechests key";
    }

    @Override
    public String getPermission() {
        return "treasurechests.key";
    }

    @Override
    public void perform(Player player, String[] args) {

        /*
        args[0] - key
        args[1] - set/get
        args[2] - key name
        args length are 2 or 3.
        */

        if (!player.hasPermission(getPermission())) {
            player.sendMessage("§cYou do not have permission to create treasure chest keys.");
            return;
        }

        if (args.length < 2 || args.length > 3) {
            player.sendMessage(getSyntax() + " - " + getDescription());
            return;
        }

        Data data = TreasureChests.getPlugin().getDataFile();


        if (args[1].equals("set")) {
            setKey(data, player);
        } else if (args[1].equals("get")) {
            if (args.length == 2) {
                getKey(data, player);
            } else {
                getKey(data, player, args[2]);
            }
        } else {
            player.sendMessage("Wrong syntax.");
        }

    }

    @Override
    public List<String> getTabSuggester(Player player, int argsLength) {
        return null;
    }

    private void setKey(Data data, Player player) {
        ItemStack handItem = player.getInventory().getItemInMainHand();
        Block targetBlock = player.getTargetBlockExact(10);

        if (targetBlock == null) {
            return;
        }

        if (handItem.getType() == Material.AIR) {
            player.sendMessage("§cYou must be holding an item in your hand to set it as the key for this chest.");
            return;
        }

        Set<String> nameList = GetTreasureSet.listTreasures(data);

        if (nameList == null) {
            player.sendMessage("§cThere are no treasure chests that have been created.");
            return;
        }

        String containerName = GetTreasureSet.getTreasureName(data, targetBlock, nameList);

        if (containerName == null) {
            player.sendMessage("§cThis key could not be set to this chest.");
            return;
        }

        data.get().set("Treasures." + containerName + ".key", handItem.clone());

        player.sendMessage("This key has been linked to this chest.");
        data.save();

    }

    private void getKey(Data data, Player player) {

        Block targetBlock = player.getTargetBlockExact(10);

        if (targetBlock == null || !TreasureChests.getPlugin().getMaterialSet().contains(targetBlock.getType())) {
            player.sendMessage("This is not a treasure chest. To get a key, use this command while looking at the treasure chest, or use /key get <treasure " +
                    "name>.");
            return;
        }

        Set<String> nameList = GetTreasureSet.listTreasures(data);

        if (nameList == null) {
            player.sendMessage("§cThere are no treasure chests that have been created.");
            return;
        }

        String containerName = GetTreasureSet.getTreasureName(data, targetBlock, nameList);

        if (containerName == null) {
            return;
        }

        getKey(data, player, containerName);


    }

    private void getKey(Data data, Player player, String name) {

        ItemStack itemStack = data.get().getItemStack("Treasures." + name + ".key");

        if (itemStack == null) {
            player.sendMessage("This key could not be found.");
            return;
        }

        player.getInventory().addItem(itemStack);
        player.sendMessage("§3You have obtained a copy of the key for the treasure chest §6" + name + "§3.");


    }

}
