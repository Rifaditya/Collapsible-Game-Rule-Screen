// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {

    public IntegerRuleEntryMixin() {
        super(null);
    }
}
