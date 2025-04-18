package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends BaseCommand {

    public HelpCommand(TeaTeleport plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        messageService.sendMessage(sender, "help_message");
        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.use";
    }

    @Override
    public boolean isPlayerOnly() {
        return false; // Help can be shown to console as well
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>(); // No tab completion for help command
    }
}