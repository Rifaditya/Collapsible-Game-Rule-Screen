// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryPrettifierTest {

    @Test
    @DisplayName("Null and empty inputs return empty string")
    void testNullAndEmpty() {
        assertEquals("", CategoryPrettifier.prettifyCategoryKey(null));
        assertEquals("", CategoryPrettifier.prettifyCategoryKey(""));
    }

    @Test
    @DisplayName("Standard vanilla category keys without namespace strip prefix and capitalize")
    void testStandardVanillaKeys() {
        assertEquals("Spawning", CategoryPrettifier.prettifyCategoryKey("gamerule.category.spawning"));
        assertEquals("Drops", CategoryPrettifier.prettifyCategoryKey("gamerule.category.drops"));
        assertEquals("Player", CategoryPrettifier.prettifyCategoryKey("gamerule.category.player"));
        assertEquals("Updates", CategoryPrettifier.prettifyCategoryKey("gamerule.category.updates"));
        assertEquals("Chat", CategoryPrettifier.prettifyCategoryKey("gamerule.category.chat"));
        assertEquals("Misc", CategoryPrettifier.prettifyCategoryKey("gamerule.category.misc"));
    }

    @Test
    @DisplayName("Minecraft namespace prefix is cleanly stripped")
    void testMinecraftNamespace() {
        assertEquals("Spawning", CategoryPrettifier.prettifyCategoryKey("gamerule.category.minecraft.spawning"));
        assertEquals("Player Combat", CategoryPrettifier.prettifyCategoryKey("gamerule.category.minecraft.player_combat"));
    }

    @Test
    @DisplayName("Redundant mod namespace and path are normalized to path")
    void testRedundantModNamespace() {
        assertEquals("Better Bats", CategoryPrettifier.prettifyCategoryKey("gamerule.category.better-bats.better_bats"));
        assertEquals("Collapsible Game Rules", CategoryPrettifier.prettifyCategoryKey("gamerule.category.collapsible_game_rules.collapsible-game-rules"));
    }

    @Test
    @DisplayName("Distinct mod namespace and subcategory are combined and capitalized")
    void testDistinctModNamespaceAndPath() {
        assertEquals("Agrarian Crop Growth", CategoryPrettifier.prettifyCategoryKey("gamerule.category.agrarian.crop_growth"));
        assertEquals("Custom Mod Server Mechanics", CategoryPrettifier.prettifyCategoryKey("gamerule.category.custom_mod.server_mechanics"));
    }

    @Test
    @DisplayName("Hyphens and underscores are treated as word separators with proper capitalization")
    void testDelimitersAndCasing() {
        assertEquals("Natural Reproduction Toggles", CategoryPrettifier.prettifyCategoryKey("gamerule.category.natural_reproduction-toggles"));
        assertEquals("Mob Spawning Rules", CategoryPrettifier.prettifyCategoryKey("gamerule.category.mob__spawning--rules"));
    }
}
