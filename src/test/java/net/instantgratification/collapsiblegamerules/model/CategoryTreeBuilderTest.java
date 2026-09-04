// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.model;

import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTreeBuilderTest {

    @Test
    @DisplayName("Empty raw entries return empty CategoryGroup list")
    void testEmptyRawEntries() {
        List<CategoryGroup> emptyNull = CategoryTreeBuilder.buildGroups(null);
        assertNotNull(emptyNull);
        assertTrue(emptyNull.isEmpty());

        List<CategoryGroup> emptyList = CategoryTreeBuilder.buildGroups(Collections.emptyList());
        assertNotNull(emptyList);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    @DisplayName("CategoryGroup maintains immutable rules and O(1) rule count")
    void testCategoryGroupImmutabilityAndCount() {
        Component label = Component.literal("Player");
        String key = "gamerule.category.player";
        CategoryGroup group = new CategoryGroup(label, key, Collections.emptyList());

        assertEquals(0, group.ruleCount());
        assertEquals("gamerule.category.player", group.persistenceKey());
        assertEquals("Player", group.displayLabel().getString());
        assertThrows(UnsupportedOperationException.class, () -> group.rules().add(null));
    }

    @Test
    @DisplayName("CategoryGroup with multiple rules accurately reflects ruleCount")
    void testCategoryGroupWithMultipleRules() {
        Component label = Component.literal("Spawning");
        String key = "gamerule.category.spawning";
        // Create 3 dummy RuleEntry mocks/subclasses if needed, or non-null references
        CategoryGroup group = new CategoryGroup(label, key, List.of(
                new DummyRuleEntry(),
                new DummyRuleEntry(),
                new DummyRuleEntry()
        ));

        assertEquals(3, group.ruleCount());
        assertEquals(3, group.rules().size());
    }

    /**
     * Minimal concrete stub of RuleEntry for testing list containment and counts.
     */
    private static class DummyRuleEntry extends AbstractGameRulesScreen.RuleEntry {
        public DummyRuleEntry() {
            super(null);
        }

        @Override
        public List<? extends net.minecraft.client.gui.components.events.GuiEventListener> children() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends net.minecraft.client.gui.narration.NarratableEntry> narratables() {
            return Collections.emptyList();
        }

        @Override
        public void extractContent(net.minecraft.client.gui.GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            // No-op for unit tests
        }
    }
}
