package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.storage.LocationManager;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteCommand extends BaseCommand {
    private final LocationManager locationManager;

    public DeleteCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkSender(sender)) {
            return true;
        }

        if (args.length < 1) {
            messageService.sendMessage(sender, "location_delete_usage");
            return true;
        }

        String locationName = args[0].toLowerCase();

        if (!plugin.getLocationManager().locationExists(locationName)) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", locationName);
            messageService.sendMessage(sender, "location_not_found", placeholders);
            return true;
        }

        // Delete the location
        plugin.getLocationManager().removeLocation(locationName);

        // Send success message
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", locationName);
        messageService.sendMessage(sender, "location_delete", placeholders);

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.delete";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(locationManager.getLocationNames());
        }
        return new ArrayList<>();
    }
}