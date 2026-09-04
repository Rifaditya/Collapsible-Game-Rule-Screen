// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.model;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a category grouping in the Game Rules screen.
 * Caches pre-resolved display titles, directional headers, and child rule entries
 * to enable O(1) metric calculations and zero allocations per render frame.
 */
public record CategoryGroup(
        Component displayLabel,
        String persistenceKey,
        List<AbstractGameRulesScreen.RuleEntry> rules,
        Component expandedDisplay,
        Component collapsedDisplay,
        Component expandedLeft,
        Component collapsedLeft,
        Component countBadge
) {
    public CategoryGroup(
            Component displayLabel,
            String persistenceKey,
            List<AbstractGameRulesScreen.RuleEntry> rules
    ) {
        this(
                displayLabel,
                persistenceKey,
                rules,
                createLegacyDisplay(displayLabel, "▼ ", rules != null ? rules.size() : 0),
                createLegacyDisplay(displayLabel, "▶ ", rules != null ? rules.size() : 0),
                Component.literal("▼ ").append(displayLabel),
                Component.literal("▶ ").append(displayLabel),
                createBadge(rules != null ? rules.size() : 0, 0)
        );
    }

    public CategoryGroup {
        Objects.requireNonNull(displayLabel, "displayLabel cannot be null");
        Objects.requireNonNull(persistenceKey, "persistenceKey cannot be null");
        rules = rules != null ? Collections.unmodifiableList(rules) : Collections.emptyList();
        Objects.requireNonNull(expandedDisplay, "expandedDisplay cannot be null");
        Objects.requireNonNull(collapsedDisplay, "collapsedDisplay cannot be null");
        Objects.requireNonNull(expandedLeft, "expandedLeft cannot be null");
        Objects.requireNonNull(collapsedLeft, "collapsedLeft cannot be null");
        Objects.requireNonNull(countBadge, "countBadge cannot be null");
    }

    /**
     * Returns a new CategoryGroup with updated countBadge reflecting the number of modified rules.
     */
    public CategoryGroup withModifiedCount(int modifiedCount) {
        return new CategoryGroup(
                this.displayLabel,
                this.persistenceKey,
                this.rules,
                this.expandedDisplay,
                this.collapsedDisplay,
                this.expandedLeft,
                this.collapsedLeft,
                createBadge(this.rules.size(), modifiedCount)
        );
    }

    /**
     * Returns a new CategoryGroup with updated countBadge reflecting the number of matching rules during active search queries.
     */
    public CategoryGroup withMatchCount(int matchCount) {
        return new CategoryGroup(
                this.displayLabel,
                this.persistenceKey,
                this.rules,
                this.expandedDisplay,
                this.collapsedDisplay,
                this.expandedLeft,
                this.collapsedLeft,
                createMatchBadge(matchCount)
        );
    }

    public static Component createMatchBadge(int matchCount) {
        if (matchCount == 1) {
            return Component.literal("[● 1 match]").withStyle(ChatFormatting.AQUA);
        }
        return Component.literal("[● " + matchCount + " matches]").withStyle(ChatFormatting.AQUA);
    }

    private static Component createLegacyDisplay(Component label, String prefix, int count) {
        Component badge = Component.literal(" (" + count + " rules)").withStyle(ChatFormatting.GRAY);
        return Component.literal(prefix).append(label).append(badge);
    }

    public static Component createBadge(int totalRules, int modifiedCount) {
        if (modifiedCount > 0) {
            return Component.literal("[● " + modifiedCount + " mod / " + totalRules + " rules]").withStyle(ChatFormatting.GOLD);
        }
        return Component.literal("[" + totalRules + " rules]").withStyle(ChatFormatting.GRAY);
    }

    /**
     * Instant O(1) rule count lookup.
     */
    public int ruleCount() {
        return this.rules.size();
    }
}
