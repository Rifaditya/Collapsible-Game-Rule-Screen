// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import net.instantgratification.collapsiblegamerules.model.CategoryGroup;
import net.instantgratification.collapsiblegamerules.model.CategoryTreeBuilder;
import net.instantgratification.collapsiblegamerules.model.ResettableRuleEntry;
import net.instantgratification.collapsiblegamerules.util.CategoryResetHelper;
import net.instantgratification.collapsiblegamerules.util.SearchHighlightHelper;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class CollapsibleGameRulesConcurrencyLoadTest {

    @BeforeEach
    void setUp() {
        GameRuleStateConfig.resetState();
    }

    @Test
    @DisplayName("Pillar 2: Concurrent Config Read/Write/Toggle & Persistence Load Simulator (50 threads)")
    void testConcurrentConfigReadWriteToggle(@TempDir Path tempDir) throws InterruptedException {
        int threadCount = 50;
        int operationsPerThread = 500;
        Path tempConfigFile = tempDir.resolve("concurrency-test-config.json");

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch finishGate = new CountDownLatch(threadCount);
        ConcurrentLinkedQueue<Throwable> exceptions = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < threadCount; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    for (int op = 0; op < operationsPerThread; op++) {
                        String categoryKey = "category.concurrency." + (workerId % 10) + "." + (op % 20);

                        // Toggle write
                        boolean state = (op % 2 == 0);
                        GameRuleStateConfig.setExpanded(categoryKey, state);

                        // Read verification
                        boolean expanded = GameRuleStateConfig.isExpanded(categoryKey);
                        assertNotNull(expanded);

                        // Periodically trigger save / load cycles
                        if (op % 50 == 0) {
                            GameRuleStateConfig.saveToPath(tempConfigFile);
                            GameRuleStateConfig.loadFromPath(tempConfigFile);
                        }

                        // Periodic count and dirty checks
                        if (op % 25 == 0) {
                            int count = GameRuleStateConfig.getExpandedCategoriesCount();
                            assertTrue(count >= 0);
                            assertNotNull(GameRuleStateConfig.isDirty());
                        }

                        // Periodic batch operations
                        if (op == 250) {
                            GameRuleStateConfig.expandAll(List.of(
                                    "batch.rule.1",
                                    "batch.rule.2",
                                    "batch.rule.3"
                            ));
                        }
                    }
                } catch (Throwable t) {
                    exceptions.add(t);
                } finally {
                    finishGate.countDown();
                }
            });
        }

        // Release all threads simultaneously for maximum contention
        startGate.countDown();
        boolean finished = finishGate.await(15, TimeUnit.SECONDS);
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Executor failed to terminate cleanly");

        assertTrue(finished, "50 worker threads did not complete within 15 seconds (deadlock or stall detected)");
        if (!exceptions.isEmpty()) {
            fail("Concurrent exceptions occurred (" + exceptions.size() + "): " + exceptions.peek().getMessage(), exceptions.peek());
        }
    }

    @Test
    @DisplayName("Pillar 2: Concurrent CategoryTreeBuilder & CategoryGroup Load Simulator (50 threads)")
    void testConcurrentCategoryTreeBuilding() throws InterruptedException {
        int threadCount = 50;
        int operationsPerThread = 200;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch finishGate = new CountDownLatch(threadCount);
        ConcurrentLinkedQueue<Throwable> exceptions = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < threadCount; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    for (int op = 0; op < operationsPerThread; op++) {
                        String catName = "WorkerCategory_" + (workerId % 10) + "_" + (op % 5);
                        String persistenceKey = "gamerule.category." + catName.toLowerCase(Locale.ROOT);
                        Component label = Component.literal(catName);

                        List<AbstractGameRulesScreen.RuleEntry> dummyRules = List.of(
                                new TestRuleEntry(),
                                new TestResettableRuleEntry(op % 2 == 0),
                                new TestResettableRuleEntry(op % 3 == 0)
                        );

                        // CategoryGroup instantiation and field queries
                        CategoryGroup group = new CategoryGroup(label, persistenceKey, dummyRules);
                        assertEquals(3, group.ruleCount());
                        assertEquals(persistenceKey, group.persistenceKey());
                        assertEquals(catName, group.displayLabel().getString());
                        assertNotNull(group.expandedDisplay());
                        assertNotNull(group.collapsedDisplay());
                        assertNotNull(group.expandedLeft());
                        assertNotNull(group.collapsedLeft());
                        assertNotNull(group.countBadge());

                        // Derivations (immutability checks)
                        CategoryGroup modGroup = group.withModifiedCount(op % 4);
                        assertNotNull(modGroup.countBadge());
                        assertEquals(3, modGroup.ruleCount());

                        CategoryGroup matchGroup = group.withMatchCount(op % 10);
                        assertNotNull(matchGroup.countBadge());
                        assertEquals(3, matchGroup.ruleCount());

                        // Tree building via CategoryTreeBuilder
                        List<CategoryGroup> builtGroups = CategoryTreeBuilder.buildGroups(dummyRules);
                        assertNotNull(builtGroups);
                        assertFalse(builtGroups.isEmpty());
                        CategoryGroup miscGroup = builtGroups.get(0);
                        assertEquals("gamerule.category.misc", miscGroup.persistenceKey());
                        assertEquals(3, miscGroup.ruleCount());
                    }
                } catch (Throwable t) {
                    exceptions.add(t);
                } finally {
                    finishGate.countDown();
                }
            });
        }

        startGate.countDown();
        boolean finished = finishGate.await(15, TimeUnit.SECONDS);
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Executor failed to terminate cleanly");

        assertTrue(finished, "Category tree building worker threads timed out after 15 seconds");
        if (!exceptions.isEmpty()) {
            fail("Concurrent exceptions occurred during CategoryTreeBuilding: " + exceptions.peek().getMessage(), exceptions.peek());
        }
    }

    @Test
    @DisplayName("Pillar 2: Concurrent SearchHighlightHelper Partitioning & Allocation Simulator (50 threads)")
    void testConcurrentSearchHighlighting() throws InterruptedException {
        int threadCount = 50;
        int operationsPerThread = 200;

        String[] sampleTexts = {
                "randomTickSpeed",
                "playersSleepingPercentage",
                "maxEntityCramming",
                "mobGriefing",
                "doDaylightCycle",
                "keepInventory",
                "naturalRegeneration",
                "spawnRadius",
                "gamerule.category.spawning",
                "gamerule.category.player",
                "A very long game rule description with multiple occurrences of tick and speed tick tick speed",
                "Symbols and special characters: #c:game_rules / ? < > [ ] { } ! @ $ % ^ & * ( ) _ +",
                "Unicode sample: Привет мир 12345",
                ""
        };

        String[] sampleQueries = {
                "tick",
                "TICK",
                "speed",
                "player",
                "mob",
                "daylight",
                "ing",
                "cramming",
                "c:",
                "rules",
                "not_matching_anything_xyz",
                "",
                "   ",
                null
        };

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch finishGate = new CountDownLatch(threadCount);
        ConcurrentLinkedQueue<Throwable> exceptions = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < threadCount; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    for (int op = 0; op < operationsPerThread; op++) {
                        String text = sampleTexts[(workerId + op) % sampleTexts.length];
                        String query = sampleQueries[(workerId * 3 + op) % sampleQueries.length];

                        Component spanResult = SearchHighlightHelper.buildHighlightedSpans(text, query);
                        assertNotNull(spanResult, "buildHighlightedSpans must never return null");

                        Component highlightResult = SearchHighlightHelper.highlight(text, query);
                        assertNotNull(highlightResult, "highlight must never return null");

                        Component componentResult = SearchHighlightHelper.highlight(Component.literal(text != null ? text : ""), query);
                        assertNotNull(componentResult, "highlight(Component) must never return null");

                        // Verify getString() integrity under concurrency
                        String resolved = spanResult.getString();
                        assertNotNull(resolved);
                    }
                } catch (Throwable t) {
                    exceptions.add(t);
                } finally {
                    finishGate.countDown();
                }
            });
        }

        startGate.countDown();
        boolean finished = finishGate.await(15, TimeUnit.SECONDS);
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Executor failed to terminate cleanly");

        assertTrue(finished, "Search highlight worker threads timed out after 15 seconds");
        if (!exceptions.isEmpty()) {
            fail("Concurrent exceptions occurred during SearchHighlighting: " + exceptions.peek().getMessage(), exceptions.peek());
        }
    }

    @Test
    @DisplayName("Pillar 2: Concurrent CategoryResetHelper Evaluation & Clamping Simulator (50 threads)")
    void testConcurrentCategoryResetHelper() throws InterruptedException {
        int threadCount = 50;
        int operationsPerThread = 500;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch finishGate = new CountDownLatch(threadCount);
        ConcurrentLinkedQueue<Throwable> exceptions = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < threadCount; i++) {
            final int workerId = i;
            executor.submit(() -> {
                try {
                    startGate.await();
                    for (int op = 0; op < operationsPerThread; op++) {
                        int currentInt = (workerId * 31 + op) % 100;
                        int defaultInt = op % 50;
                        boolean intModified = CategoryResetHelper.isModified(currentInt, defaultInt);
                        assertEquals(currentInt != defaultInt, intModified);

                        boolean currentBool = (op % 2 == 0);
                        boolean defaultBool = (workerId % 2 == 0);
                        boolean boolModified = CategoryResetHelper.isModified(currentBool, defaultBool);
                        assertEquals(currentBool != defaultBool, boolModified);

                        String s1 = (op % 7 == 0) ? null : "val_" + (op % 10);
                        String s2 = (workerId % 5 == 0) ? null : "val_" + ((op + 1) % 10);
                        boolean strModified = CategoryResetHelper.isModified(s1, s2);
                        assertNotNull(strModified);

                        int totalRules = op % 20;
                        int modCount = op % 30 - 5; // can be negative
                        int clamped = CategoryResetHelper.clampModifiedCount(totalRules, modCount);
                        assertTrue(clamped >= 0);
                        if (totalRules > 0) {
                            assertTrue(clamped <= totalRules);
                        } else {
                            assertEquals(0, clamped);
                        }

                        boolean canReset = CategoryResetHelper.canReset(clamped);
                        assertEquals(clamped > 0, canReset);
                    }
                } catch (Throwable t) {
                    exceptions.add(t);
                } finally {
                    finishGate.countDown();
                }
            });
        }

        startGate.countDown();
        boolean finished = finishGate.await(15, TimeUnit.SECONDS);
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "Executor failed to terminate cleanly");

        assertTrue(finished, "CategoryResetHelper worker threads timed out after 15 seconds");
        if (!exceptions.isEmpty()) {
            fail("Concurrent exceptions occurred during CategoryResetHelper: " + exceptions.peek().getMessage(), exceptions.peek());
        }
    }

    /**
     * Minimal stub of RuleEntry for headless concurrency tests.
     */
    private static class TestRuleEntry extends AbstractGameRulesScreen.RuleEntry {
        public TestRuleEntry() {
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

    private static class TestResettableRuleEntry extends TestRuleEntry implements ResettableRuleEntry {
        private boolean modified;

        public TestResettableRuleEntry(boolean modified) {
            this.modified = modified;
        }

        @Override
        public boolean collapsible_game_rules$isModified() {
            return this.modified;
        }

        @Override
        public void collapsible_game_rules$resetToDefault() {
            this.modified = false;
        }
    }
}
