// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class GameRuleSliderHelper {

    public record SliderConfig(int min, int max, int step, String unitSuffix) {
        public SliderConfig {
            if (min >= max) {
                throw new IllegalArgumentException("min must be strictly less than max: " + min + " >= " + max);
            }
            if (step <= 0) {
                throw new IllegalArgumentException("step must be positive: " + step);
            }
            if (unitSuffix == null) {
                unitSuffix = "";
            }
        }
    }

    private static final Map<String, SliderConfig> REGISTRY = new HashMap<>();
    private static final Map<String, SliderConfig> NORMALIZED_REGISTRY = new HashMap<>();

    static {
        // Vanilla integer rules across versions (supporting both snake_case and camelCase)
        register("randomTickSpeed", 0, 20, 1, "");
        register("random_tick_speed", 0, 20, 1, "");

        register("spawnRadius", 0, 32, 1, " blocks");
        register("respawn_radius", 0, 32, 1, " blocks");

        register("playersSleepingPercentage", 0, 100, 5, "%");
        register("players_sleeping_percentage", 0, 100, 5, "%");

        register("maxEntityCramming", 0, 100, 1, "");
        register("max_entity_cramming", 0, 100, 1, "");

        register("playersNetherPortalCreativeDelay", 0, 100, 1, " ticks");
        register("players_nether_portal_creative_delay", 0, 100, 1, " ticks");

        register("playersNetherPortalDefaultDelay", 0, 100, 5, " ticks");
        register("players_nether_portal_default_delay", 0, 100, 5, " ticks");

        register("snowAccumulationHeight", 0, 8, 1, " layers");
        register("max_snow_accumulation_height", 0, 8, 1, " layers");

        register("maxMinecartSpeed", 1, 1000, 1, " bps");
        register("max_minecart_speed", 1, 1000, 1, " bps");
    }

    private GameRuleSliderHelper() {
    }

    public static String normalizeKey(String key) {
        if (key == null) {
            return "";
        }
        int colon = key.indexOf(':');
        if (colon >= 0) {
            key = key.substring(colon + 1);
        }
        return key.toLowerCase().replace("_", "").replace("-", "").trim();
    }

    public static void register(String ruleName, int min, int max, int step, String unitSuffix) {
        if (ruleName != null && !ruleName.isBlank()) {
            SliderConfig config = new SliderConfig(min, max, step, unitSuffix);
            REGISTRY.put(ruleName, config);
            NORMALIZED_REGISTRY.put(normalizeKey(ruleName), config);
        }
    }

    public static SliderConfig getConfig(String ruleName) {
        if (ruleName == null) {
            return null;
        }
        SliderConfig direct = REGISTRY.get(ruleName);
        if (direct != null) {
            return direct;
        }
        return NORMALIZED_REGISTRY.get(normalizeKey(ruleName));
    }

    public static boolean hasConfig(String ruleName) {
        return getConfig(ruleName) != null;
    }

    public static Map<String, SliderConfig> getAllConfigs() {
        return Collections.unmodifiableMap(REGISTRY);
    }

    public static int snapAndClamp(int value, SliderConfig config) {
        if (config == null) {
            return value;
        }
        if (value <= config.min()) {
            return config.min();
        }
        if (value >= config.max()) {
            return config.max();
        }
        int offset = value - config.min();
        int roundedOffset = Math.round((float) offset / config.step()) * config.step();
        int snapped = config.min() + roundedOffset;
        return Math.max(config.min(), Math.min(config.max(), snapped));
    }

    public static String formatValue(int value, SliderConfig config) {
        if (config == null || config.unitSuffix().isEmpty()) {
            return String.valueOf(value);
        }
        return value + config.unitSuffix();
    }
}