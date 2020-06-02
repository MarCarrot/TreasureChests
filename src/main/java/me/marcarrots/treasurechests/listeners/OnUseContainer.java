package me.marcarrots.treasurechests.listeners;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.commands.Delete;
import me.marcarrots.treasurechests.saves.Data;
import me.marcarrots.treasurechests.utils.GetTreasureSet;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class OnUseContainer implements Listener {

    private String getTreasureChest(Data data, Block targetBlock) {
        Set<String> nameList = GetTreasureSet.listTreasures(data);

        if (nameList == null) {
            return null;
        }

        return GetTreasureSet.getTreasureName(data, targetBlock, nameList);

    }

    @EventHandler
    public void onClickChest(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block targetBlock = event.getClickedBlock();

            if (!TreasureChests.getPlugin().getMaterialSet().contains(targetBlock.getType())) {
                return;
            }

            Data data = TreasureChests.getPlugin().getDataFile();
            Player player = event.getPlayer();

            String containerName = getTreasureChest(data, targetBlock);
            if (containerName == null) {
                return;
            }

            Container container = (Container) targetBlock.getState();
            ItemStack handItem = player.getInventory().getItemInMainHand();
            ItemStack itemStack = data.get().getItemStack("Treasures." + containerName + ".key");

            if (itemStack == null) {
                player.sendMessage("You have opened this treasure chest.");
                event.setCancelled(true);
            }

            itemStack.setAmount(1);
            handItem.setAmount(1);

            player.sendMessage(itemStack.toString());
            player.sendMessage(handItem.toString());

            if (!itemStack.equals(handItem)) {
                if (!player.hasPermission("treasures.admin")) {
                    player.sendMessage("This key is locked and requires a special key to open.");
                } else {
                    player.sendMessage("You are now modifying the contents of this chest.");
                }
                return;
            } else {
                player.sendMessage("You have opened this treasure chest with your key.");
                event.setCancelled(true);
            }

            for (int i = 0; i < 27; i++) {
                ItemStack containerItem = container.getInventory().getItem(i);
                if (containerItem != null) {
                    player.getInventory().addItem(containerItem);
                }
            }


        }
    }

    @EventHandler
    public void destroyContainer(BlockBreakEvent event) {

        Block targetBlock = event.getBlock();

        if (!TreasureChests.getPlugin().getMaterialSet().contains(targetBlock.getType())) {
            return;
        }

        Data data = TreasureChests.getPlugin().getDataFile();
        Player player = event.getPlayer();

        String containerName = getTreasureChest(data, targetBlock);
        if (containerName == null) {
            return;
        }

        if (player.hasPermission("Treasurechests.admin")) {
            player.sendMessage("You have destroyed this treasure chest.");
            new Delete().perform(player, new String[]{"delete", containerName});
        } else {
            player.sendMessage("You are not allowed to destroy treasure chests.");
            event.setCancelled(true);
        }

    }

}