// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.model;

/**
 * Common contract for rule entries that support modification detection and reversion to defaults.
 */
public interface ResettableRuleEntry {

    /**
     * Checks if this rule entry's current value deviates from its default value.
     *
     * @return true if modified, false otherwise
     */
    boolean collapsible_game_rules$isModified();

    /**
     * Resets this rule entry to its default value, updating underlying GameRules and any bound UI widgets.
     */
    void collapsible_game_rules$resetToDefault();
}
