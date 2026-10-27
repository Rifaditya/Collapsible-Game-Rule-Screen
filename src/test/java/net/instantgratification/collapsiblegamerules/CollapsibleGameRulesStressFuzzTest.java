// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import net.instantgratification.collapsiblegamerules.util.CategoryPrettifier;
import net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper;
import net.instantgratification.collapsiblegamerules.util.SearchHighlightHelper;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CollapsibleGameRulesStressFuzzTest {

    private static final long SEED = 0xDA51CL;

    @BeforeEach
    void setUp() {
        GameRuleStateConfig.resetState();
    }

    @Test
    @DisplayName("Pillar 1: CategoryPrettifier Chaos Fuzzing (25,000 iterations)")
    void testCategoryPrettifierChaosFuzzing() {
        Random random = new Random(SEED);

        String[] prefixes = {
                "",
                "gamerule.category.",
                "gamerule.category.minecraft.",
                "gamerule.category.better-bats.",
                "minecraft.",
                "category.",
                "custom_mod."
        };

        String[] charPool = {
                "abcdefghijklmnopqrstuvwxyz",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                "0123456789",
                " ._-\t\n\r:;!@#$%^&*()+={}[]|\\/?<>,~`'\"",
                "\u0000\u0001\u0007\u0008\u001b\u001f\u007f\u0080\u009f",
                "ПриветМирこんにちは世界مرحبابالعالم",
                "😀🔥🎉🚀🐱🎮💡⚔️🛡️",
                "\uD83D\uDE00\uD83C\uDF89\uD800\uDFFF"
        };

        for (int i = 0; i < 25_000; i++) {
            String input;
            int type = random.nextInt(100);

            if (type == 0) {
                input = null;
            } else if (type == 1) {
                input = "";
            } else {
                String prefix = prefixes[random.nextInt(prefixes.length)];
                int length = random.nextInt(500);
                StringBuilder sb = new StringBuilder(prefix);

                String chosenSet = charPool[random.nextInt(charPool.length)];
                for (int j = 0; j < length; j++) {
                    sb.append(chosenSet.charAt(random.nextInt(chosenSet.length())));
                }
                input = sb.toString();
            }

            assertDoesNotThrow(() -> {
                String prettifiedName = CategoryPrettifier.prettifyCategoryName(input);
                assertNotNull(prettifiedName, "Prettified category name must never be null");

                String prettifiedKey = CategoryPrettifier.prettifyCategoryKey(input);
                assertNotNull(prettifiedKey, "Prettified category key must never be null");
                assertEquals(prettifiedName, prettifiedKey, "Alias and key prettifier must yield identical results");
            }, "CategoryPrettifier threw an unhandled exception for iteration " + i);
        }
    }

    @Test
    @DisplayName("Pillar 1: SearchHighlightHelper Regex & Metacharacter Chaos (5,000 iterations)")
    void testSearchHighlightHelperRegexChaos() {
        Random random = new Random(SEED);

        String[] candidateTexts = {
                "randomTickSpeed",
                "playersSleepingPercentage",
                "maxEntityCramming",
                "mobGriefing",
                "doDaylightCycle",
                "gamerule.category.better-bats.better_bats",
                "Enables or disables natural health regeneration",
                "",
                "abc 123 !@# $%^",
                "Unicode: Привет, мир! こんにちは世界 😀🚀🔥"
        };

        String[] regexAdversarialSnippets = {
                "[", "]", "(", ")", "{", "}", "*", "+", "?", "^", "$", "\\", "|", ".",
                "\\d", "\\w", "\\s", "\\b", "\\p{L}", ".*", ".+", "(?i)", "[a-z]+",
                "*invalid", "+bad", "?quantifier", "(?", "([", "\\[\\]", "\\Q...\\E",
                "\\k<group>", "\\1", "(?:.*)+", "{1,2}", "[[[", ")))", "$$$", "^^^",
                "\uD800", "\uDFFF", "\uD83D", "\uDE00", "\\u0000", "\\n", "\\r"
        };

        for (int i = 0; i < 5_000; i++) {
            String text = candidateTexts[random.nextInt(candidateTexts.length)];
            String query;

            int queryType = random.nextInt(10);
            if (queryType == 0) {
                query = null;
            } else if (queryType == 1) {
                query = "";
            } else if (queryType == 2) {
                query = "   \t\n  ";
            } else {
                StringBuilder qb = new StringBuilder();
                int pieces = 1 + random.nextInt(6);
                for (int p = 0; p < pieces; p++) {
                    qb.append(regexAdversarialSnippets[random.nextInt(regexAdversarialSnippets.length)]);
                }
                query = qb.toString();
            }

            final String fText = text;
            final String fQuery = query;

            assertDoesNotThrow(() -> {
                Component compSpans = SearchHighlightHelper.buildHighlightedSpans(fText, fQuery);
                assertNotNull(compSpans, "buildHighlightedSpans returned null component");
                assertNotNull(compSpans.getString(), "Component string must not be null");

                Component compHighlight = SearchHighlightHelper.highlight(fText, fQuery);
                assertNotNull(compHighlight, "highlight returned null component");

                if (fText != null) {
                    Component literalInput = Component.literal(fText);
                    Component compFromComponent = SearchHighlightHelper.highlight(literalInput, fQuery);
                    assertNotNull(compFromComponent, "highlight(Component, query) returned null");
                }
            }, "SearchHighlightHelper threw an exception on query '" + fQuery + "' with text '" + fText + "'");
        }
    }

    @Test
    @DisplayName("Pillar 1: GameRuleSliderHelper Boundary & Extreme Values Fuzzing")
    void testGameRuleSliderHelperBoundaryFuzzing() {
        Random random = new Random(SEED);

        int[] extremeInts = {
                Integer.MIN_VALUE,
                Integer.MIN_VALUE + 1,
                -1000000,
                -100,
                -1,
                0,
                1,
                5,
                100,
                1000000,
                Integer.MAX_VALUE - 1,
                Integer.MAX_VALUE
        };

        // 1. Boundary fuzzing on calculateBoundedSteps
        for (int min : extremeInts) {
            for (int max : extremeInts) {
                for (int step : extremeInts) {
                    assertDoesNotThrow(() -> {
                        int steps = GameRuleSliderHelper.calculateBoundedSteps(min, max, step);
                        assertTrue(steps >= 0, "Calculated steps must be non-negative");
                    }, "calculateBoundedSteps threw ArithmeticException or error for (" + min + ", " + max + ", " + step + ")");
                }
            }
        }

        // 2. Boundary fuzzing on SliderConfig validation
        for (int min : extremeInts) {
            for (int max : extremeInts) {
                for (int step : extremeInts) {
                    if (min >= max || step <= 0) {
                        assertThrows(IllegalArgumentException.class, () -> new GameRuleSliderHelper.SliderConfig(min, max, step, ""));
                    } else {
                        assertDoesNotThrow(() -> {
                            GameRuleSliderHelper.SliderConfig cfg = new GameRuleSliderHelper.SliderConfig(min, max, step, " units");
                            assertEquals(min, cfg.min());
                            assertEquals(max, cfg.max());
                            assertEquals(step, cfg.step());
                        });
                    }
                }
            }
        }

        // 3. Stress snapAndClamp with randomized values across realistic and extreme configs
        GameRuleSliderHelper.SliderConfig[] validConfigs = {
                new GameRuleSliderHelper.SliderConfig(0, 100, 5, "%"),
                new GameRuleSliderHelper.SliderConfig(-50, 50, 1, ""),
                new GameRuleSliderHelper.SliderConfig(1, 1000, 10, " ticks"),
                new GameRuleSliderHelper.SliderConfig(-10000, 10000, 250, ""),
                new GameRuleSliderHelper.SliderConfig(Integer.MIN_VALUE / 4, Integer.MAX_VALUE / 4, 1000, "")
        };

        for (int i = 0; i < 10_000; i++) {
            int value = random.nextInt();
            GameRuleSliderHelper.SliderConfig cfg = validConfigs[random.nextInt(validConfigs.length)];

            assertDoesNotThrow(() -> {
                int clamped = GameRuleSliderHelper.snapAndClamp(value, cfg);
                assertTrue(clamped >= cfg.min(), "Snapped value (" + clamped + ") must be >= min (" + cfg.min() + ")");
                assertTrue(clamped <= cfg.max(), "Snapped value (" + clamped + ") must be <= max (" + cfg.max() + ")");

                String formatted = GameRuleSliderHelper.formatValue(clamped, cfg);
                assertNotNull(formatted);
                assertTrue(formatted.contains(String.valueOf(clamped)));
            });

            // Null config test
            assertEquals(value, GameRuleSliderHelper.snapAndClamp(value, null));
        }

        // 4. normalizeKey fuzzing
        for (int i = 0; i < 2_000; i++) {
            int len = random.nextInt(50);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                sb.append((char) (random.nextInt(128)));
            }
            assertDoesNotThrow(() -> {
                String normalized = GameRuleSliderHelper.normalizeKey(sb.toString());
                assertNotNull(normalized);
            });
        }
        assertEquals("", GameRuleSliderHelper.normalizeKey(null));
    }

    @Test
    @DisplayName("Pillar 1: GameRuleStateConfig Corrupted JSON & Malformed Payloads Fuzzing (5,000 iterations)")
    void testGameRuleStateConfigCorruptedJsonFuzzing(@TempDir Path tempDir) throws IOException {
        Random random = new Random(SEED);

        String[] malformedTemplates = {
                "{",
                "}",
                "[]",
                "[}",
                "{]",
                "{\"schemaVersion\":",
                "{\"schemaVersion\": 1,",
                "{\"schemaVersion\": \"notAnInteger\"}",
                "{\"schemaVersion\": 1, \"expandedCategories\": [",
                "{\"schemaVersion\": 1, \"expandedCategories\": [\"spawning\"",
                "{\"schemaVersion\": 1, \"expandedCategories\": [\"spawning\",]}",
                "{\"schemaVersion\": 1, \"expandedCategories\": {\"key\": \"value\"}}",
                "{\"schemaVersion\": 1, \"expandedCategories\": 12345}",
                "{\"schemaVersion\": 1, \"expandedCategories\": null}",
                "{\"expandedCategories\": [null, \"valid\", null]}",
                "{\"unrelatedKey\": 999}",
                "\"naked string\"",
                "123456789",
                "true",
                "false",
                "null",
                "NaN",
                "undefined",
                "<!DOCTYPE html><html><body>Error</body></html>",
                "\u0000\u0001\u0002\u0003{\"schemaVersion\": 1}",
                "{\"\": \"\"}",
                "{{{{{}}}}}",
                "[[[[[[]]]]]]"
        };

        Path reusableTempFile = tempDir.resolve("fuzz-config.json");

        for (int i = 0; i < 5_000; i++) {
            String corruptedPayload;
            int choice = random.nextInt(10);

            if (choice < 6) {
                // Select template and mutate
                String base = malformedTemplates[random.nextInt(malformedTemplates.length)];
                if (random.nextBoolean()) {
                    corruptedPayload = base + "/* trailing garbage " + random.nextLong() + " */";
                } else {
                    corruptedPayload = base.substring(0, Math.max(0, base.length() - random.nextInt(3)));
                }
            } else if (choice < 9) {
                // Chaotic character sequence
                int len = 1 + random.nextInt(120);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < len; j++) {
                    sb.append((char) (random.nextInt(128)));
                }
                corruptedPayload = sb.toString();
            } else {
                // Highly nested or truncated array payload
                corruptedPayload = "[\"cat_" + i + "\", " + random.nextInt(500) + ", {";
            }

            final String fPayload = corruptedPayload;

            // In-memory deserialization fuzzing
            assertDoesNotThrow(() -> {
                GameRuleStateConfig.deserializeJson(fPayload);
                assertTrue(GameRuleStateConfig.getExpandedCategoriesCount() >= 0);
            }, "deserializeJson threw unhandled exception on payload: " + fPayload);

            // Re-assert clean state recovery
            assertNotNull(GameRuleStateConfig.isDirty());

            // Run file-based load verification periodically (every 25 iterations = 200 disk checks)
            if (i % 25 == 0) {
                Files.writeString(reusableTempFile, fPayload);
                assertDoesNotThrow(() -> {
                    GameRuleStateConfig.loadFromPath(reusableTempFile);
                    assertTrue(GameRuleStateConfig.getExpandedCategoriesCount() >= 0);
                }, "loadFromPath threw unhandled exception on file content: " + fPayload);
            }
        }

        // Final sanity check: valid configuration works after heavy fuzz assault
        GameRuleStateConfig.deserializeJson("{\"schemaVersion\": 1, \"expandedCategories\": [\"spawning\", \"drops\"]}");
        assertEquals(2, GameRuleStateConfig.getExpandedCategoriesCount());
        assertTrue(GameRuleStateConfig.isExpanded("spawning"));
        assertTrue(GameRuleStateConfig.isExpanded("drops"));
        assertFalse(GameRuleStateConfig.isDirty());
    }
}
