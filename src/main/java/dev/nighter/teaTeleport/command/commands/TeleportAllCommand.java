package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.storage.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeleportAllCommand extends BaseCommand {
    private final LocationManager locationManager;

    public TeleportAllCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();

    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            messageService.sendMessage(sender, "teleport_all_usage");
            return true;
        }

        String locationName = args[0].toLowerCase();

        // Get the location
        Location location = locationManager.getLocation(locationName);

        if (location == null) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", locationName);
            messageService.sendMessage(sender, "location_not_found", placeholders);
            return true;
        }

        // Teleport all players
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", locationName);

        int count = 0;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.teleportAsync(location);
            messageService.sendMessage(onlinePlayer, "teleport_all_players", placeholders);
            count++;
        }

        // Send success message
        placeholders.put("count", String.valueOf(count));
        messageService.sendMessage(sender, "teleport_all_user", placeholders);

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.tpall";
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