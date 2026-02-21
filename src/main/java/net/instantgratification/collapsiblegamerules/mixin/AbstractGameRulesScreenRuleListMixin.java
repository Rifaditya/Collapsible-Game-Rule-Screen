package net.instantgratification.collapsiblegamerules.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Mixin(AbstractGameRulesScreen.RuleList.class)
public abstract class AbstractGameRulesScreenRuleListMixin
        extends ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry> {

    public AbstractGameRulesScreenRuleListMixin() {
        super(null, 0, 0, 0, 0);
    }

    // Store expanded categories
    @Unique
    private final Map<Component, Boolean> collapsiblegamerules$expandedCategories = new HashMap<>();

    @Unique
    private List<AbstractGameRulesScreen.RuleEntry> collapsiblegamerules$allEntries = new ArrayList<>();

    @Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
    private void collapsiblegamerules$onPopulateChildren(String filter, CallbackInfo ci) {
        LoggerFactory.getLogger("collapsible-game-rules")
                .info("Inside onPopulateChildren TAIL! Children size: " + this.children().size());
        // Save the currently generated list of all entries
        this.collapsiblegamerules$allEntries = new ArrayList<>(this.children());
        this.collapsiblegamerules$updateVisibleEntries();
    }

    @Unique
    private void collapsiblegamerules$updateVisibleEntries() {
        Logger logger = LoggerFactory.getLogger("collapsible-game-rules");
        logger.info("Updating visible entries! allEntries size: " + this.collapsiblegamerules$allEntries.size());
        this.clearEntries();
        boolean currentCategoryExpanded = true; // Assume true if no category found initially

        for (AbstractGameRulesScreen.RuleEntry entry : this.collapsiblegamerules$allEntries) {
            if (entry instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
                Component label = ((CategoryRuleEntryAccessor) entry).collapsiblegamerules$getLabel();
                this.collapsiblegamerules$expandedCategories.putIfAbsent(label, false); // Default collapsed

                CollapsibleCategoryRuleEntry newEntry = new CollapsibleCategoryRuleEntry(label,
                        this.collapsiblegamerules$expandedCategories.get(label), () -> {
                            this.collapsiblegamerules$expandedCategories.put(label,
                                    !this.collapsiblegamerules$expandedCategories.get(label));
                            this.collapsiblegamerules$updateVisibleEntries();
                        });
                this.addEntry(newEntry);
                currentCategoryExpanded = this.collapsiblegamerules$expandedCategories.get(label);
            } else {
                // Normal game rule entry
                if (currentCategoryExpanded) {
                    this.addEntry(entry);
                }
            }
        }

        // Force the abstract selection list to recalculate the Y layout for all
        // existing children
        this.updateSizeAndPosition(this.getWidth(), this.getHeight(), this.getX(), this.getY());
    }

    @Unique
    private class CollapsibleCategoryRuleEntry extends AbstractGameRulesScreen.RuleEntry implements NarratableEntry {
        private final Component label;
        private final boolean expanded;
        private final Runnable toggleAction;

        public CollapsibleCategoryRuleEntry(Component label, boolean expanded, Runnable toggleAction) {
            super(null);
            this.label = label;
            this.expanded = expanded;
            this.toggleAction = toggleAction;
        }

        @Override
        public void renderContent(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float a) {
            // Draw the label
            String prefix = this.expanded ? "[-] " : "[+] ";
            Component display = Component.literal(prefix).append(this.label);

            graphics.drawCenteredString(net.minecraft.client.Minecraft.getInstance().font, display,
                    this.getContentXMiddle(), this.getContentY() + 5, hovered ? 0xFFFFFFAA : 0xFFFFFFFF);
        }

        @Override
        public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
            if (event.button() == 0) { // Left click
                this.toggleAction.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager()
                        .play(net.minecraft.client.resources.sounds.SimpleSoundInstance
                                .forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
            return false;
        }

        @Override
        public List<? extends net.minecraft.client.gui.components.events.GuiEventListener> children() {
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
            output.add(NarratedElementType.TITLE, this.label);
        }
    }
}
