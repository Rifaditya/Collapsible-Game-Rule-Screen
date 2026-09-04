// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.instantgratification.collapsiblegamerules.model.ResettableRuleEntry;
import net.instantgratification.collapsiblegamerules.ui.IntegerSliderWidget;
import net.instantgratification.collapsiblegamerules.util.CategoryResetHelper;
import net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
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

@Mixin(AbstractGameRulesScreen.IntegerRuleEntry.class)
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry implements ResettableRuleEntry {

    @Shadow
    @Final
    private EditBox input;

    @Unique
    private IntegerSliderWidget collapsible_game_rules$sliderWidget;

    @Unique
    private GameRule<Integer> collapsible_game_rules$rule;

    @Unique
    private AbstractGameRulesScreen collapsible_game_rules$screen;

    public IntegerRuleEntryMixin() {
        super(null);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void collapsible_game_rules$initSliderWidget(
            AbstractGameRulesScreen screen,
            Component name,
            List<FormattedCharSequence> label,
            String description,
            GameRule<Integer> rule,
            CallbackInfo ci
    ) {
        this.collapsible_game_rules$screen = screen;
        this.collapsible_game_rules$rule = rule;
        if (rule == null) {
            return;
        }

        GameRuleSliderHelper.SliderConfig config = null;
        try {
            if (rule.id() != null) {
                config = GameRuleSliderHelper.getConfig(rule.id());
            }
        } catch (Exception ignored) {
        }

        if (config == null) {
            try {
                net.minecraft.resources.Identifier id = rule.getIdentifierWithFallback();
                if (id != null) {
                    config = GameRuleSliderHelper.getConfig(id.getPath());
                }
            } catch (Exception ignored) {
            }
        }

        if (config == null && description != null) {
            config = GameRuleSliderHelper.getConfig(description);
        }

        if (config != null) {
            this.input.visible = false;
            int initialVal = config.min();
            try {
                initialVal = Integer.parseInt(this.input.getValue());
            } catch (NumberFormatException ignored) {
            }

            this.collapsible_game_rules$sliderWidget = new IntegerSliderWidget(
                    10, 5, 60, 20, config, initialVal,
                    (newVal) -> this.input.setValue(String.valueOf(newVal))
            );

            @SuppressWarnings("rawtypes")
            List rawList = (List) this.children();
            rawList.remove(this.input);
            rawList.add(this.collapsible_game_rules$sliderWidget);
        }
    }

    @Override
    public boolean collapsible_game_rules$isModified() {
        if (this.collapsible_game_rules$rule == null) {
            return false;
        }
        String valStr = this.input.getValue();
        try {
            int currentVal = Integer.parseInt(valStr.trim());
            return CategoryResetHelper.isModified(currentVal, this.collapsible_game_rules$rule.defaultValue());
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void collapsible_game_rules$resetToDefault() {
        if (this.collapsible_game_rules$rule == null) {
            return;
        }
        int defaultVal = this.collapsible_game_rules$rule.defaultValue();
        this.input.setValue(String.valueOf(defaultVal));
        if (this.collapsible_game_rules$sliderWidget != null) {
            this.collapsible_game_rules$sliderWidget.setValueAsInt(defaultVal);
        }
        if (this.collapsible_game_rules$screen != null) {
            ((AbstractGameRulesScreenAccessor) this.collapsible_game_rules$screen)
                    .collapsible_game_rules$getGameRules()
                    .set(this.collapsible_game_rules$rule, defaultVal, null);
        }
    }

    @Inject(method = "extractContent", at = @At("TAIL"))
    private void collapsible_game_rules$renderCustomSlider(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            boolean hovered,
            float partialTick,
            CallbackInfo ci
    ) {
        if (this.collapsible_game_rules$sliderWidget != null) {
            this.collapsible_game_rules$sliderWidget.setX(this.getContentRight() - 65);
            this.collapsible_game_rules$sliderWidget.setY(this.getContentY());
            this.collapsible_game_rules$sliderWidget.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
    }
}