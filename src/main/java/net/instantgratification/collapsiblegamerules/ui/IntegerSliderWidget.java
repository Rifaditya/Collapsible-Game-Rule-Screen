// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.ui;

import net.instantgratification.collapsiblegamerules.util.GameRuleSliderHelper;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class IntegerSliderWidget extends AbstractSliderButton {

    private final GameRuleSliderHelper.SliderConfig config;
    private final Consumer<Integer> onValueChanged;
    private int currentIntValue;

    public IntegerSliderWidget(int x, int y, int width, int height, GameRuleSliderHelper.SliderConfig config, int initialValue, Consumer<Integer> onValueChanged) {
        super(x, y, width, height, Component.empty(), calculateDoubleValue(initialValue, config != null ? config.min() : 0, config != null ? config.max() : 100));
        this.config = config != null ? config : new GameRuleSliderHelper.SliderConfig(0, 100, 1, "");
        this.currentIntValue = GameRuleSliderHelper.snapAndClamp(initialValue, this.config);
        this.onValueChanged = onValueChanged;
        this.updateMessage();
    }

    public IntegerSliderWidget(int x, int y, int width, int height, int min, int max, int initialValue, Consumer<Integer> onValueChanged) {
        this(x, y, width, height, new GameRuleSliderHelper.SliderConfig(min, max, 1, ""), initialValue, onValueChanged);
    }

    private static double calculateDoubleValue(int val, int min, int max) {
        if (max <= min) return 0.0;
        double clamped = Math.max(min, Math.min(max, val));
        return (clamped - min) / (double) (max - min);
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal(GameRuleSliderHelper.formatValue(this.currentIntValue, this.config)));
    }

    @Override
    protected void applyValue() {
        int raw = this.config.min() + (int) Math.round(this.value * (this.config.max() - this.config.min()));
        int snapped = GameRuleSliderHelper.snapAndClamp(raw, this.config);
        this.value = calculateDoubleValue(snapped, this.config.min(), this.config.max());
        if (snapped != this.currentIntValue) {
            this.currentIntValue = snapped;
            this.updateMessage();
            if (this.onValueChanged != null) {
                this.onValueChanged.accept(this.currentIntValue);
            }
        }
    }

    public int getValueAsInt() {
        return this.currentIntValue;
    }

    public void setValueAsInt(int newValue) {
        this.currentIntValue = GameRuleSliderHelper.snapAndClamp(newValue, this.config);
        this.value = calculateDoubleValue(this.currentIntValue, this.config.min(), this.config.max());
        this.updateMessage();
    }

    public GameRuleSliderHelper.SliderConfig getConfig() {
        return this.config;
    }
}