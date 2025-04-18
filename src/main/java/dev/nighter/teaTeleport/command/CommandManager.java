package dev.nighter.teaTeleport.command;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.command.commands.*;
import dev.nighter.teaTeleport.language.MessageService;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private final TeaTeleport plugin;
    @Getter
    private final Map<String, BaseCommand> commands = new HashMap<>();
    private final TabCompleter tabCompleter;
    private final MessageService messageService;

    public CommandManager(TeaTeleport plugin) {
        this.plugin = plugin;
        this.tabCompleter = new TabCompleter(this);
        this.messageService = plugin.getMessageService();
    }

    public void registerCommands() {
        // Register subcommands
        registerCommand("set", new SetCommand(plugin));
        registerCommand("tp", new TeleportCommand(plugin));
        registerCommand("tpall", new TeleportAllCommand(plugin));
        registerCommand("tpplayer", new TeleportPlayerCommand(plugin));
        registerCommand("worldtp", new WorldTeleportCommand(plugin));
        registerCommand("list", new ListCommand(plugin));
        registerCommand("delete", new DeleteCommand(plugin));
        registerCommand("help", new HelpCommand(plugin));
        registerCommand("reload", new ReloadCommand(plugin)); // Add the reload command

        // Register main command handler
        PluginCommand mainCommand = plugin.getCommand("teateleport");
        if (mainCommand != null) {
            mainCommand.setExecutor(this);
            mainCommand.setTabCompleter(tabCompleter);
        }
    }

    private void registerCommand(String name, BaseCommand command) {
        commands.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (args.length == 0) {
            // No arguments, show help
            commands.get("help").execute(sender, args);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        BaseCommand command = commands.get(subCommand);

        if (command == null) {
            // Unknown subcommand, show error message
            messageService.sendMessage(sender, "error_invalid_command");
            return true;
        }

        // Remove the subcommand from args array
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, args.length - 1);

        // Execute the subcommand
        return command.execute(sender, subArgs);
    }

}