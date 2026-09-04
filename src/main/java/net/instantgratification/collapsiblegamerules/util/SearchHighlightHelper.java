// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.Locale;

/**
 * Headless string and component partitioning utility.
 * Partitions text into sequential spans where matched substrings are highlighted
 * with high-contrast formatting (ChatFormatting.YELLOW) during search queries.
 */
public final class SearchHighlightHelper {

    public static final ChatFormatting DEFAULT_HIGHLIGHT = ChatFormatting.YELLOW;

    private SearchHighlightHelper() {}

    /**
     * Highlights occurrences of {@code query} within {@code text} using {@link #DEFAULT_HIGHLIGHT}.
     *
     * @param text  the raw text to search
     * @param query the search substring to highlight
     * @return a formatted Component with highlighted spans, or plain literal if query is absent
     */
    public static Component highlight(String text, String query) {
        return highlight(text, query, DEFAULT_HIGHLIGHT);
    }

    /**
     * Highlights occurrences of {@code query} within {@code text} using the specified {@code highlightFormat}.
     *
     * @param text            the raw text to search
     * @param query           the search substring to highlight
     * @param highlightFormat the ChatFormatting style to apply to matching spans
     * @return a formatted Component with highlighted spans, or plain literal if query is absent
     */
    public static Component highlight(String text, String query, ChatFormatting highlightFormat) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }
        if (query == null || query.isBlank()) {
            return Component.literal(text);
        }

        String lowerText = text.toLowerCase(Locale.ROOT);
        String lowerQuery = query.toLowerCase(Locale.ROOT).trim();
        if (lowerQuery.isEmpty()) {
            return Component.literal(text);
        }

        int idx = lowerText.indexOf(lowerQuery);
        if (idx == -1) {
            return Component.literal(text);
        }

        MutableComponent root = Component.empty();
        int lastIndex = 0;
        int queryLen = lowerQuery.length();
        ChatFormatting format = highlightFormat != null ? highlightFormat : DEFAULT_HIGHLIGHT;

        while (idx != -1) {
            if (idx > lastIndex) {
                root.append(Component.literal(text.substring(lastIndex, idx)));
            }
            root.append(Component.literal(text.substring(idx, idx + queryLen)).withStyle(format));
            lastIndex = idx + queryLen;
            idx = lowerText.indexOf(lowerQuery, lastIndex);
        }

        if (lastIndex < text.length()) {
            root.append(Component.literal(text.substring(lastIndex)));
        }

        return root;
    }

    /**
     * Highlights occurrences of {@code query} within a {@link Component}.
     * If the query is absent, returns the original component directly to guarantee 0B heap allocation.
     *
     * @param component the source component
     * @param query     the search substring to highlight
     * @return a highlighted Component, or the original Component if no match
     */
    public static Component highlight(Component component, String query) {
        return highlight(component, query, DEFAULT_HIGHLIGHT);
    }

    /**
     * Highlights occurrences of {@code query} within a {@link Component} using the specified {@code highlightFormat}.
     * If the query is absent, returns the original component directly to guarantee 0B heap allocation.
     *
     * @param component       the source component
     * @param query           the search substring to highlight
     * @param highlightFormat the ChatFormatting style to apply to matching spans
     * @return a highlighted Component, or the original Component if no match
     */
    public static Component highlight(Component component, String query, ChatFormatting highlightFormat) {
        if (component == null) {
            return Component.empty();
        }
        if (query == null || query.isBlank()) {
            return component;
        }

        String rawText = component.getString();
        String lowerQuery = query.toLowerCase(Locale.ROOT).trim();
        if (lowerQuery.isEmpty() || !rawText.toLowerCase(Locale.ROOT).contains(lowerQuery)) {
            return component; // Fast-path: 0B allocation if query is not found
        }

        return highlight(rawText, query, highlightFormat);
    }

    /**
     * Highlights occurrences of {@code query} within a {@link FormattedCharSequence}.
     * Reconstructs character string from sink, performs highlight partitioning, and returns
     * visual ordered FormattedCharSequence.
     *
     * @param sequence the source sequence
     * @param query    the search query
     * @return highlighted sequence or original if no match
     */
    public static FormattedCharSequence highlightSequence(FormattedCharSequence sequence, String query) {
        if (sequence == null || query == null || query.isBlank()) {
            return sequence;
        }
        StringBuilder sb = new StringBuilder();
        sequence.accept((index, style, codePoint) -> {
            sb.appendCodePoint(codePoint);
            return true;
        });
        String text = sb.toString();
        String lowerQuery = query.toLowerCase(Locale.ROOT).trim();
        if (lowerQuery.isEmpty() || !text.toLowerCase(Locale.ROOT).contains(lowerQuery)) {
            return sequence; // 0B allocation on fast path
        }
        return highlight(text, query).getVisualOrderText();
    }
}
