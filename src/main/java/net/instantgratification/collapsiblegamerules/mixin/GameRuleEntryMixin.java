// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.instantgratification.collapsiblegamerules.util.SearchHighlightHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Mixin targeting AbstractGameRulesScreen$GameRuleEntry to highlight search query matches
 * within rule titles when filtering rules.
 */
@Mixin(AbstractGameRulesScreen.GameRuleEntry.class)
public abstract class GameRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {

    @Shadow
    protected List<FormattedCharSequence> label;

    public GameRuleEntryMixin() {
        super(null);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void collapsible_game_rules$highlightRuleLabel(
            AbstractGameRulesScreen screen,
            List<FormattedCharSequence> tooltip,
            Component label,
            CallbackInfo ci
    ) {
        if (screen != null) {
            EditBox searchBox = ((AbstractGameRulesScreenAccessor) screen).collapsible_game_rules$getSearchBox();
            if (searchBox != null) {
                String query = searchBox.getValue();
                if (query != null && !query.trim().isEmpty()) {
                    Component highlighted = SearchHighlightHelper.highlight(label, query);
                    this.label = Minecraft.getInstance().font.split(highlighted, 250);

                    List<FormattedCharSequence> currentTooltip = ((RuleEntryAccessor) this).collapsible_game_rules$getTooltip();
                    if (currentTooltip != null && !currentTooltip.isEmpty()) {
                        List<FormattedCharSequence> highlightedTooltip = new java.util.ArrayList<>(currentTooltip.size());
                        for (FormattedCharSequence line : currentTooltip) {
                            highlightedTooltip.add(SearchHighlightHelper.highlightSequence(line, query));
                        }
                        ((RuleEntryAccessor) this).collapsible_game_rules$setTooltip(highlightedTooltip);
                    }
                } else {
                    this.label = Minecraft.getInstance().font.split(label, 250);
                }
            } else {
                this.label = Minecraft.getInstance().font.split(label, 250);
            }
        }
    }
}
