// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import java.util.Objects;

/**
 * Headless helper utility for detecting modified game rule states and calculating
 * category reversion metrics without runtime allocation churn.
 */
public final class CategoryResetHelper {

    private CategoryResetHelper() {}

    /**
     * Checks if a boolean value has deviated from its default.
     *
     * @param current the current boolean value
     * @param defaultValue the default boolean value
     * @return true if modified, false if matching default
     */
    public static boolean isModified(boolean current, boolean defaultValue) {
        return current != defaultValue;
    }

    /**
     * Checks if an integer value has deviated from its default.
     *
     * @param current the current integer value
     * @param defaultValue the default integer value
     * @return true if modified, false if matching default
     */
    public static boolean isModified(int current, int defaultValue) {
        return current != defaultValue;
    }

    /**
     * Checks if a serialized string value has deviated from its default representation.
     *
     * @param current the current string value
     * @param defaultValue the default string value
     * @return true if modified, false if matching default or both null
     */
    public static boolean isModified(String current, String defaultValue) {
        if (current == null && defaultValue == null) {
            return false;
        }
        if (current == null || defaultValue == null) {
            return true;
        }
        return !Objects.equals(current.trim(), defaultValue.trim());
    }

    /**
     * Clamps a modified rule counter between 0 and the total number of rules in a category.
     *
     * @param totalRules the total rules in the category
     * @param modifiedCount the counted modified rules
     * @return clamped valid count
     */
    public static int clampModifiedCount(int totalRules, int modifiedCount) {
        if (totalRules <= 0) {
            return 0;
        }
        return Math.max(0, Math.min(totalRules, modifiedCount));
    }

    /**
     * Determines whether a category is considered modified and eligible for reset.
     *
     * @param modifiedCount the count of modified rules in the category
     * @return true if at least one rule is modified
     */
    public static boolean canReset(int modifiedCount) {
        return modifiedCount > 0;
    }
}
