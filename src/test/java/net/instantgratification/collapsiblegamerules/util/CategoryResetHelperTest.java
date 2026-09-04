// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryResetHelperTest {

    @Test
    @DisplayName("Boolean modification check correctly identifies deviations")
    void testBooleanModification() {
        assertFalse(CategoryResetHelper.isModified(true, true));
        assertFalse(CategoryResetHelper.isModified(false, false));
        assertTrue(CategoryResetHelper.isModified(true, false));
        assertTrue(CategoryResetHelper.isModified(false, true));
    }

    @Test
    @DisplayName("Integer modification check correctly identifies deviations")
    void testIntegerModification() {
        assertFalse(CategoryResetHelper.isModified(3, 3));
        assertFalse(CategoryResetHelper.isModified(0, 0));
        assertFalse(CategoryResetHelper.isModified(-10, -10));

        assertTrue(CategoryResetHelper.isModified(3, 4));
        assertTrue(CategoryResetHelper.isModified(100, 0));
        assertTrue(CategoryResetHelper.isModified(-1, 0));
    }

    @Test
    @DisplayName("String modification check handles trimming, equality, and null safety")
    void testStringModification() {
        assertFalse(CategoryResetHelper.isModified((String) null, null));
        assertFalse(CategoryResetHelper.isModified("true", "true"));
        assertFalse(CategoryResetHelper.isModified(" 10 ", "10"));

        assertTrue(CategoryResetHelper.isModified("true", null));
        assertTrue(CategoryResetHelper.isModified(null, "false"));
        assertTrue(CategoryResetHelper.isModified("true", "false"));
        assertTrue(CategoryResetHelper.isModified("10", "20"));
    }

    @Test
    @DisplayName("Clamp modified count respects boundaries")
    void testClampModifiedCount() {
        assertEquals(0, CategoryResetHelper.clampModifiedCount(0, 5));
        assertEquals(0, CategoryResetHelper.clampModifiedCount(-5, 5));
        assertEquals(0, CategoryResetHelper.clampModifiedCount(10, -2));
        assertEquals(3, CategoryResetHelper.clampModifiedCount(10, 3));
        assertEquals(10, CategoryResetHelper.clampModifiedCount(10, 15));
    }

    @Test
    @DisplayName("canReset returns true only when modifiedCount > 0")
    void testCanReset() {
        assertFalse(CategoryResetHelper.canReset(0));
        assertFalse(CategoryResetHelper.canReset(-1));
        assertTrue(CategoryResetHelper.canReset(1));
        assertTrue(CategoryResetHelper.canReset(5));
    }
}
