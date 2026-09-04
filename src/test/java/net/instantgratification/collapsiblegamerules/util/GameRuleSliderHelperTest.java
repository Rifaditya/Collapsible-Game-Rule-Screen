// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameRuleSliderHelperTest {

    @Test
    @DisplayName("Pre-registered vanilla rules must have valid positive ranges and step sizes")
    void testRegisteredRulesValidity() {
        Map<String, GameRuleSliderHelper.SliderConfig> configs = GameRuleSliderHelper.getAllConfigs();
        assertFalse(configs.isEmpty());

        for (Map.Entry<String, GameRuleSliderHelper.SliderConfig> entry : configs.entrySet()) {
            GameRuleSliderHelper.SliderConfig config = entry.getValue();
            assertNotNull(config, "Config for " + entry.getKey() + " should not be null");
            assertTrue(config.min() < config.max(), "min must be strictly less than max for " + entry.getKey());
            assertTrue(config.step() >= 1, "step must be at least 1 for " + entry.getKey());
            assertNotNull(config.unitSuffix(), "unitSuffix must not be null for " + entry.getKey());
        }
    }

    @Test
    @DisplayName("Specific known rules return expected slider configurations")
    void testKnownRules() {
        assertTrue(GameRuleSliderHelper.hasConfig("randomTickSpeed"));
        GameRuleSliderHelper.SliderConfig tickConfig = GameRuleSliderHelper.getConfig("randomTickSpeed");
        assertNotNull(tickConfig);
        assertEquals(0, tickConfig.min());
        assertEquals(20, tickConfig.max());
        assertEquals(1, tickConfig.step());

        assertTrue(GameRuleSliderHelper.hasConfig("playersSleepingPercentage"));
        GameRuleSliderHelper.SliderConfig sleepConfig = GameRuleSliderHelper.getConfig("playersSleepingPercentage");
        assertNotNull(sleepConfig);
        assertEquals(0, sleepConfig.min());
        assertEquals(100, sleepConfig.max());
        assertEquals(5, sleepConfig.step());
        assertEquals("%", sleepConfig.unitSuffix());
    }

    @Test
    @DisplayName("Unregistered / unbound rules return null and false")
    void testUnregisteredRules() {
        assertFalse(GameRuleSliderHelper.hasConfig("unregisteredCustomRule"));
        assertNull(GameRuleSliderHelper.getConfig("unregisteredCustomRule"));
        assertFalse(GameRuleSliderHelper.hasConfig(null));
        assertNull(GameRuleSliderHelper.getConfig(null));
    }

    @Test
    @DisplayName("snapAndClamp correctly bounds and snaps intermediate values")
    void testSnapAndClamp() {
        GameRuleSliderHelper.SliderConfig step5Config = GameRuleSliderHelper.getConfig("playersSleepingPercentage");
        assertNotNull(step5Config);

        // Clamping to min/max
        assertEquals(0, GameRuleSliderHelper.snapAndClamp(-10, step5Config));
        assertEquals(100, GameRuleSliderHelper.snapAndClamp(150, step5Config));

        // Snapping to step=5
        assertEquals(10, GameRuleSliderHelper.snapAndClamp(11, step5Config));
        assertEquals(10, GameRuleSliderHelper.snapAndClamp(12, step5Config));
        assertEquals(15, GameRuleSliderHelper.snapAndClamp(13, step5Config));
        assertEquals(15, GameRuleSliderHelper.snapAndClamp(14, step5Config));
        assertEquals(15, GameRuleSliderHelper.snapAndClamp(15, step5Config));

        // Null config returns original value unchanged
        assertEquals(42, GameRuleSliderHelper.snapAndClamp(42, null));
    }

    @Test
    @DisplayName("formatValue formats with or without unit suffixes")
    void testFormatValue() {
        GameRuleSliderHelper.SliderConfig sleepConfig = GameRuleSliderHelper.getConfig("playersSleepingPercentage");
        assertEquals("100%", GameRuleSliderHelper.formatValue(100, sleepConfig));
        assertEquals("0%", GameRuleSliderHelper.formatValue(0, sleepConfig));

        GameRuleSliderHelper.SliderConfig tickConfig = GameRuleSliderHelper.getConfig("randomTickSpeed");
        assertEquals("3", GameRuleSliderHelper.formatValue(3, tickConfig));

        GameRuleSliderHelper.SliderConfig portalConfig = GameRuleSliderHelper.getConfig("playersNetherPortalCreativeDelay");
        assertEquals("10 ticks", GameRuleSliderHelper.formatValue(10, portalConfig));

        assertEquals("50", GameRuleSliderHelper.formatValue(50, null));
    }

    @Test
    @DisplayName("Invalid SliderConfig bounds throw IllegalArgumentException")
    void testInvalidConfigConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new GameRuleSliderHelper.SliderConfig(10, 5, 1, ""));
        assertThrows(IllegalArgumentException.class, () -> new GameRuleSliderHelper.SliderConfig(10, 10, 1, ""));
        assertThrows(IllegalArgumentException.class, () -> new GameRuleSliderHelper.SliderConfig(0, 10, 0, ""));
        assertThrows(IllegalArgumentException.class, () -> new GameRuleSliderHelper.SliderConfig(0, 10, -1, ""));
    }
}