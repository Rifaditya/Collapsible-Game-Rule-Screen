package net.instantgratification.collapsiblegamerules.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
import net.minecraft.client.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.instantgratification.collapsiblegamerules.GameRuleStateConfig;
import net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry;
import net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric;

@Mixin(AbstractGameRulesScreen.RuleList.class)
public abstract class AbstractGameRulesScreenRuleListMixin
        extends ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry> {

    public AbstractGameRulesScreenRuleListMixin() {
        super(null, 0, 0, 0, 0);
    }

    // Removed internal map, using GameRuleStateConfig instead


    @Unique
    private List<AbstractGameRulesScreen.RuleEntry> collapsiblegamerules$allEntries = new ArrayList<>();

    @Unique
    private String collapsiblegamerules$currentFilter = "";

    @Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
    private void collapsiblegamerules$onPopulateChildren(String filter, CallbackInfo ci) {
        this.collapsiblegamerules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
        // Save the currently generated list of all entries
        this.collapsiblegamerules$allEntries = new ArrayList<>(this.children());
        this.collapsiblegamerules$updateVisibleEntries();
    }


    @Unique
    private void collapsiblegamerules$updateVisibleEntries() {
        this.clearEntries();

        // 1. Hook GlobalActions UI at index 0 (if there are any rules)
        if (!this.collapsiblegamerules$allEntries.isEmpty()) {
            this.addEntry(new GlobalActionsRuleEntry(
                () -> {
                    List<String> allKeys = this.collapsiblegamerules$allEntries.stream()
                        .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                        .map(e -> ((CategoryRuleEntryAccessor) e).collapsiblegamerules$getLabel().getString())
                        .collect(Collectors.toList());
                    GameRuleStateConfig.expandAll(allKeys);
                    this.collapsiblegamerules$updateVisibleEntries();
                },
                () -> {
                    GameRuleStateConfig.collapseAll();
                    this.collapsiblegamerules$updateVisibleEntries();
                }
            ));
        }

        boolean currentCategoryExpanded = true; // Assume true if no category found initially

        for (AbstractGameRulesScreen.RuleEntry entry : this.collapsiblegamerules$allEntries) {
            if (entry instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
                Component label = ((CategoryRuleEntryAccessor) entry).collapsiblegamerules$getLabel();
                String categoryKey = label.getString();
                
                // 3. DasikLibrary Metadata Integration Hook
                // If DasikLibrary is present, we would check: DasikLibrary.getGameRuleMetadata(categoryKey)
                // For now, it respects native GameRules.Category out of the box.
                
                // Smart Search: if there's an active filter and we are populating children,
                // vanilla already filters the list. If this category header is here, it means
                // a child rule matched OR the category name matched. We should expand it to show results.
                boolean isSearching = !this.collapsiblegamerules$currentFilter.isEmpty();
                
                boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(categoryKey);

                CollapsibleCategoryRuleEntry newEntry = new CollapsibleCategoryRuleEntry(label,
                        isExpanded, () -> {
                            boolean newState = !GameRuleStateConfig.isExpanded(categoryKey);
                            GameRuleStateConfig.setExpanded(categoryKey, newState);
                            GameRuleStateConfig.save();
                            this.collapsiblegamerules$updateVisibleEntries();
                        });
                this.addEntry(newEntry);
                currentCategoryExpanded = isExpanded;
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
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            // Zenith Premium Highlight on hover
            if (hovered) {
                graphics.fill(this.getX() - 2, this.getY(), this.getX() + this.getWidth() + 2, this.getY() + 24, 0x22FFFFFF);
            }

            // Draw the directional arrow and label
            String prefix = this.expanded ? "▼ " : "▶ ";
            Component display = Component.literal(prefix).append(this.label);

            graphics.centeredText(net.minecraft.client.Minecraft.getInstance().font, display,
                    this.getContentXMiddle(), this.getContentY() + 5, hovered ? 0xFFFFFFAA : 0xFFFFFFFF);
            
            // Subtle separating line at the bottom
            graphics.fill(this.getX() + 10, this.getY() + 23, this.getX() + this.getWidth() - 10, this.getY() + 24, 0x44AAAAAA);
        }

        @Override
        public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
            if (event.button() == 0 || event.button() == 1) { // Left or right click toggles
                this.toggleAction.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager()
                        .play(net.minecraft.client.resources.sounds.SimpleSoundInstance
                                .forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
            return false;
        }

        @Override
        public boolean keyPressed(KeyEvent event) {
            int keyCode = event.key();
            if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER || keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ENTER || keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE) {
                this.toggleAction.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager()
                        .play(net.minecraft.client.resources.sounds.SimpleSoundInstance
                                .forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT && this.expanded) {
                this.toggleAction.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager().play(net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            } else if (keyCode == org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT && !this.expanded) {
                this.toggleAction.run();
                net.minecraft.client.Minecraft.getInstance().getSoundManager().play(net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
            return super.keyPressed(event);
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
