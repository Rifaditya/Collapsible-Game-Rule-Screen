// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import java.util.HashMap;
import java.util.Map;

public final class GameRuleSliderHelper {

    public record RuleBounds(int min, int max, int defaultValue) {}

    private static final Map<String, RuleBounds> BOUNDS_MAP = new HashMap<>();

    static {
        // Vanilla Integer Game Rules
        BOUNDS_MAP.put("randomTickSpeed", new RuleBounds(0, 100, 3));
        BOUNDS_MAP.put("spawnRadius", new RuleBounds(0, 32, 10));
        BOUNDS_MAP.put("playersSleepingPercentage", new RuleBounds(0, 100, 100));
        BOUNDS_MAP.put("maxEntityCramming", new RuleBounds(0, 100, 24));
        BOUNDS_MAP.put("maxCommandChainLength", new RuleBounds(0, 65536, 65536));
        BOUNDS_MAP.put("commandModificationBlockLimit", new RuleBounds(0, 65536, 32768));
    }

    private GameRuleSliderHelper() {}

    public static RuleBounds getBounds(String ruleKey) {
        String cleanKey = ruleKey;
        if (cleanKey.startsWith("gamerule.")) {
            cleanKey = cleanKey.substring("gamerule.".length());
        }
        return BOUNDS_MAP.getOrDefault(cleanKey, new RuleBounds(0, 100, 0));
    }

    public static int getDefaultValue(String ruleKey) {
        return getBounds(ruleKey).defaultValue();
    }
}
