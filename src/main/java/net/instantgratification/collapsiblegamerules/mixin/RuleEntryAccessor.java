// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * Accessor for AbstractGameRulesScreen$RuleEntry to inspect and update rule tooltips.
 */
@Mixin(AbstractGameRulesScreen.RuleEntry.class)
public interface RuleEntryAccessor {

    @Accessor("tooltip")
    List<FormattedCharSequence> collapsible_game_rules$getTooltip();

    @Mutable
    @Accessor("tooltip")
    void collapsible_game_rules$setTooltip(List<FormattedCharSequence> tooltip);
}
