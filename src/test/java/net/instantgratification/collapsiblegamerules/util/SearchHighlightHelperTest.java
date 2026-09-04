// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchHighlightHelperTest {

    @Test
    @DisplayName("Null or empty text returns empty component")
    void testNullOrEmptyText() {
        assertEquals("", SearchHighlightHelper.highlight((String) null, "test").getString());
        assertEquals("", SearchHighlightHelper.highlight("", "test").getString());
        assertEquals("", SearchHighlightHelper.highlight((Component) null, "test").getString());
    }

    @Test
    @DisplayName("Null or blank query returns plain text without modification")
    void testNullOrBlankQuery() {
        Component resNull = SearchHighlightHelper.highlight("random_tick_speed", null);
        assertEquals("random_tick_speed", resNull.getString());

        Component resEmpty = SearchHighlightHelper.highlight("random_tick_speed", "");
        assertEquals("random_tick_speed", resEmpty.getString());

        Component resSpaces = SearchHighlightHelper.highlight("random_tick_speed", "   ");
        assertEquals("random_tick_speed", resSpaces.getString());
    }

    @Test
    @DisplayName("Component fast-path returns identical reference when query is absent (0B heap allocation)")
    void testComponentFastPath() {
        Component original = Component.literal("random_tick_speed");

        assertSame(original, SearchHighlightHelper.highlight(original, null));
        assertSame(original, SearchHighlightHelper.highlight(original, ""));
        assertSame(original, SearchHighlightHelper.highlight(original, "   "));
        assertSame(original, SearchHighlightHelper.highlight(original, "creeper"));
    }

    @Test
    @DisplayName("Case-insensitive substring search matches and preserves original text casing")
    void testCaseInsensitiveMatch() {
        Component res = SearchHighlightHelper.highlight("Random_Tick_Speed", "tick");
        assertEquals("Random_Tick_Speed", res.getString());

        // Verify that the highlighted sibling contains the matched text with style
        boolean foundHighlightedSibling = res.toFlatList().stream()
                .anyMatch(c -> "Tick".equals(c.getString()) && c.getStyle().getColor() != null);
        assertTrue(foundHighlightedSibling, "Expected sibling with 'Tick' to carry highlight style");
    }

    @Test
    @DisplayName("Prefix match correctly partitions text at index 0")
    void testPrefixMatch() {
        Component res = SearchHighlightHelper.highlight("random_tick_speed", "random");
        assertEquals("random_tick_speed", res.getString());

        boolean foundPrefix = res.toFlatList().stream()
                .anyMatch(c -> "random".equals(c.getString()) && c.getStyle().getColor() != null);
        assertTrue(foundPrefix);
    }

    @Test
    @DisplayName("Suffix match correctly partitions text at the very end")
    void testSuffixMatch() {
        Component res = SearchHighlightHelper.highlight("random_tick_speed", "speed");
        assertEquals("random_tick_speed", res.getString());

        boolean foundSuffix = res.toFlatList().stream()
                .anyMatch(c -> "speed".equals(c.getString()) && c.getStyle().getColor() != null);
        assertTrue(foundSuffix);
    }

    @Test
    @DisplayName("Multiple occurrences in a single string are all highlighted")
    void testMultipleOccurrences() {
        Component res = SearchHighlightHelper.highlight("freeze_damage", "e");
        assertEquals("freeze_damage", res.getString());

        long highlightedCount = res.toFlatList().stream()
                .filter(c -> "e".equals(c.getString()) && c.getStyle().getColor() != null)
                .count();
        assertEquals(4, highlightedCount, "Expected 4 'e' substrings to be highlighted in 'freeze_damage'");
    }

    @Test
    @DisplayName("Whole string exact match highlights entire component")
    void testWholeStringMatch() {
        Component res = SearchHighlightHelper.highlight("fire", "fire");
        assertEquals("fire", res.getString());

        boolean foundWhole = res.toFlatList().stream()
                .anyMatch(c -> "fire".equals(c.getString()) && c.getStyle().getColor() != null);
        assertTrue(foundWhole);
    }

    @Test
    @DisplayName("Custom highlight format (e.g. GOLD) is respected")
    void testCustomHighlightFormat() {
        Component res = SearchHighlightHelper.highlight("keep_inventory", "inventory", ChatFormatting.GOLD);
        assertEquals("keep_inventory", res.getString());

        boolean foundGold = res.toFlatList().stream()
                .anyMatch(c -> "inventory".equals(c.getString()) && c.getStyle().getColor() != null);
        assertTrue(foundGold);
    }

    @Test
    @DisplayName("highlightSequence highlights matching FormattedCharSequence and fast-paths non-matches")
    void testHighlightSequence() {
        net.minecraft.util.FormattedCharSequence seq = Component.literal("Enables or disables mob griefing").getVisualOrderText();

        // Non-match returns same sequence instance (0B allocation)
        assertSame(seq, SearchHighlightHelper.highlightSequence(seq, "creeper"));
        assertSame(seq, SearchHighlightHelper.highlightSequence(seq, ""));
        assertSame(seq, SearchHighlightHelper.highlightSequence(seq, null));

        // Matching query returns highlighted FormattedCharSequence
        net.minecraft.util.FormattedCharSequence highlighted = SearchHighlightHelper.highlightSequence(seq, "griefing");
        assertNotSame(seq, highlighted);
        assertNotNull(highlighted);

        StringBuilder sb = new StringBuilder();
        highlighted.accept((index, style, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });
        assertEquals("Enables or disables mob griefing", sb.toString());
    }
}
