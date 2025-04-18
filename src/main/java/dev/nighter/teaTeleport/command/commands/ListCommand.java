package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.storage.LocationManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListCommand extends BaseCommand {
    private final LocationManager locationManager;

    public ListCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkSender(sender)) {
            return true;
        }

        Map<String, Location> locations = locationManager.getTeleportLocations();

        if (locations.isEmpty()) {
            messageService.sendMessage(sender, "location_list_empty");
            return true;
        }

        // Send header
        messageService.sendMessage(sender, "location_list_header");

        // Send each location
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            String name = entry.getKey();
            Location loc = entry.getValue();

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", name);
            placeholders.put("world", loc.getWorld().getName());
            placeholders.put("x", String.format("%.1f", loc.getX()));
            placeholders.put("y", String.format("%.1f", loc.getY()));
            placeholders.put("z", String.format("%.1f", loc.getZ()));

            messageService.sendMessage(sender, "location_list_entry", placeholders);
        }

        // Send footer
        messageService.sendMessage(sender, "location_list_footer");

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.list";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>(); // No arguments for list command
    }
}