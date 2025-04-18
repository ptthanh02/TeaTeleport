package dev.nighter.teaTeleport;

import dev.nighter.teaTeleport.command.CommandManager;
import dev.nighter.teaTeleport.language.LanguageManager;
import dev.nighter.teaTeleport.language.MessageService;
import dev.nighter.teaTeleport.storage.LocationManager;
import dev.nighter.teaTeleport.updates.ConfigUpdater;
import dev.nighter.teaTeleport.updates.LanguageUpdater;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Accessors(chain = false)
public final class TeaTeleport extends JavaPlugin {
    private ConfigUpdater configUpdater;
    private LanguageManager languageManager;
    private LanguageUpdater languageUpdater;
    private MessageService messageService;
    private LocationManager locationManager;
    private CommandManager commandManager;

    @Getter
    private static TeaTeleport instance;

    @Override
    public void onEnable() {
        instance = this;

        // Save default config
        saveDefaultConfig();

        // Initialize language and message services
        this.configUpdater = new ConfigUpdater(this);
        this.configUpdater.checkAndUpdateConfig();
        this.languageManager = new LanguageManager(this, LanguageManager.LanguageFileType.MESSAGES);
        this.languageUpdater = new LanguageUpdater(this, LanguageUpdater.LanguageFileType.MESSAGES);
        this.messageService = new MessageService(this, languageManager);

        // Initialize location manager
        locationManager = new LocationManager(this);

        // Register commands and tab completer
        commandManager = new CommandManager(this);
        commandManager.registerCommands();

        getLogger().info("TeaTeleport plugin enabled successfully!");
    }

    @Override
    public void onDisable() {
        // Save locations when plugin is disabled
        if (locationManager != null) {
            locationManager.saveLocations();
        }

        getLogger().info("TeaTeleport plugin disabled successfully!");
    }
}