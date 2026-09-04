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
        Component collapsedDisplay
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
                createDisplay(displayLabel, "▼ ", rules != null ? rules.size() : 0),
                createDisplay(displayLabel, "▶ ", rules != null ? rules.size() : 0)
        );
    }

    public CategoryGroup {
        Objects.requireNonNull(displayLabel, "displayLabel cannot be null");
        Objects.requireNonNull(persistenceKey, "persistenceKey cannot be null");
        rules = rules != null ? Collections.unmodifiableList(rules) : Collections.emptyList();
        Objects.requireNonNull(expandedDisplay, "expandedDisplay cannot be null");
        Objects.requireNonNull(collapsedDisplay, "collapsedDisplay cannot be null");
    }

    private static Component createDisplay(Component label, String prefix, int count) {
        Component countBadge = Component.literal(" (" + count + " rules)").withStyle(ChatFormatting.GRAY);
        return Component.literal(prefix).append(label).append(countBadge);
    }

    /**
     * Instant O(1) rule count lookup.
     */
    public int ruleCount() {
        return this.rules.size();
    }
}
