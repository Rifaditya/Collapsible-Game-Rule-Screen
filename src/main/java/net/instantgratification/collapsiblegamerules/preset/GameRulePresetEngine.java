// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.preset;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.chat.Component;

public final class GameRulePresetEngine {

    public record Preset(String id, Component displayName, Map<String, Object> ruleValues) {}

    private static final Map<String, Preset> BUILTIN_PRESETS = new HashMap<>();

    static {
        // 1. Builder Mode
        Map<String, Object> builderRules = new HashMap<>();
        builderRules.put("doDaylightCycle", false);
        builderRules.put("doWeatherCycle", false);
        builderRules.put("doMobSpawning", false);
        builderRules.put("keepInventory", true);
        builderRules.put("mobGriefing", false);
        builderRules.put("doFireTick", false);
        BUILTIN_PRESETS.put("builder", new Preset("builder", Component.literal("🏰 Builder Mode"), builderRules));

        // 2. Fast Play
        Map<String, Object> fastPlayRules = new HashMap<>();
        fastPlayRules.put("randomTickSpeed", 10);
        fastPlayRules.put("playersSleepingPercentage", 0);
        fastPlayRules.put("keepInventory", true);
        BUILTIN_PRESETS.put("fast_play", new Preset("fast_play", Component.literal("⚡ Fast Play"), fastPlayRules));

        // 3. Hardcore Realism
        Map<String, Object> hardcoreRules = new HashMap<>();
        hardcoreRules.put("naturalRegeneration", false);
        hardcoreRules.put("doInsomnia", true);
        hardcoreRules.put("playersSleepingPercentage", 100);
        BUILTIN_PRESETS.put("hardcore", new Preset("hardcore", Component.literal("💀 Hardcore Realism"), hardcoreRules));
    }

    private GameRulePresetEngine() {}

    public static Map<String, Preset> getBuiltinPresets() {
        return BUILTIN_PRESETS;
    }

    public static Preset getPreset(String id) {
        return BUILTIN_PRESETS.get(id);
    }
}
