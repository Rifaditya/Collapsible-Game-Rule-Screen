/*
 * Zenith Sovereign Engineering
 * Verified against: AbstractGameRulesScreen.java (26.*)
 */
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
import net.instantgratification.collapsiblegamerules.GameRuleStateConfig;
import net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry;
import net.instantgratification.collapsiblegamerules.util.DasikMetadataHelper;
import net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric;

@Mixin(AbstractGameRulesScreen.RuleList.class)
public abstract class AbstractGameRulesScreenRuleListMixin
        extends ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry> {

    public AbstractGameRulesScreenRuleListMixin() {
        super(null, 0, 0, 0, 0);
    }

    // Removed internal map, using GameRuleStateConfig instead


    @Unique
    private List<AbstractGameRulesScreen.RuleEntry> collapsible_game_rules$allEntries = new ArrayList<>();

    @Unique
    private String collapsible_game_rules$currentFilter = "";

    @Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
    private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
        this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
        // Save the currently generated list of all entries
        this.collapsible_game_rules$allEntries = new ArrayList<>(this.children());
        this.collapsible_game_rules$updateVisibleEntries();
    }


    @Unique
    private void collapsible_game_rules$updateVisibleEntries() {
        this.clearEntries();

        // 1. Hook GlobalActions UI at index 0 (if there are any rules)
        if (!this.collapsible_game_rules$allEntries.isEmpty()) {
            this.addEntry(new GlobalActionsRuleEntry(
                () -> {
                    List<String> allKeys = this.collapsible_game_rules$allEntries.stream()
                        .filter(e -> e instanceof AbstractGameRulesScreen.CategoryRuleEntry)
                        .map(e -> ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel().getString())
                        .toList();
                    GameRuleStateConfig.expandAll(allKeys);
                    this.collapsible_game_rules$updateVisibleEntries();
                },
                () -> {
                    GameRuleStateConfig.collapseAll();
                    this.collapsible_game_rules$updateVisibleEntries();
                }
            ));
        }

        boolean currentCategoryExpanded = true; // Assume true if no category found initially

        for (AbstractGameRulesScreen.RuleEntry entry : this.collapsible_game_rules$allEntries) {
            if (entry instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
                Component label = ((CategoryRuleEntryAccessor) entry).collapsible_game_rules$getLabel();
                String categoryKey = label.getString();
                
                // 3. DasikLibrary Metadata Integration Hook (lazy-loaded via helper)
                if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("dasik-library")) {
                    categoryKey = DasikMetadataHelper.getCategoryTranslation(categoryKey);
                }
                
                // Smart Search: if there's an active filter and we are populating children,
                // vanilla already filters the list. If this category header is here, it means
                // a child match OR the category name matched. We should expand it to show results.
                boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();
                
                final String finalCategoryKey = categoryKey;
                boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalCategoryKey);

                CollapsibleCategoryRuleEntry newEntry = new CollapsibleCategoryRuleEntry(label,
                        isExpanded, () -> {
                            boolean newState = !GameRuleStateConfig.isExpanded(finalCategoryKey);
                            GameRuleStateConfig.setExpanded(finalCategoryKey, newState);
                            GameRuleStateConfig.saveIfDirty();
                            this.collapsible_game_rules$updateVisibleEntries();
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
