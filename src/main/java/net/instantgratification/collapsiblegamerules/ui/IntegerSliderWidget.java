// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.ui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;

public class IntegerSliderWidget extends AbstractSliderButton {

    private final int min;
    private final int max;
    private final Consumer<Integer> onValueChanged;
    private int currentIntValue;

    public IntegerSliderWidget(int x, int y, int width, int height, int min, int max, int initialValue, Consumer<Integer> onValueChanged) {
        super(x, y, width, height, Component.empty(), calculateDoubleValue(initialValue, min, max));
        this.min = min;
        this.max = max;
        this.currentIntValue = initialValue;
        this.onValueChanged = onValueChanged;
        this.updateMessage();
    }

    private static double calculateDoubleValue(int val, int min, int max) {
        if (max <= min) return 0.0;
        double clamped = Math.max(min, Math.min(max, val));
        return (clamped - min) / (double) (max - min);
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal(String.valueOf(this.currentIntValue)));
    }

    @Override
    protected void applyValue() {
        int calculated = this.min + (int) Math.round(this.value * (this.max - this.min));
        if (calculated != this.currentIntValue) {
            this.currentIntValue = calculated;
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
        this.currentIntValue = Math.max(this.min, Math.min(this.max, newValue));
        this.value = calculateDoubleValue(this.currentIntValue, this.min, this.max);
        this.updateMessage();
    }
}
