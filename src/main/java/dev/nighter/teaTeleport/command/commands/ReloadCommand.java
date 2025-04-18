package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(TeaTeleport plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkSender(sender)) {
            return true;
        }

        // Reload language files
        plugin.getLanguageManager().reloadLanguages();

        // Clear message service cache if applicable
        messageService.clearKeyExistsCache();

        // Reload locations
        plugin.getLocationManager().loadLocations();

        // Send success message
        messageService.sendMessage(sender, "command_reload_success");

        return true;
    }


    private void logCacheStats() {
        Map<String, Object> stats = plugin.getLanguageManager().getCacheStats();
        plugin.getLogger().info("Language cache statistics:");
        for (Map.Entry<String, Object> entry : stats.entrySet()) {
            plugin.getLogger().info("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public String getPermission() {
        return "teateleport.reload";
    }

    @Override
    public boolean isPlayerOnly() {
        return false; // Allow console to reload the plugin
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>(); // No tab completion needed for reload
    }
}