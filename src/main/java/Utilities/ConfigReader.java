package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();
    private static boolean loaded = false;

    // Default loader (uses system property or fallback path)
    public static void load() {
        if (loaded) return;

        String configPath = System.getProperty("config.path", "config.properties");
        load(configPath);  // delegate to custom loader
    }

    // Overloaded loader with custom path
    public static void load(String configPath) {
        if (loaded) return;

        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
            loaded = true;
            System.out.println("✅ Loaded config from: " + configPath);
        } catch (IOException e) {
            System.err.println("❌ Failed to load config: " + configPath);
            e.printStackTrace();
        }
    }

    public static String get(String key, String defaultValue) {
        load(); // auto-load if not done
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }

    public static String get(String key) {
        return get(key, null);
    }
}
