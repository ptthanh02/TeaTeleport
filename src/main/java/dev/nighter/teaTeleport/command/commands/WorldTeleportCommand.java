package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.storage.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldTeleportCommand extends BaseCommand {
    private final LocationManager locationManager;

    public WorldTeleportCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            messageService.sendMessage(sender, "world_teleport_usage");
            return true;
        }

        String worldName = args[0];
        String locationName = args[1].toLowerCase();

        // Check if world exists
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("world", worldName);
            messageService.sendMessage(sender, "world_not_found", placeholders);
            return true;
        }

        // Get the location
        Location location = locationManager.getLocation(locationName);

        if (location == null) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("name", locationName);
            messageService.sendMessage(sender, "location_not_found", placeholders);
            return true;
        }

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", locationName);
        placeholders.put("world", worldName);

        // Teleport all players in the specified world
        int count = 0;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getWorld().equals(world)) {
                onlinePlayer.teleportAsync(location);
                messageService.sendMessage(onlinePlayer, "world_teleport_players", placeholders);
                count++;
            }
        }

        // Send success message
        placeholders.put("count", String.valueOf(count));
        messageService.sendMessage(sender, "world_teleport_user", placeholders);

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.worldtp";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Return a list of all worlds
            for (World world : Bukkit.getWorlds()) {
                completions.add(world.getName());
            }
        } else if (args.length == 2) {
            // Return a list of teleport locations
            completions.addAll(locationManager.getLocationNames());
        }

        return completions;
    }
}