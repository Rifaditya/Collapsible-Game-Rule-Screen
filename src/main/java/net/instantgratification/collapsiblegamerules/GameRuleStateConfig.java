/*
 * Sovereign Engineering
 * Verified against: FabricLoader.java (26.*)
 */
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

    public static void load() {
        loadFromPath(getDefaultConfigFile());
    }

    public static void loadFromPath(Path path) {
        if (path == null || !Files.exists(path)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(path)) {
            Set<String> loaded = GSON.fromJson(reader, new TypeToken<Set<String>>(){}.getType());
            expandedCategories = (loaded != null) ? loaded : new HashSet<>();
            isDirty = false;
        } catch (com.google.gson.JsonSyntaxException e) {
            LOGGER.warn("Corrupted JSON syntax detected in config file '{}', resetting to default state: {}", path, e.getMessage());
            expandedCategories = new HashSet<>();
            isDirty = false;
        } catch (IOException e) {
            LOGGER.error("Failed to load Collapsible GameRules state from '{}'", path, e);
        }
    }

    public static void save() {
        saveToPath(getDefaultConfigFile());
    }

    public static void saveToPath(Path path) {
        if (path == null) {
            return;
        }
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(expandedCategories, writer);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save Collapsible GameRules state to '{}'", path, e);
        }
    }

    public static void saveIfDirty() {
        if (isDirty) {
            save();
            isDirty = false;
        }
    }

    public static boolean isExpanded(String categoryKey) {
        return expandedCategories.contains(categoryKey);
    }

    public static void setExpanded(String categoryKey, boolean expanded) {
        if (expanded) {
            if (expandedCategories.add(categoryKey)) isDirty = true;
        } else {
            if (expandedCategories.remove(categoryKey)) isDirty = true;
        }
    }
    
    public static void expandAll(Iterable<String> allKeys) {
        for (String key : allKeys) {
            if (expandedCategories.add(key)) isDirty = true;
        }
    }

    public static void collapseAll() {
        if (!expandedCategories.isEmpty()) {
            expandedCategories.clear();
            isDirty = true;
        }
    }

    public static int getExpandedCategoriesCount() {
        return expandedCategories.size();
    }

    public static boolean isDirty() {
        return isDirty;
    }

    public static void resetState() {
        expandedCategories.clear();
        isDirty = false;
    }
}
