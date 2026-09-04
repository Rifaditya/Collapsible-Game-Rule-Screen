// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.model;

import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a category grouping in the Game Rules screen.
 * Caches pre-resolved display titles and child rule entries to enable O(1)
 * metric calculations and eliminate repeated nested list scans.
 */
public record CategoryGroup(
        Component displayLabel,
        String persistenceKey,
        List<AbstractGameRulesScreen.RuleEntry> rules
) {
    public CategoryGroup {
        Objects.requireNonNull(displayLabel, "displayLabel cannot be null");
        Objects.requireNonNull(persistenceKey, "persistenceKey cannot be null");
        rules = rules != null ? Collections.unmodifiableList(rules) : Collections.emptyList();
    }

    /**
     * Instant O(1) rule count lookup.
     */
    public int ruleCount() {
        return this.rules.size();
    }
}
