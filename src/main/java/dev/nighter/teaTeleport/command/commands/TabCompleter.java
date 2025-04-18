package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.command.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private final CommandManager commandManager;

    public TabCompleter(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // First argument - show available subcommands the user has permission for
            List<String> subCommands = new ArrayList<>();
            for (Map.Entry<String, BaseCommand> entry : commandManager.getCommands().entrySet()) {
                if (entry.getValue().hasPermission(sender)) {
                    subCommands.add(entry.getKey());
                }
            }
            StringUtil.copyPartialMatches(args[0], subCommands, completions);
        } else if (args.length >= 2) {
            // Get the subcommand
            String subCommandName = args[0].toLowerCase();
            BaseCommand subCommand = commandManager.getCommands().get(subCommandName);

            if (subCommand != null && subCommand.hasPermission(sender)) {
                // Create a new array with the subcommand removed
                String[] subArgs = new String[args.length - 1];
                System.arraycopy(args, 1, subArgs, 0, args.length - 1);

                // Get completions from the subcommand
                List<String> subCompletions = subCommand.tabComplete(sender, subArgs);
                if (subCompletions != null) {
                    StringUtil.copyPartialMatches(args[args.length - 1], subCompletions, completions);
                }
            }
        }

        Collections.sort(completions);
        return completions;
    }
}