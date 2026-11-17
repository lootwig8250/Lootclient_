package de.lootwig.lootclient;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Stores the small set of client HUD preferences between game launches. */
public final class LootclientConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("lootclient.properties");

    private LootclientConfig() {}

    public static void load() {
        Properties properties = new Properties();
        if (Files.isRegularFile(FILE)) {
            try (InputStream input = Files.newInputStream(FILE)) {
                properties.load(input);
            } catch (IOException exception) {
                System.err.println("[Lootclient] Could not read config: " + exception.getMessage());
            }
        }
        LootclientScreen.showFps = Boolean.parseBoolean(properties.getProperty("showFps", "true"));
        LootclientScreen.showArmor = Boolean.parseBoolean(properties.getProperty("showArmor", "true"));
        LootclientScreen.showSprint = Boolean.parseBoolean(properties.getProperty("showSprint", "true"));
    }

    public static void save() {
        Properties properties = new Properties();
        properties.setProperty("showFps", Boolean.toString(LootclientScreen.showFps));
        properties.setProperty("showArmor", Boolean.toString(LootclientScreen.showArmor));
        properties.setProperty("showSprint", Boolean.toString(LootclientScreen.showSprint));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream output = Files.newOutputStream(FILE)) {
                properties.store(output, "Lootclient settings");
            }
        } catch (IOException exception) {
            System.err.println("[Lootclient] Could not save config: " + exception.getMessage());
        }
    }
}
