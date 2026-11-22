package io.github.derexxd.customminecartspeeds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private CustomMinecartSpeeds plugin;

    public CommandManager(CustomMinecartSpeeds plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("customminecartspeeds.reload")) {
                sender.sendMessage("§cYou do not have permission to run this command.");
                return true;
            }
            plugin.reloadConfig();
            plugin.loadSpeeds();
            sender.sendMessage("§a[Custom-Minecart-Speeds] Configuration reloaded.");
            return true;
        }
        sender.sendMessage("§e[Custom-Minecart-Speeds] Use /" + label + " reload to reload configuration.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && "reload".startsWith(args[0].toLowerCase())) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
