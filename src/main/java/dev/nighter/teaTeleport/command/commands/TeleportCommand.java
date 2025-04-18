package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.storage.LocationManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeleportCommand extends BaseCommand {
    private final LocationManager locationManager;

    public TeleportCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkSender(sender)) {
            return true;
        }

        if (args.length < 1) {
            messageService.sendMessage(sender, "location_tp_usage");
            return true;
        }

        Player player = (Player) sender;
        String locationName = args[0].toLowerCase();

        // Get the location
        Location location = locationManager.getLocation(locationName);

        if (location == null) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", locationName);
            messageService.sendMessage(player, "location_not_found", placeholders);
            return true;
        }

        // Teleport the player
        player.teleportAsync(location);

        // Send success message
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", locationName);
        messageService.sendMessage(player, "location_tp", placeholders);

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.tp";
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