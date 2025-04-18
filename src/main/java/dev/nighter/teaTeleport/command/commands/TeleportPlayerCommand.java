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

public class TeleportPlayerCommand extends BaseCommand {
    private final LocationManager locationManager;

    public TeleportPlayerCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkSender(sender)) {
            return true;
        }

        if (args.length < 2) {
            messageService.sendMessage(sender, "teleport_player_usage");
            return true;
        }

        String playerName = args[0];
        String locationName = args[1].toLowerCase();

        // Get target player
        Player targetPlayer = Bukkit.getPlayer(playerName);
        if (targetPlayer == null) {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("player", playerName);
            messageService.sendMessage(sender, "player_not_found", placeholders);
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

        // Teleport the player
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", locationName);
        placeholders.put("player", targetPlayer.getName());

        targetPlayer.teleportAsync(location);

        // Send message to the target player
        messageService.sendMessage(targetPlayer, "teleport_player_target", placeholders);

        // Send success message to command sender
        messageService.sendMessage(sender, "teleport_player_sender", placeholders);

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.tpplayer";
    }

    @Override
    public boolean isPlayerOnly() {
        return false; // Can be used by console
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames;
        } else if (args.length == 2) {
            return new ArrayList<>(locationManager.getLocationNames());
        }
        return new ArrayList<>();
    }
}