/*
 * Sovereign Engineering
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
import net.instantgratification.collapsiblegamerules.model.CategoryGroup;
import net.instantgratification.collapsiblegamerules.model.CategoryTreeBuilder;
import net.instantgratification.collapsiblegamerules.ui.GlobalActionsRuleEntry;

@Mixin(AbstractGameRulesScreen.RuleList.class)
public abstract class AbstractGameRulesScreenRuleListMixin
        extends ContainerObjectSelectionList<AbstractGameRulesScreen.RuleEntry> {

    public AbstractGameRulesScreenRuleListMixin() {
        super(null, 0, 0, 0, 0);
    }

    @Unique
    private List<CategoryGroup> collapsible_game_rules$groups = new ArrayList<>();

    @Unique
    private String collapsible_game_rules$currentFilter = "";

    @Unique
    private GlobalActionsRuleEntry collapsible_game_rules$cachedGlobalActions;

    @Unique
    private final java.util.Map<String, CollapsibleCategoryRuleEntry> collapsible_game_rules$cachedCategoryEntries = new java.util.HashMap<>();

    @Inject(method = "populateChildren(Ljava/lang/String;)V", at = @At("TAIL"))
    private void collapsible_game_rules$onPopulateChildren(String filter, CallbackInfo ci) {
        this.collapsible_game_rules$currentFilter = (filter != null) ? filter.toLowerCase(java.util.Locale.ROOT) : "";
        // Ingest entries linearly in a single O(N) pass, pre-caching metadata and rule counts
        this.collapsible_game_rules$groups = CategoryTreeBuilder.buildGroups(new ArrayList<>(this.children()));

        // Pre-build persistent entries to eliminate allocation churn on click/toggle
        this.collapsible_game_rules$cachedGlobalActions = new GlobalActionsRuleEntry(
            () -> {
                List<String> allKeys = this.collapsible_game_rules$groups.stream()
                    .map(CategoryGroup::persistenceKey)
                    .toList();
                GameRuleStateConfig.expandAll(allKeys);
                this.collapsible_game_rules$updateVisibleEntries();
            },
            () -> {
                GameRuleStateConfig.collapseAll();
                this.collapsible_game_rules$updateVisibleEntries();
            }
        );

        this.collapsible_game_rules$cachedCategoryEntries.clear();
        for (CategoryGroup group : this.collapsible_game_rules$groups) {
            String persistenceKey = group.persistenceKey();
            this.collapsible_game_rules$cachedCategoryEntries.put(
                persistenceKey,
                new CollapsibleCategoryRuleEntry(
                    group,
                    GameRuleStateConfig.isExpanded(persistenceKey),
                    () -> {
                        boolean newState = !GameRuleStateConfig.isExpanded(persistenceKey);
                        GameRuleStateConfig.setExpanded(persistenceKey, newState);
                        GameRuleStateConfig.saveIfDirty();
                        this.collapsible_game_rules$updateVisibleEntries();
                    }
                )
            );
        }

        this.collapsible_game_rules$updateVisibleEntries();
    }

    @Unique
    private void collapsible_game_rules$updateVisibleEntries() {
        this.clearEntries();

        // 1. Hook GlobalActions UI at index 0 (if there are any rules)
        if (!this.collapsible_game_rules$groups.isEmpty() && this.collapsible_game_rules$cachedGlobalActions != null) {
            this.addEntry(this.collapsible_game_rules$cachedGlobalActions);
        }

        boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();

        for (CategoryGroup group : this.collapsible_game_rules$groups) {
            String persistenceKey = group.persistenceKey();
            boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(persistenceKey);

            CollapsibleCategoryRuleEntry entry = this.collapsible_game_rules$cachedCategoryEntries.get(persistenceKey);
            if (entry != null) {
                entry.setExpanded(isExpanded);
                this.addEntry(entry);
            }

            if (isExpanded) {
                for (AbstractGameRulesScreen.RuleEntry rule : group.rules()) {
                    this.addEntry(rule);
                }
            }
        }

        // Force the abstract selection list to recalculate the Y layout for all existing children
        this.updateSizeAndPosition(this.getWidth(), this.getHeight(), this.getX(), this.getY());
    }

    @Unique
    private class CollapsibleCategoryRuleEntry extends AbstractGameRulesScreen.RuleEntry implements NarratableEntry {
        private final CategoryGroup group;
        private boolean expanded;
        private final Runnable toggleAction;

        public CollapsibleCategoryRuleEntry(CategoryGroup group, boolean expanded, Runnable toggleAction) {
            super(null);
            this.group = group;
            this.expanded = expanded;
            this.toggleAction = toggleAction;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            net.minecraft.client.gui.Font font = net.minecraft.client.Minecraft.getInstance().font;

            int leftX = this.getX() + 8;
            int topY = this.getY() + 3;
            int rightX = this.getX() + this.getWidth() - 8;
            int bottomY = this.getY() + 21;

            // Subtle card background plate (elevated on hover)
            int bgColor = hovered ? 0x24FFFFFF : 0x10FFFFFF;
            graphics.fill(leftX - 4, topY, rightX + 4, bottomY, bgColor);

            // Left accent vertical bar indicating category presence
            int accentColor = this.expanded ? 0xFFFFAA00 : 0xFF55FF55; // Warm gold when expanded, crisp lime/green when collapsed
            graphics.fill(leftX - 4, topY, leftX - 2, bottomY, accentColor);

            // Left-aligned directional arrow and category title
            Component leftTitle = this.expanded ? this.group.expandedLeft() : this.group.collapsedLeft();
            int titleColor = hovered ? 0xFFFFFFAA : 0xFFFFFFFF;
            graphics.text(font, leftTitle, leftX + 2, this.getY() + 7, titleColor);

            // Right-anchored rule count badge directly before scrollbar
            Component badge = this.group.countBadge();
            int badgeWidth = font.width(badge);
            int badgeX = rightX - badgeWidth;
            graphics.text(font, badge, badgeX, this.getY() + 7, 0xFFAAAAAA);

            // Subtle separating hairline at card footer
            graphics.fill(leftX - 4, bottomY + 2, rightX + 4, bottomY + 3, 0x22AAAAAA);
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
            output.add(NarratedElementType.TITLE, this.group.displayLabel());
        }
    }
}
