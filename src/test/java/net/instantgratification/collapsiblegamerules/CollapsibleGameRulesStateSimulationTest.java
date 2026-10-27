// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import net.instantgratification.collapsiblegamerules.model.CategoryGroup;
import net.instantgratification.collapsiblegamerules.model.ResettableRuleEntry;
import net.instantgratification.collapsiblegamerules.util.CategoryResetHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CollapsibleGameRulesStateSimulationTest: Pillar 4 Headless State Machine & Client GUI Simulation Suite.
 *
 * Verifies headless client simulation without GLFW, OpenGL, or active display context:
 * - Category collapse and expansion transitions and dirtiness tracking.
 * - Global bulk actions state machine (Expand All / Collapse All idempotency).
 * - Live search query filtering, badge match computation, and visible entry layout recalculation.
 * - Category reset state machine, deviation detection, and revert transitions.
 */
@DisplayName("Pillar 4: Collapsible Game Rules Headless State Machine Simulation Tests")
public class CollapsibleGameRulesStateSimulationTest {

    @BeforeEach
    void setUp() {
        GameRuleStateConfig.resetState();
    }

    @Test
    @DisplayName("Verify category collapse and expand state transitions via setCategoryCollapsed")
    void testCategoryCollapseExpandStateMachine() {
        String spawningKey = "gamerule.category.spawning";
        String playerKey = "gamerule.category.player";

        // Initial default state: untracked categories are collapsed by default
        assertTrue(GameRuleStateConfig.isCategoryCollapsed(spawningKey),
                "Category must be collapsed by default");
        assertFalse(GameRuleStateConfig.isExpanded(spawningKey),
                "Category must not be expanded by default");
        assertFalse(GameRuleStateConfig.isDirty(),
                "State should not be dirty upon initial clean setup");

        // Transition 1: Expand 'spawning' (set collapsed = false)
        GameRuleStateConfig.setCategoryCollapsed(spawningKey, false);
        assertFalse(GameRuleStateConfig.isCategoryCollapsed(spawningKey),
                "Category must be uncollapsed after setting collapsed to false");
        assertTrue(GameRuleStateConfig.isExpanded(spawningKey),
                "Category must be marked expanded");
        assertTrue(GameRuleStateConfig.isDirty(),
                "Dirty flag must be set after state mutation");
        assertEquals(1, GameRuleStateConfig.getExpandedCategoriesCount(),
                "Expanded category count must equal 1");

        // Transition 2: Expand 'player'
        GameRuleStateConfig.setCategoryCollapsed(playerKey, false);
        assertFalse(GameRuleStateConfig.isCategoryCollapsed(playerKey));
        assertTrue(GameRuleStateConfig.isExpanded(playerKey));
        assertEquals(2, GameRuleStateConfig.getExpandedCategoriesCount());

        // Idempotency: setting the same state again should not alter count
        GameRuleStateConfig.setCategoryCollapsed(playerKey, false);
        assertEquals(2, GameRuleStateConfig.getExpandedCategoriesCount());

        // Transition 3: Re-collapse 'spawning' (set collapsed = true)
        GameRuleStateConfig.setCategoryCollapsed(spawningKey, true);
        assertTrue(GameRuleStateConfig.isCategoryCollapsed(spawningKey),
                "Category must be collapsed after setting collapsed to true");
        assertFalse(GameRuleStateConfig.isExpanded(spawningKey),
                "Category must no longer be expanded");
        assertEquals(1, GameRuleStateConfig.getExpandedCategoriesCount(),
                "Expanded count must decrease to 1");

        // Transition 4: Re-collapse 'player'
        GameRuleStateConfig.setCategoryCollapsed(playerKey, true);
        assertTrue(GameRuleStateConfig.isCategoryCollapsed(playerKey));
        assertFalse(GameRuleStateConfig.isExpanded(playerKey));
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount(),
                "Expanded count must be 0 after collapsing all");
    }

    @Test
    @DisplayName("Verify bulk collapse and bulk expand actions update all categories deterministically")
    void testGlobalBulkActionsStateMachine() {
        List<String> categories = List.of(
                "gamerule.category.spawning",
                "gamerule.category.player",
                "gamerule.category.drops",
                "gamerule.category.updates",
                "gamerule.category.chat",
                "gamerule.category.misc"
        );

        // Verify initial state: all categories collapsed
        for (String key : categories) {
            assertTrue(GameRuleStateConfig.isCategoryCollapsed(key),
                    "Category " + key + " should initially be collapsed");
        }
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount());

        // 1. Bulk Expand All
        GameRuleStateConfig.expandAll(categories);
        for (String key : categories) {
            assertFalse(GameRuleStateConfig.isCategoryCollapsed(key),
                    "Category " + key + " must be expanded after bulk expand");
            assertTrue(GameRuleStateConfig.isExpanded(key));
        }
        assertEquals(categories.size(), GameRuleStateConfig.getExpandedCategoriesCount(),
                "All categories must be accounted for in expanded count");
        assertTrue(GameRuleStateConfig.isDirty());

        // 2. Bulk Collapse All
        GameRuleStateConfig.collapseAll();
        for (String key : categories) {
            assertTrue(GameRuleStateConfig.isCategoryCollapsed(key),
                    "Category " + key + " must be collapsed after bulk collapse");
            assertFalse(GameRuleStateConfig.isExpanded(key));
        }
        assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount(),
                "Expanded count must be 0 after bulk collapse");

        // 3. Multi-cycle stress simulation (idempotency & determinism)
        for (int cycle = 0; cycle < 5; cycle++) {
            GameRuleStateConfig.expandAll(categories);
            assertEquals(categories.size(), GameRuleStateConfig.getExpandedCategoriesCount());

            GameRuleStateConfig.collapseAll();
            assertEquals(0, GameRuleStateConfig.getExpandedCategoriesCount());
        }
    }

    @Test
    @DisplayName("Verify search query filtering on CategoryGroup list calculates matches and visible items")
    void testSearchQueryFilteringStateMachine() {
        // Construct headless mock rules
        SimulatedRuleEntry spawningRule1 = new SimulatedRuleEntry("doMobSpawning");
        SimulatedRuleEntry spawningRule2 = new SimulatedRuleEntry("doPatrolSpawning");
        SimulatedRuleEntry spawningRule3 = new SimulatedRuleEntry("spawnRadius");

        SimulatedRuleEntry playerRule1 = new SimulatedRuleEntry("keepInventory");
        SimulatedRuleEntry playerRule2 = new SimulatedRuleEntry("naturalRegeneration");

        SimulatedRuleEntry dropsRule1 = new SimulatedRuleEntry("doEntityDrops");
        SimulatedRuleEntry dropsRule2 = new SimulatedRuleEntry("doTileDrops");
        SimulatedRuleEntry dropsRule3 = new SimulatedRuleEntry("doMobLoot");

        CategoryGroup spawningGroup = new CategoryGroup(
                Component.literal("Spawning"),
                "gamerule.category.spawning",
                List.of(spawningRule1, spawningRule2, spawningRule3)
        );

        CategoryGroup playerGroup = new CategoryGroup(
                Component.literal("Player"),
                "gamerule.category.player",
                List.of(playerRule1, playerRule2)
        );

        CategoryGroup dropsGroup = new CategoryGroup(
                Component.literal("Drops"),
                "gamerule.category.drops",
                List.of(dropsRule1, dropsRule2, dropsRule3)
        );

        List<CategoryGroup> allGroups = List.of(spawningGroup, playerGroup, dropsGroup);

        // --- Scenario 1: Empty search query (Normal view) ---
        // Player group is expanded, Spawning and Drops are collapsed
        GameRuleStateConfig.setCategoryCollapsed(playerGroup.persistenceKey(), false);
        GameRuleStateConfig.setCategoryCollapsed(spawningGroup.persistenceKey(), true);
        GameRuleStateConfig.setCategoryCollapsed(dropsGroup.persistenceKey(), true);

        int normalVisibleCount = computeVisibleItemCount(allGroups, "");
        // 1 spawning header + (1 player header + 2 player rules) + 1 drops header = 5
        assertEquals(5, normalVisibleCount,
                "Normal visible items must include all headers plus rules of expanded categories");

        for (CategoryGroup group : allGroups) {
            assertEquals("[" + group.ruleCount() + " rules]", group.countBadge().getString(),
                    "Default count badge should reflect total rule count");
        }

        // --- Scenario 2: Active search query with multiple matches ("spawn") ---
        String query = "spawn";
        List<CategoryGroup> searchResults = filterCategoryGroups(allGroups, query);

        // 'spawning' group has 3 matches (doMobSpawning, doPatrolSpawning, spawnRadius)
        // 'player' and 'drops' have 0 matches
        CategoryGroup matchedSpawning = searchResults.stream()
                .filter(g -> g.persistenceKey().equals("gamerule.category.spawning"))
                .findFirst().orElseThrow();
        assertEquals(3, matchedSpawning.ruleCount());
        assertEquals("[● 3 matches]", matchedSpawning.countBadge().getString(),
                "Badge should show plural match text");

        // During search, matching categories are forced expanded: 1 header + 3 rules = 4
        int searchVisibleCount = computeVisibleItemCount(searchResults, query);
        assertEquals(4, searchVisibleCount,
                "Search visible count should include matching header and its matching rules");

        // --- Scenario 3: Active search query with singular match ("inventory") ---
        String singularQuery = "inventory";
        List<CategoryGroup> singularResults = filterCategoryGroups(allGroups, singularQuery);
        assertEquals(1, singularResults.size(), "Only player category should match 'inventory'");

        CategoryGroup matchedPlayer = singularResults.get(0);
        assertEquals("[● 1 match]", matchedPlayer.countBadge().getString(),
                "Badge should show singular match text");
        int singularVisibleCount = computeVisibleItemCount(singularResults, singularQuery);
        assertEquals(2, singularVisibleCount, "Singular match: 1 header + 1 rule = 2 entries");

        // --- Scenario 4: Clearing search query restores normal collapse view ---
        int restoredVisibleCount = computeVisibleItemCount(allGroups, "");
        assertEquals(5, restoredVisibleCount,
                "Restoring empty search query should immediately return to persisted collapse state");
    }

    @Test
    @DisplayName("Verify category reset state machine and deviation detection via CategoryResetHelper")
    void testCategoryResetStateMachine() {
        // Construct resettable entries
        SimulatedResettableRuleEntry ruleA = new SimulatedResettableRuleEntry("ruleA", true);
        SimulatedResettableRuleEntry ruleB = new SimulatedResettableRuleEntry("ruleB", false);
        SimulatedResettableRuleEntry ruleC = new SimulatedResettableRuleEntry("ruleC", true);

        List<SimulatedResettableRuleEntry> categoryRules = List.of(ruleA, ruleB, ruleC);
        int totalRules = categoryRules.size();

        // Initial state: all rules match their defaults
        int initialModified = countModifiedRules(categoryRules);
        assertEquals(0, initialModified, "Initially no rules should be modified");
        assertFalse(CategoryResetHelper.canReset(initialModified),
                "Category should not be eligible for reset when 0 rules are modified");
        assertEquals("[3 rules]", CategoryGroup.createBadge(totalRules, initialModified).getString());

        // Step 1: User modifies ruleA (true -> false)
        ruleA.setValue(false);
        assertTrue(ruleA.collapsible_game_rules$isModified(), "ruleA should be marked modified");
        int step1Modified = countModifiedRules(categoryRules);
        assertEquals(1, step1Modified);
        assertTrue(CategoryResetHelper.canReset(step1Modified),
                "Category should be eligible for reset when 1 rule is modified");
        assertEquals("[● 1 mod / 3 rules]", CategoryGroup.createBadge(totalRules, step1Modified).getString());

        // Step 2: User modifies ruleB (false -> true)
        ruleB.setValue(true);
        assertTrue(ruleB.collapsible_game_rules$isModified(), "ruleB should be marked modified");
        int step2Modified = countModifiedRules(categoryRules);
        assertEquals(2, step2Modified);
        assertTrue(CategoryResetHelper.canReset(step2Modified));
        assertEquals("[● 2 mod / 3 rules]", CategoryGroup.createBadge(totalRules, step2Modified).getString());

        // Step 3: Trigger Category Reset action
        for (SimulatedResettableRuleEntry rule : categoryRules) {
            rule.collapsible_game_rules$resetToDefault();
        }

        // Verify state after reset
        int resetModified = countModifiedRules(categoryRules);
        assertEquals(0, resetModified, "All rules must be reverted to defaults after reset");
        assertFalse(CategoryResetHelper.canReset(resetModified),
                "Category must no longer be eligible for reset");
        assertEquals(0, CategoryResetHelper.clampModifiedCount(totalRules, resetModified));
        assertEquals("[3 rules]", CategoryGroup.createBadge(totalRules, resetModified).getString(),
                "Badge must return to clean unmodified format");

        // Verify boundary safety of CategoryResetHelper
        assertEquals(3, CategoryResetHelper.clampModifiedCount(3, 100));
        assertEquals(0, CategoryResetHelper.clampModifiedCount(3, -5));
        assertEquals(0, CategoryResetHelper.clampModifiedCount(0, 5));

        // Test multi-type modification checks
        assertFalse(CategoryResetHelper.isModified(10, 10));
        assertTrue(CategoryResetHelper.isModified(10, 20));
        assertFalse(CategoryResetHelper.isModified("default", "default"));
        assertTrue(CategoryResetHelper.isModified("changed", "default"));
    }

    // --- Headless Simulation Helpers ---

    private static int countModifiedRules(List<? extends ResettableRuleEntry> rules) {
        int count = 0;
        for (ResettableRuleEntry rule : rules) {
            if (rule.collapsible_game_rules$isModified()) {
                count++;
            }
        }
        return count;
    }

    private static List<CategoryGroup> filterCategoryGroups(List<CategoryGroup> groups, String query) {
        String filter = query.toLowerCase(Locale.ROOT).trim();
        List<CategoryGroup> results = new ArrayList<>();

        for (CategoryGroup group : groups) {
            List<AbstractGameRulesScreen.RuleEntry> matchingRules = new ArrayList<>();
            for (AbstractGameRulesScreen.RuleEntry rule : group.rules()) {
                if (rule instanceof SimulatedRuleEntry sim && sim.getName().toLowerCase(Locale.ROOT).contains(filter)) {
                    matchingRules.add(rule);
                }
            }

            if (!matchingRules.isEmpty()) {
                CategoryGroup matchedGroup = new CategoryGroup(
                        group.displayLabel(),
                        group.persistenceKey(),
                        matchingRules,
                        group.modifiedCount()
                ).withMatchCount(matchingRules.size());
                results.add(matchedGroup);
            }
        }
        return results;
    }

    private static int computeVisibleItemCount(List<CategoryGroup> groups, String query) {
        boolean isSearching = query != null && !query.trim().isEmpty();
        int count = 0;

        for (CategoryGroup group : groups) {
            // Header entry is always added
            count++;

            // If searching, categories are forced expanded; otherwise respect config
            boolean expanded = isSearching || GameRuleStateConfig.isExpanded(group.persistenceKey());
            if (expanded) {
                count += group.rules().size();
            }
        }
        return count;
    }

    /**
     * Minimal headless stub of RuleEntry for state machine simulation.
     */
    private static class SimulatedRuleEntry extends AbstractGameRulesScreen.RuleEntry {
        private final String name;

        public SimulatedRuleEntry(String name) {
            super(null);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return Collections.emptyList();
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            // No-op for headless unit test
        }
    }

    private static class SimulatedResettableRuleEntry extends SimulatedRuleEntry implements ResettableRuleEntry {
        private final boolean defaultValue;
        private boolean currentValue;
        private boolean modified;

        public SimulatedResettableRuleEntry(String name, boolean defaultValue) {
            super(name);
            this.defaultValue = defaultValue;
            this.currentValue = defaultValue;
            this.modified = false;
        }

        public void setValue(boolean value) {
            this.currentValue = value;
            this.modified = CategoryResetHelper.isModified(this.currentValue, this.defaultValue);
        }

        @Override
        public boolean collapsible_game_rules$isModified() {
            return this.modified;
        }

        @Override
        public void collapsible_game_rules$resetToDefault() {
            this.currentValue = this.defaultValue;
            this.modified = false;
        }
    }
}
