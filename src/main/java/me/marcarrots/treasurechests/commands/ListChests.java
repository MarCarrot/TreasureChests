package me.marcarrots.treasurechests.commands;

import me.marcarrots.treasurechests.TreasureChests;
import me.marcarrots.treasurechests.saves.Data;
import me.marcarrots.treasurechests.utils.LocationHandler;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class ListChests extends SubCommand {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Gives the list of treasures.";
    }

    @Override
    public String getSyntax() {
        return "/treasurechests list";
    }

    @Override
    public String getPermission() {
        return "treasures.list";
    }

    @Override
    public void perform(Player player, String[] args) {
        Data data = TreasureChests.getPlugin().getDataFile();
        listKey(data, player);
    }

    @Override
    public java.util.List<String> getTabSuggester(Player player, int argsLength) {
        return null;
    }

    private void listKey(Data data, Player player) {

        Set<String> nameList;

        try {
            nameList = data.get().getConfigurationSection("Treasures").getKeys(false);
        } catch (NullPointerException e) {
            player.sendMessage("§cThere are no keys because no treasure chests that have been created.");
            return;
        }

        if (nameList.size() == 0) {
            player.sendMessage("§cThere are currently no treasure chests.");
            return;
        }

        for (String name : nameList) {


            TextComponent location = new TextComponent(TextComponent.fromLegacyText("§a[Teleport]"));
            TextComponent key = new TextComponent(TextComponent.fromLegacyText("§d[Get Key]"));
            TextComponent delete = new TextComponent(TextComponent.fromLegacyText("§c[Delete]"));


            location.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/treasurechests tp " + name));
            key.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/treasurechests key get " + name));
            delete.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/treasurechests delete " + name));

            BaseComponent[] componentBuilder = (new ComponentBuilder()
                    .append("§6 ● §3")
                    .append(name))
                    .append(":   ")
                    .append(location)
                    .append("    ")
                    .append(key)
                    .append("    ")
                    .append(delete)
                    .create();

            player.spigot().sendMessage(componentBuilder);
        }

    }


}
