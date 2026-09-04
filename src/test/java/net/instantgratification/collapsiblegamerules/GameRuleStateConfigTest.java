// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameRuleStateConfigTest {

    @BeforeEach
    void setUp() {
        GameRuleStateConfig.resetState();
    }

    @Test
    @DisplayName("Expanding and collapsing categories tracks state and dirtiness")
    void testExpansionAndDirtyTracking() {
        assertFalse(GameRuleStateConfig.isExpanded("spawning"));
        assertFalse(GameRuleStateConfig.isDirty());

        GameRuleStateConfig.setExpanded("spawning", true);
        assertTrue(GameRuleStateConfig.isExpanded("spawning"));
        assertTrue(GameRuleStateConfig.isDirty());

        GameRuleStateConfig.setExpanded("spawning", true); // already true, no dirtiness toggle
        assertEquals(1, GameRuleStateConfig.getExpandedCategoriesCount());

        GameRuleStateConfig.setExpanded("spawning", false);
        assertFalse(GameRuleStateConfig.isExpanded("spawning"));
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount());
    }

    @Test
    @DisplayName("ExpandAll and CollapseAll cleanly update batch keys")
    void testExpandAllAndCollapseAll() {
        GameRuleStateConfig.expandAll(List.of("spawning", "drops", "player"));
        assertEquals(3, GameRuleStateConfig.getExpandedCategoriesCount());
        assertTrue(GameRuleStateConfig.isExpanded("drops"));

        GameRuleStateConfig.collapseAll();
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount());
    }

    @Test
    @DisplayName("Save and load cycle cleanly persists categories to JSON file")
    void testSaveAndLoadCycle(@TempDir Path tempDir) {
        Path configFile = tempDir.resolve("collapsible-game-rules-state.json");

        GameRuleStateConfig.setExpanded("category.spawning", true);
        GameRuleStateConfig.setExpanded("category.player", true);
        GameRuleStateConfig.saveToPath(configFile);

        assertTrue(Files.exists(configFile));

        // Reset memory state and load from file
        GameRuleStateConfig.resetState();
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount());

        GameRuleStateConfig.loadFromPath(configFile);
        assertEquals(2, GameRuleStateConfig.getExpandedCategoriesCount());
        assertTrue(GameRuleStateConfig.isExpanded("category.spawning"));
        assertTrue(GameRuleStateConfig.isExpanded("category.player"));
        assertFalse(GameRuleStateConfig.isDirty());
    }

    @Test
    @DisplayName("Corrupted JSON syntax recovers safely to empty defaults without throwing")
    void testCorruptedJsonRecovery(@TempDir Path tempDir) throws IOException {
        Path configFile = tempDir.resolve("corrupted-config.json");
        Files.writeString(configFile, "INVALID_JSON_CONTENT [{{");

        GameRuleStateConfig.setExpanded("test.key", true);

        // Load corrupted file
        assertDoesNotThrow(() -> GameRuleStateConfig.loadFromPath(configFile));

        // Should cleanly reset to empty defaults
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount());
        assertFalse(GameRuleStateConfig.isExpanded("test.key"));
        assertFalse(GameRuleStateConfig.isDirty());
    }

    @Test
    @DisplayName("Non-existent config file leaves default state unchanged")
    void testNonExistentFile(@TempDir Path tempDir) {
        Path missingFile = tempDir.resolve("non_existent.json");

        GameRuleStateConfig.setExpanded("existing.key", true);
        assertDoesNotThrow(() -> GameRuleStateConfig.loadFromPath(missingFile));

        // State remains as it was
        assertTrue(GameRuleStateConfig.isExpanded("existing.key"));
    }

    @Test
    @DisplayName("Saved JSON contains schemaVersion 1 and expandedCategories array")
    void testVersionedSchemaOutput(@TempDir Path tempDir) throws IOException {
        Path configFile = tempDir.resolve("versioned-config.json");

        GameRuleStateConfig.setExpanded("spawning", true);
        GameRuleStateConfig.saveToPath(configFile);

        String rawJson = Files.readString(configFile);
        assertTrue(rawJson.contains("\"schemaVersion\": 1") || rawJson.contains("\"schemaVersion\":1"));
        assertTrue(rawJson.contains("expandedCategories"));
        assertTrue(rawJson.contains("spawning"));
    }

    @Test
    @DisplayName("Legacy raw JSON array is migrated cleanly and flagged as dirty")
    void testLegacyArrayMigration(@TempDir Path tempDir) throws IOException {
        Path legacyFile = tempDir.resolve("legacy-config.json");
        Files.writeString(legacyFile, "[\"gamerule.category.spawning\", \"gamerule.category.player\"]");

        GameRuleStateConfig.loadFromPath(legacyFile);

        assertEquals(2, GameRuleStateConfig.getExpandedCategoriesCount());
        assertTrue(GameRuleStateConfig.isExpanded("gamerule.category.spawning"));
        assertTrue(GameRuleStateConfig.isExpanded("gamerule.category.player"));
        assertTrue(GameRuleStateConfig.isDirty()); // Flagged for migration rewrite

        // Saving should rewrite it in versioned schema
        GameRuleStateConfig.saveToPath(legacyFile);
        String updatedJson = Files.readString(legacyFile);
        assertTrue(updatedJson.contains("schemaVersion"));
    }

    @Test
    @DisplayName("Modern schema JSON object loads correctly and is clean")
    void testModernSchemaLoad(@TempDir Path tempDir) throws IOException {
        Path modernFile = tempDir.resolve("modern-config.json");
        Files.writeString(modernFile, "{\n  \"schemaVersion\": 1,\n  \"expandedCategories\": [\"spawning\"]\n}");

        GameRuleStateConfig.loadFromPath(modernFile);

        assertEquals(1, GameRuleStateConfig.getExpandedCategoriesCount());
        assertTrue(GameRuleStateConfig.isExpanded("spawning"));
        assertFalse(GameRuleStateConfig.isDirty());
    }
}