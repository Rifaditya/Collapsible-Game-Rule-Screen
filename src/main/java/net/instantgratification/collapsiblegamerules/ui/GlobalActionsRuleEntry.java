// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.ui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class GlobalActionsRuleEntry extends AbstractGameRulesScreen.RuleEntry implements NarratableEntry {

    private static final Component EXPAND_LABEL = Component.literal("▼ ").append(Component.translatable("gui.collapsible-game-rules.expand_all"));
    private static final Component COLLAPSE_LABEL = Component.literal("▶ ").append(Component.translatable("gui.collapsible-game-rules.collapse_all"));
    private static final Component NARRATION_TITLE = Component.translatable("gui.collapsible-game-rules.expand_all").append(" / ").append(Component.translatable("gui.collapsible-game-rules.collapse_all"));
    private static final Component NARRATION_USAGE = Component.translatable("gui.collapsible-game-rules.expand_all").append(": Left card. ").append(Component.translatable("gui.collapsible-game-rules.collapse_all")).append(": Right card.");

    private final Runnable expandAll;
    private final Runnable collapseAll;

    public GlobalActionsRuleEntry(Runnable expandAll, Runnable collapseAll) {
        super(null);
        this.expandAll = expandAll;
        this.collapseAll = collapseAll;
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        int leftX = this.getX() + 8;
        int rightX = this.getX() + this.getWidth() - 8;
        int topY = this.getY() + 3;
        int bottomY = this.getY() + 21;

        int midX = leftX + (rightX - leftX) / 2;
        int card1Right = midX - 3;
        int card2Left = midX + 3;

        boolean hoverExpand = hovered && mouseX >= leftX && mouseX <= card1Right && mouseY >= topY && mouseY <= bottomY;
        boolean hoverCollapse = hovered && mouseX >= card2Left && mouseX <= rightX && mouseY >= topY && mouseY <= bottomY;

        int expandColor = hoverExpand ? 0xFFFFFFAA : 0xFFFFFFFF;
        int collapseColor = hoverCollapse ? 0xFFFFFFAA : 0xFFFFFFFF;

        // Card 1: Expand All plate and warm gold accent bar
        int bgExpand = hoverExpand ? 0x24FFFFFF : 0x10FFFFFF;
        graphics.fill(leftX, topY, card1Right, bottomY, bgExpand);
        graphics.fill(leftX, topY, leftX + 2, bottomY, 0xFFFFAA00); // Warm gold accent

        // Card 2: Collapse All plate and crisp lime accent bar
        int bgCollapse = hoverCollapse ? 0x24FFFFFF : 0x10FFFFFF;
        graphics.fill(card2Left, topY, rightX, bottomY, bgCollapse);
        graphics.fill(card2Left, topY, card2Left + 2, bottomY, 0xFF55FF55); // Crisp lime accent

        // Centered labels inside each card
        int card1CenterX = leftX + (card1Right - leftX) / 2;
        int card2CenterX = card2Left + (rightX - card2Left) / 2;
        int textY = this.getY() + 7;

        graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, EXPAND_LABEL, card1CenterX, textY, expandColor);
        graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, COLLAPSE_LABEL, card2CenterX, textY, collapseColor);

        // Subtle separating line at the bottom
        graphics.fill(leftX - 4, bottomY + 2, rightX + 4, bottomY + 3, 0x22AAAAAA);
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0 || event.button() == 1) { // Left or right click
            double mouseX = event.x();
            double mouseY = event.y();

            int leftX = this.getX() + 8;
            int rightX = this.getX() + this.getWidth() - 8;
            int topY = this.getY() + 3;
            int bottomY = this.getY() + 21;

            if (mouseY < topY || mouseY > bottomY) {
                return false;
            }

            int midX = leftX + (rightX - leftX) / 2;
            int card1Right = midX - 3;
            int card2Left = midX + 3;

            if (mouseX >= leftX && mouseX <= card1Right) {
                this.expandAll.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager().play(net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else if (mouseX >= card2Left && mouseX <= rightX) {
                this.collapseAll.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager().play(net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }
        return false;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return ImmutableList.of();
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return ImmutableList.of(this);
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.HOVERED;
    }

    @Override
    public void updateNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, NARRATION_TITLE);
        output.add(NarratedElementType.USAGE, NARRATION_USAGE);
    }
}
