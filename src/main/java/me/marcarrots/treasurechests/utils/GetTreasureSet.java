package me.marcarrots.treasurechests.utils;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.saves.Data;
import org.bukkit.block.Block;

import java.util.Set;

public class GetTreasureSet {

    public static Set<String> listTreasures(Data data) {
        Set<String> nameList;

        try {
            nameList = data.get().getConfigurationSection("Treasures").getKeys(false);
        } catch (NullPointerException e) {
            return null;
        }


        return nameList;
    }

    public static String getTreasureName(Data data, Block block, Set<String> treasureList) {
        String containerName = null;

        for (String name : treasureList) {
            if (LocationHandler.getStringFromLocation(block.getLocation()).equals(data.get().getString("Treasures." + name + ".location"))) {
                containerName = name;
                break;
            }
        }

        return containerName;
    }

}
