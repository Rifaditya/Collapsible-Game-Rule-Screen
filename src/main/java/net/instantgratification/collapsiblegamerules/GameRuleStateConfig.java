// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class GameRuleStateConfig {
    private static Path getDefaultConfigFile() {
        try {
            if (FabricLoader.getInstance() != null && FabricLoader.getInstance().getConfigDir() != null) {
                return FabricLoader.getInstance().getConfigDir().resolve("collapsible-game-rules-state.json");
            }
        } catch (Throwable ignored) {
            // Headless unit test environment where FabricLoader is uninitialized
        }
        return Path.of("config", "collapsible-game-rules-state.json");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    // Set of category translation keys that are currently EXPANDED
    private static Set<String> expandedCategories = new HashSet<>();
    private static boolean isDirty = false;

    public static final int CURRENT_SCHEMA_VERSION = 1;

    public static class ConfigData {
        public int schemaVersion = CURRENT_SCHEMA_VERSION;
        public Set<String> expandedCategories = new HashSet<>();

        public ConfigData() {}

        public ConfigData(int schemaVersion, Set<String> expandedCategories) {
            this.schemaVersion = schemaVersion;
            this.expandedCategories = expandedCategories != null ? expandedCategories : new HashSet<>();
        }
    }

    /**
     * Deserializes JSON string directly into memory state with Freedom Over Anti-Crash fallback.
     */
    public static synchronized void deserializeJson(String json) {
        if (json == null || json.isBlank()) {
            resetState();
            return;
        }
        try {
            com.google.gson.JsonElement root = com.google.gson.JsonParser.parseString(json);
            if (root == null || root.isJsonNull()) {
                resetState();
            } else if (root.isJsonArray()) {
                // Legacy raw set format e.g. ["spawning", "player"]
                Set<String> legacy = GSON.fromJson(root, new TypeToken<Set<String>>(){}.getType());
                expandedCategories = (legacy != null) ? legacy : new HashSet<>();
                isDirty = true; // Flag for automatic migration to versioned schema
            } else if (root.isJsonObject()) {
                ConfigData data = GSON.fromJson(root, ConfigData.class);
                if (data != null && data.expandedCategories != null) {
                    expandedCategories = new HashSet<>(data.expandedCategories);
                } else {
                    resetState();
                }
                isDirty = false;
            } else {
                resetState();
            }
        } catch (com.google.gson.JsonSyntaxException | IllegalArgumentException e) {
            LOGGER.warn("Corrupted JSON syntax detected during deserialization, resetting to default state: {}", e.getMessage());
            resetState();
        }
    }

    public static synchronized void load() {
        loadFromPath(getDefaultConfigFile());
    }

    public static synchronized void loadFromPath(Path path) {
        if (path == null || !Files.exists(path)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            com.google.gson.JsonElement root = com.google.gson.JsonParser.parseReader(reader);
            if (root == null || root.isJsonNull()) {
                expandedCategories = new HashSet<>();
                isDirty = false;
            } else if (root.isJsonArray()) {
                // Legacy raw set format e.g. ["spawning", "player"]
                Set<String> legacy = GSON.fromJson(root, new TypeToken<Set<String>>(){}.getType());
                expandedCategories = (legacy != null) ? legacy : new HashSet<>();
                isDirty = true; // Flag for automatic migration to versioned schema
            } else if (root.isJsonObject()) {
                ConfigData data = GSON.fromJson(root, ConfigData.class);
                if (data != null && data.expandedCategories != null) {
                    expandedCategories = new HashSet<>(data.expandedCategories);
                } else {
                    expandedCategories = new HashSet<>();
                }
                isDirty = false;
            } else {
                expandedCategories = new HashSet<>();
                isDirty = false;
            }
        } catch (com.google.gson.JsonSyntaxException | IllegalArgumentException e) {
            LOGGER.warn("Corrupted JSON syntax detected in config file '{}', resetting to default state: {}", path, e.getMessage());
            expandedCategories = new HashSet<>();
            isDirty = false;
        } catch (IOException e) {
            LOGGER.error("Failed to load Collapsible GameRules state from '{}'", path, e);
        }
    }

    public static synchronized void save() {
        saveToPath(getDefaultConfigFile());
    }

    public static synchronized void saveToPath(Path path) {
        if (path == null) {
            return;
        }
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (Writer writer = Files.newBufferedWriter(path)) {
                ConfigData data = new ConfigData(CURRENT_SCHEMA_VERSION, new HashSet<>(expandedCategories));
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save Collapsible GameRules state to '{}'", path, e);
        }
    }

    public static synchronized void saveIfDirty() {
        if (isDirty) {
            save();
            isDirty = false;
        }
    }

    public static synchronized boolean isExpanded(String categoryKey) {
        return expandedCategories.contains(categoryKey);
    }

    public static synchronized void setExpanded(String categoryKey, boolean expanded) {
        if (expanded) {
            if (expandedCategories.add(categoryKey)) isDirty = true;
        } else {
            if (expandedCategories.remove(categoryKey)) isDirty = true;
        }
    }

    public static synchronized boolean isCategoryCollapsed(String categoryKey) {
        return !isExpanded(categoryKey);
    }

    public static synchronized void setCategoryCollapsed(String categoryKey, boolean collapsed) {
        setExpanded(categoryKey, !collapsed);
    }
    
    public static synchronized void expandAll(Iterable<String> allKeys) {
        for (String key : allKeys) {
            if (expandedCategories.add(key)) isDirty = true;
        }
    }

    public static synchronized void collapseAll() {
        if (!expandedCategories.isEmpty()) {
            expandedCategories.clear();
            isDirty = true;
        }
    }

    public static synchronized int getExpandedCategoriesCount() {
        return expandedCategories.size();
    }

    public static synchronized boolean isDirty() {
        return isDirty;
    }

    public static synchronized void resetState() {
        expandedCategories.clear();
        isDirty = false;
    }
}
