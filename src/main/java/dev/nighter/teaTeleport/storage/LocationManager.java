package dev.nighter.teaTeleport.storage;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LocationManager {
    private final JavaPlugin plugin;
    private File locationsFile;
    private FileConfiguration locationsConfig;

    @Getter
    private final Map<String, Location> teleportLocations = new HashMap<>();

    public LocationManager(JavaPlugin plugin) {
        this.plugin = plugin;
        createLocationsFile();
        loadLocations();
    }

    private void createLocationsFile() {
        locationsFile = new File(plugin.getDataFolder(), "locations.yml");
        if (!locationsFile.exists()) {
            locationsFile.getParentFile().mkdirs();
            plugin.saveResource("locations.yml", false);
        }

        locationsConfig = YamlConfiguration.loadConfiguration(locationsFile);
    }

    public void loadLocations() {
        teleportLocations.clear();
        ConfigurationSection locationsSection = locationsConfig.getConfigurationSection("locations");

        if (locationsSection != null) {
            for (String locationName : locationsSection.getKeys(false)) {
                ConfigurationSection locationSection = locationsSection.getConfigurationSection(locationName);
                if (locationSection != null) {
                    String worldName = locationSection.getString("world");
                    World world = Bukkit.getWorld(worldName);

                    if (world != null) {
                        double x = locationSection.getDouble("x");
                        double y = locationSection.getDouble("y");
                        double z = locationSection.getDouble("z");
                        float yaw = (float) locationSection.getDouble("yaw");
                        float pitch = (float) locationSection.getDouble("pitch");

                        Location location = new Location(world, x, y, z, yaw, pitch);
                        teleportLocations.put(locationName.toLowerCase(), location);
                        plugin.getLogger().info("Loaded teleport location: " + locationName);
                    } else {
                        plugin.getLogger().warning("World " + worldName + " not found for location: " + locationName);
                    }
                }
            }
        }
    }

    public void saveLocations() {
        // Clear existing locations in config
        locationsConfig.set("locations", null);

        // Create a new section for locations
        ConfigurationSection locationsSection = locationsConfig.createSection("locations");

        // Save each location
        for (Map.Entry<String, Location> entry : teleportLocations.entrySet()) {
            String locationName = entry.getKey();
            Location location = entry.getValue();

            ConfigurationSection locationSection = locationsSection.createSection(locationName);
            locationSection.set("world", location.getWorld().getName());
            locationSection.set("x", location.getX());
            locationSection.set("y", location.getY());
            locationSection.set("z", location.getZ());
            locationSection.set("yaw", location.getYaw());
            locationSection.set("pitch", location.getPitch());
        }

        // Save the config to file
        try {
            locationsConfig.save(locationsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save locations to file!");
            e.printStackTrace();
        }
    }

    public boolean addLocation(String name, Location location) {
        String lowerName = name.toLowerCase();
        teleportLocations.put(lowerName, location.clone());
        saveLocations();
        return true;
    }

    public boolean removeLocation(String name) {
        String lowerName = name.toLowerCase();
        if (teleportLocations.containsKey(lowerName)) {
            teleportLocations.remove(lowerName);
            saveLocations();
            return true;
        }
        return false;
    }

    public Location getLocation(String name) {
        return teleportLocations.get(name.toLowerCase());
    }

    public boolean locationExists(String name) {
        return teleportLocations.containsKey(name.toLowerCase());
    }

    public Set<String> getLocationNames() {
        return teleportLocations.keySet();
    }
}