// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.model;

import net.instantgratification.collapsiblegamerules.mixin.CategoryRuleEntryAccessor;
import net.instantgratification.collapsiblegamerules.util.CategoryPrettifier;
import net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.ArrayList;
import java.util.List;

/**
 * Builds structured CategoryGroup hierarchies from raw flat rule entry lists in a single O(N) pass.
 * Pre-resolves translations, Dasik metadata, and fallback prettification to eliminate nested list scans.
 */
public final class CategoryTreeBuilder {

    private CategoryTreeBuilder() {}

    /**
     * Ingests a raw list of RuleEntries in a single O(N) linear pass, grouping entries under their respective category headers.
     *
     * @param rawEntries the raw entries produced by Minecraft's AbstractGameRulesScreen.RuleList
     * @return an immutable list of structured CategoryGroup objects
     */
    public static List<CategoryGroup> buildGroups(List<AbstractGameRulesScreen.RuleEntry> rawEntries) {
        if (rawEntries == null || rawEntries.isEmpty()) {
            return List.of();
        }

        List<CategoryGroup> groups = new ArrayList<>();
        Component currentDisplayLabel = null;
        String currentPersistenceKey = null;
        List<AbstractGameRulesScreen.RuleEntry> currentRules = new ArrayList<>();

        for (AbstractGameRulesScreen.RuleEntry entry : rawEntries) {
            if (entry instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
                // If we were already collecting a group, flush it before starting the next category
                if (currentDisplayLabel != null && currentPersistenceKey != null) {
                    groups.add(new CategoryGroup(currentDisplayLabel, currentPersistenceKey, currentRules, countModified(currentRules)));
                    currentRules = new ArrayList<>();
                }

                Component label = ((CategoryRuleEntryAccessor) entry).collapsible_game_rules$getLabel();
                String categoryKey = label.getString();
                String persistenceKey = categoryKey;

                if (label.getContents() instanceof TranslatableContents translatable) {
                    persistenceKey = translatable.getKey();
                }

                // DasikLibrary metadata integration
                if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("dasik-library")) {
                    categoryKey = DasikMetadataHelper.getCategoryTranslation(categoryKey);
                }

                Component displayLabel = label;
                if (label.getContents() instanceof TranslatableContents translatable) {
                    String key = translatable.getKey();
                    if (!Language.getInstance().has(key)) {
                        displayLabel = Component.literal(CategoryPrettifier.prettifyCategoryKey(key));
                    }
                }

                currentDisplayLabel = displayLabel;
                currentPersistenceKey = persistenceKey;
            } else {
                // Orphan rule entry handling (rules encountered before any category header)
                if (currentDisplayLabel == null || currentPersistenceKey == null) {
                    currentDisplayLabel = Component.translatable("gamerule.category.misc");
                    currentPersistenceKey = "gamerule.category.misc";
                }
                currentRules.add(entry);
            }
        }

        // Flush the final group
        if (currentDisplayLabel != null && currentPersistenceKey != null) {
            groups.add(new CategoryGroup(currentDisplayLabel, currentPersistenceKey, currentRules, countModified(currentRules)));
        }

        return List.copyOf(groups);
    }

    private static int countModified(List<AbstractGameRulesScreen.RuleEntry> rules) {
        if (rules == null || rules.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < rules.size(); i++) {
            AbstractGameRulesScreen.RuleEntry entry = rules.get(i);
            if (entry instanceof ResettableRuleEntry resettable && resettable.collapsible_game_rules$isModified()) {
                count++;
            }
        }
        return count;
    }
}
