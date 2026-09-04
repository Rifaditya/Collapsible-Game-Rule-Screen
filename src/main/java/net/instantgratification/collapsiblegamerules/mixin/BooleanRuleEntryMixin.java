// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.instantgratification.collapsiblegamerules.model.ResettableRuleEntry;
import net.instantgratification.collapsiblegamerules.ui.BooleanToggleWidget;
import net.instantgratification.collapsiblegamerules.util.CategoryResetHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.gamerules.GameRule;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(AbstractGameRulesScreen.BooleanRuleEntry.class)
public abstract class BooleanRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry implements ResettableRuleEntry {

    @Shadow
    @Final
    private CycleButton<Boolean> checkbox;

    @Unique
    private BooleanToggleWidget collapsible_game_rules$toggleWidget;

    @Unique
    private GameRule<Boolean> collapsible_game_rules$rule;

    @Unique
    private AbstractGameRulesScreen collapsible_game_rules$screen;

    public BooleanRuleEntryMixin() {
        super(null);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void collapsible_game_rules$initToggleWidget(
            AbstractGameRulesScreen screen,
            Component name,
            List<FormattedCharSequence> label,
            String description,
            GameRule<Boolean> rule,
            CallbackInfo ci
    ) {
        this.collapsible_game_rules$screen = screen;
        this.collapsible_game_rules$rule = rule;
        this.checkbox.visible = false;
        boolean initialVal = Boolean.TRUE.equals(this.checkbox.getValue());

        this.collapsible_game_rules$toggleWidget = new BooleanToggleWidget(
                10, 5, 44, 20, initialVal,
                (newState) -> {
                    this.checkbox.setValue(newState);
                    if (this.collapsible_game_rules$screen != null && this.collapsible_game_rules$rule != null) {
                        ((AbstractGameRulesScreenAccessor) this.collapsible_game_rules$screen)
                                .collapsible_game_rules$getGameRules()
                                .set(this.collapsible_game_rules$rule, newState, null);
                    }
                }
        );

        @SuppressWarnings("rawtypes")
        List rawList = (List) this.children();
        rawList.remove(this.checkbox);
        rawList.add(this.collapsible_game_rules$toggleWidget);
    }

    @Override
    public boolean collapsible_game_rules$isModified() {
        if (this.collapsible_game_rules$rule == null) {
            return false;
        }
        Boolean currentVal = this.checkbox.getValue();
        if (currentVal == null) {
            return false;
        }
        return CategoryResetHelper.isModified(currentVal, this.collapsible_game_rules$rule.defaultValue());
    }

    @Override
    public void collapsible_game_rules$resetToDefault() {
        if (this.collapsible_game_rules$rule == null) {
            return;
        }
        boolean defaultVal = this.collapsible_game_rules$rule.defaultValue();
        this.checkbox.setValue(defaultVal);
        if (this.collapsible_game_rules$toggleWidget != null) {
            this.collapsible_game_rules$toggleWidget.setState(defaultVal);
        }
        if (this.collapsible_game_rules$screen != null) {
            ((AbstractGameRulesScreenAccessor) this.collapsible_game_rules$screen)
                    .collapsible_game_rules$getGameRules()
                    .set(this.collapsible_game_rules$rule, defaultVal, null);
        }
    }

    @Inject(method = "extractContent", at = @At("TAIL"))
    private void collapsible_game_rules$renderCustomToggle(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            boolean hovered,
            float partialTick,
            CallbackInfo ci
    ) {
        if (this.collapsible_game_rules$toggleWidget != null) {
            this.collapsible_game_rules$toggleWidget.setX(this.getContentRight() - 45);
            this.collapsible_game_rules$toggleWidget.setY(this.getContentY());
            this.collapsible_game_rules$toggleWidget.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
    }
}