// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.ui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;

public class BooleanToggleWidget extends AbstractButton {

    private boolean state;
    private final Consumer<Boolean> onToggle;

    public BooleanToggleWidget(int x, int y, int width, int height, boolean initialState, Consumer<Boolean> onToggle) {
        super(x, y, width, height, getDisplayComponent(initialState));
        this.state = initialState;
        this.onToggle = onToggle;
    }

    private static Component getDisplayComponent(boolean state) {
        if (state) {
            return Component.literal("✔ ON").withStyle(net.minecraft.ChatFormatting.GREEN);
        } else {
            return Component.literal("✖ OFF").withStyle(net.minecraft.ChatFormatting.RED);
        }
    }

    @Override
    public void onPress(InputWithModifiers input) {
        this.state = !this.state;
        this.setMessage(getDisplayComponent(this.state));
        if (this.onToggle != null) {
            this.onToggle.accept(this.state);
        }
    }

    @Override
    protected void updateWidgetNarration(net.minecraft.client.gui.narration.NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
    }

    @Override
    public void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int color = this.active ? (this.isHovered() ? 0xFFFFFFAA : 0xFFFFFFFF) : 0xFFA0A0A0;
        int bgColor = this.state ? 0x4400FF00 : 0x44FF0000;
        graphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), bgColor);
        graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, this.getMessage(), this.getX() + this.getWidth() / 2, this.getY() + (this.getHeight() - 8) / 2, color);
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean newState) {
        this.state = newState;
        this.setMessage(getDisplayComponent(this.state));
    }
}
