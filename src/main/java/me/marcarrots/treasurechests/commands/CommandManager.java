package me.marcarrots.treasurechests.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();


    public CommandManager() {
        subCommands.add(new Create());
        subCommands.add(new Key());
        subCommands.add(new Teleport());
        subCommands.add(new ListChests());
        subCommands.add(new Delete());
    }


    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        for (int i = 0; i < getSubCommands().size(); i++) {
            if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                getSubCommands().get(i).perform((Player) sender, args);
                return true;
            }
        }



        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
