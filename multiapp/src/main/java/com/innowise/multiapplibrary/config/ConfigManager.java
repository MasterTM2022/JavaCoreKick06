package com.innowise.multiapplibrary.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Logger log = LogManager.getLogger(ConfigManager.class);
    private final Properties properties = new Properties();

    // private constructor
    private ConfigManager() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) {
                properties.load(is);
            }
            else {
                log.warn("config.properties not found");
            }
        } catch (IOException e) {
            log.error("Failed to load config", e);
        }
    }

    // Holder Idiom (thread-safe Singleton without volatile/synchronized)
    private static class Holder {
        static final ConfigManager INSTANCE = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        return Holder.INSTANCE;
    }

    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }
}
