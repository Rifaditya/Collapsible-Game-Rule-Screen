/*
 * Sovereign Engineering
 * Verified against: AbstractGameRulesScreen.RuleEntry.java (26.*)
 */
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

    private final Runnable expandAll;
    private final Runnable collapseAll;

    public GlobalActionsRuleEntry(Runnable expandAll, Runnable collapseAll) {
        super(null);
        this.expandAll = expandAll;
        this.collapseAll = collapseAll;
    }

    @Override
    public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        Component expandText = Component.translatable("gui.collapsible-game-rules.expand_all");
        Component collapseText = Component.translatable("gui.collapsible-game-rules.collapse_all");

        boolean hoverExpand = hovered && mouseX < this.getX() + this.getWidth() / 2;
        boolean hoverCollapse = hovered && mouseX >= this.getX() + this.getWidth() / 2;

        int expandColor = hoverExpand ? 0xFFFFFFAA : 0xFFFFFFFF;
        int collapseColor = hoverCollapse ? 0xFFFFFFAA : 0xFFFFFFFF;

        if (hoverExpand) {
            graphics.fill(this.getX(), this.getY(), this.getX() + this.getWidth() / 2, this.getY() + 24, 0x22FFFFFF);
        }
        if (hoverCollapse) {
            graphics.fill(this.getX() + this.getWidth() / 2, this.getY(), this.getX() + this.getWidth(), this.getY() + 24, 0x22FFFFFF);
        }

        graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, Component.literal("[ ").append(expandText).append(" ]"), this.getX() + this.getWidth() / 4, this.getContentY() + 5, expandColor);
        graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, Component.literal("[ ").append(collapseText).append(" ]"), this.getX() + 3 * this.getWidth() / 4, this.getContentY() + 5, collapseColor);

        // Subtle separating line at the bottom
        graphics.fill(this.getX() + 10, this.getY() + 23, this.getX() + this.getWidth() - 10, this.getY() + 24, 0x44AAAAAA);
    }

    @Override
    public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0 || event.button() == 1) {
            double mouseX = event.x();
            if (mouseX < this.getX() + this.getWidth() / 2.0) {
                this.expandAll.run();
            } else {
                this.collapseAll.run();
            }
            net.minecraft.client.Minecraft.getInstance().getSoundManager().play(net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
            return true;
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
        output.add(NarratedElementType.TITLE, Component.translatable("gui.collapsible-game-rules.expand_all").append(" / ").append(Component.translatable("gui.collapsible-game-rules.collapse_all")));
    }
}
