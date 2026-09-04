// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.ui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;

/**
 * Modern glassmorphic toggle switch widget with high-contrast Emerald [ON]
 * and Ruby [OFF] pill aesthetics and 0B heap allocation per render frame.
 */
public class BooleanToggleWidget extends AbstractButton {

    private static final Component ON_LABEL = Component.literal("● ON").withStyle(net.minecraft.ChatFormatting.GREEN);
    private static final Component OFF_LABEL = Component.literal("OFF ●").withStyle(net.minecraft.ChatFormatting.RED);

    private boolean state;
    private final Consumer<Boolean> onToggle;

    public BooleanToggleWidget(int x, int y, int width, int height, boolean initialState, Consumer<Boolean> onToggle) {
        super(x, y, width, height, initialState ? ON_LABEL : OFF_LABEL);
        this.state = initialState;
        this.onToggle = onToggle;
    }

    private static Component getDisplayComponent(boolean state) {
        return state ? ON_LABEL : OFF_LABEL;
    }

    @Override
    public void onPress(InputWithModifiers input) {
        this.state = !this.state;
        this.setMessage(getDisplayComponent(this.state));
        net.minecraft.client.Minecraft.getInstance().getSoundManager().play(
                net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(
                        net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
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
        int leftX = this.getX();
        int topY = this.getY();
        int rightX = leftX + this.getWidth();
        int bottomY = topY + this.getHeight();

        boolean hovered = this.isHovered();

        // High-contrast background plate
        int bgColor;
        if (this.state) {
            bgColor = hovered ? 0x4400CC55 : 0x2400AA44; // Emerald glow
        } else {
            bgColor = hovered ? 0x44CC2222 : 0x24AA1111; // Ruby glow
        }
        graphics.fill(leftX, topY, rightX, bottomY, bgColor);

        // Thin outer pill boundary
        int borderColor = hovered ? 0x55FFFFFF : 0x22FFFFFF;
        graphics.fill(leftX, topY, rightX, topY + 1, borderColor); // Top
        graphics.fill(leftX, bottomY - 1, rightX, bottomY, borderColor); // Bottom
        graphics.fill(leftX, topY, leftX + 1, bottomY, borderColor); // Left
        graphics.fill(rightX - 1, topY, rightX, bottomY, borderColor); // Right

        // Accent indicator thumb: left strip for ON, right strip for OFF
        if (this.state) {
            graphics.fill(leftX, topY, leftX + 3, bottomY, 0xFF00FF66); // Emerald accent
        } else {
            graphics.fill(rightX - 3, topY, rightX, bottomY, 0xFFFF3333); // Ruby accent
        }

        // Centered state text
        int textColor = this.active ? (hovered ? 0xFFFFFFAA : 0xFFFFFFFF) : 0xFFA0A0A0;
        int textY = topY + (this.getHeight() - 8) / 2;
        graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, this.getMessage(), leftX + this.getWidth() / 2, textY, textColor);
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean newState) {
        this.state = newState;
        this.setMessage(getDisplayComponent(this.state));
    }
}
