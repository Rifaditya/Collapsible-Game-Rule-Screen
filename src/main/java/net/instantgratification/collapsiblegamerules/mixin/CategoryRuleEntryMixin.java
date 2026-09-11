// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public abstract class CategoryRuleEntryMixin implements CategoryRuleEntryAccessor {

    @Unique
    private Component collapsible_game_rules$label;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void collapsible_game_rules$captureLabel(AbstractGameRulesScreen screen, Component label, CallbackInfo ci) {
        this.collapsible_game_rules$label = label;
    }

    @Override
    public Component collapsible_game_rules$getLabel() {
        return this.collapsible_game_rules$label != null ? this.collapsible_game_rules$label : Component.empty();
    }
}
