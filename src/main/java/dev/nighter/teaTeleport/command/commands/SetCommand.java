package dev.nighter.teaTeleport.command.commands;

import dev.nighter.teaTeleport.TeaTeleport;
import dev.nighter.teaTeleport.storage.LocationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetCommand extends BaseCommand {
    private final LocationManager locationManager;

    public SetCommand(TeaTeleport plugin) {
        super(plugin);
        this.locationManager = plugin.getLocationManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!checkSender(sender)) {
            return true;
        }

        if (args.length < 1) {
            messageService.sendMessage(sender, "location_set_usage");
            return true;
        }

        Player player = (Player) sender;
        String locationName = args[0].toLowerCase();

        // Save the location
        locationManager.addLocation(locationName, player.getLocation());

        // Send success message
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", locationName);
        messageService.sendMessage(player, "location_set", placeholders);

        return true;
    }

    @Override
    public String getPermission() {
        return "teateleport.set";
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        // No tab completion for setting a new location name
        return Collections.emptyList();
    }
}