// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.instantgratification.collapsiblegamerules.ui.IntegerSliderWidget;
import net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
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
public abstract class IntegerRuleEntryMixin extends AbstractGameRulesScreen.RuleEntry {

    @Shadow
    @Final
    private EditBox input;

    @Shadow
    @Final
    protected List<AbstractWidget> children;

    @Shadow
    protected abstract void extractLabel(GuiGraphicsExtractor graphics, int y, int x);

    @Unique
    private IntegerSliderWidget collapsible_game_rules$sliderWidget;

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
            int initialVal = config.min();
            try {
                initialVal = Integer.parseInt(this.input.getValue());
            } catch (NumberFormatException ignored) {
            }

            this.collapsible_game_rules$sliderWidget = new IntegerSliderWidget(
                    10, 5, 60, 20, config, initialVal,
                    (newVal) -> this.input.setValue(String.valueOf(newVal))
            );

            // Replace vanilla's EditBox inside children list so focus, keyboard and mouse events route to slider
            this.children.remove(this.input);
            this.children.add(this.collapsible_game_rules$sliderWidget);
        }
    }

    @Inject(method = "extractContent", at = @At("HEAD"), cancellable = true)
    private void collapsible_game_rules$renderCustomSlider(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            boolean hovered,
            float partialTick,
            CallbackInfo ci
    ) {
        if (this.collapsible_game_rules$sliderWidget != null) {
            ci.cancel();

            this.extractLabel(graphics, this.getContentY(), this.getContentX());

            this.collapsible_game_rules$sliderWidget.setX(this.getContentRight() - 65);
            this.collapsible_game_rules$sliderWidget.setY(this.getContentY());
            this.collapsible_game_rules$sliderWidget.extractRenderState(graphics, mouseX, mouseY, partialTick);
        }
    }
}