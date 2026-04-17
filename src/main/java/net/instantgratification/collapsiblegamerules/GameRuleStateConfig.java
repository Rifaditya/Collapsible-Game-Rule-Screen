/*
 * Zenith Sovereign Engineering
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
    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("collapsible-game-rules-state.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    // Set of category translation keys that are currently EXPANDED
    private static Set<String> expandedCategories = new HashSet<>();
    private static boolean isDirty = false;

    public static void load() {
        if (!Files.exists(CONFIG_FILE)) {
            return;
        }
        try (Reader reader = Files.newBufferedReader(CONFIG_FILE)) {
            expandedCategories = GSON.fromJson(reader, new TypeToken<Set<String>>(){}.getType());
            if (expandedCategories == null) {
                expandedCategories = new HashSet<>();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load Collapsible GameRules state", e);
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
                GSON.toJson(expandedCategories, writer);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save Collapsible GameRules state", e);
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
}
