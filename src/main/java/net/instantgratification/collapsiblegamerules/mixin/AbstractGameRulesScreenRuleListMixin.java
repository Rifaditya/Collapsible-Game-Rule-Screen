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
import net.instantgratification.collapsiblegamerules.model.ResettableRuleEntry;
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
                GameRuleStateConfig.saveIfDirty();
                this.collapsible_game_rules$updateVisibleEntries();
            },
            () -> {
                GameRuleStateConfig.collapseAll();
                GameRuleStateConfig.saveIfDirty();
                this.collapsible_game_rules$updateVisibleEntries();
            }
        );

        boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();

        this.collapsible_game_rules$cachedCategoryEntries.clear();
        for (CategoryGroup group : this.collapsible_game_rules$groups) {
            String persistenceKey = group.persistenceKey();
            CategoryGroup activeGroup = isSearching ? group.withMatchCount(group.ruleCount()) : group;
            this.collapsible_game_rules$cachedCategoryEntries.put(
                persistenceKey,
                new CollapsibleCategoryRuleEntry(
                    activeGroup,
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

    private class CollapsibleCategoryRuleEntry extends AbstractGameRulesScreen.RuleEntry implements NarratableEntry {
        private static final Component RESET_ICON = Component.literal("↺").withStyle(net.minecraft.ChatFormatting.GOLD);
        private static final Component RESET_TOOLTIP = Component.literal("Reset category to defaults");

        private final CategoryGroup group;
        private boolean expanded;
        private final Runnable toggleAction;

        private net.minecraft.util.FormattedCharSequence cachedExpandedTitle;
        private net.minecraft.util.FormattedCharSequence cachedCollapsedTitle;
        private int lastWidth = -1;
        private int lastBadgeWidth = -1;
        private boolean lastResetVisible = false;
        private boolean isTruncated = false;
        private int lastModifiedCount = -1;
        private boolean lastIsSearching = false;
        private Component cachedBadge;

        public CollapsibleCategoryRuleEntry(CategoryGroup group, boolean expanded, Runnable toggleAction) {
            super(null);
            this.group = group;
            this.expanded = expanded;
            this.toggleAction = toggleAction;
        }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        private int countModified() {
            int count = 0;
            List<AbstractGameRulesScreen.RuleEntry> rules = this.group.rules();
            for (int i = 0; i < rules.size(); i++) {
                AbstractGameRulesScreen.RuleEntry entry = rules.get(i);
                if (entry instanceof ResettableRuleEntry resettable && resettable.collapsible_game_rules$isModified()) {
                    count++;
                }
            }
            return count;
        }

        private void resetCategory() {
            List<AbstractGameRulesScreen.RuleEntry> rules = this.group.rules();
            for (int i = 0; i < rules.size(); i++) {
                AbstractGameRulesScreen.RuleEntry entry = rules.get(i);
                if (entry instanceof ResettableRuleEntry resettable) {
                    resettable.collapsible_game_rules$resetToDefault();
                }
            }
            net.minecraft.client.Minecraft.getInstance().getSoundManager()
                    .play(net.minecraft.client.resources.sounds.SimpleSoundInstance
                            .forUI(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
            GameRuleStateConfig.saveIfDirty();
            this.lastModifiedCount = -1;
            this.cachedBadge = null;
        }

        private net.minecraft.util.FormattedCharSequence truncateIfNeeded(net.minecraft.client.gui.Font font, Component component, int maxWidth) {
            if (font.width(component) <= maxWidth) {
                return component.getVisualOrderText();
            }
            int ellipsisWidth = font.width("...");
            int availableWidth = Math.max(0, maxWidth - ellipsisWidth);
            net.minecraft.network.chat.FormattedText truncated = font.substrByWidth(component, availableWidth);
            net.minecraft.network.chat.FormattedText withEllipsis = net.minecraft.network.chat.FormattedText.composite(truncated, net.minecraft.network.chat.FormattedText.of("..."));
            return net.minecraft.locale.Language.getInstance().getVisualOrder(withEllipsis);
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            net.minecraft.client.gui.Font font = net.minecraft.client.Minecraft.getInstance().font;

            int leftX = this.getX() + 8;
            int topY = this.getY() + 3;
            int rightX = this.getX() + this.getWidth() - 8;
            int bottomY = this.getY() + 21;

            int modifiedCount = this.countModified();
            boolean resetVisible = net.instantgratification.collapsiblegamerules.util.CategoryResetHelper.canReset(modifiedCount);
            boolean isSearching = !AbstractGameRulesScreenRuleListMixin.this.collapsible_game_rules$currentFilter.isEmpty();

            if (this.cachedBadge == null || modifiedCount != this.lastModifiedCount || isSearching != this.lastIsSearching) {
                this.lastModifiedCount = modifiedCount;
                this.lastIsSearching = isSearching;
                this.cachedBadge = isSearching ? this.group.countBadge() : CategoryGroup.createBadge(this.group.ruleCount(), modifiedCount);
            }
            Component badge = this.cachedBadge;
            int badgeWidth = font.width(badge);
            int badgeX = rightX - badgeWidth;

            int resetWidth = resetVisible ? 14 : 0;
            int resetX = badgeX - resetWidth - 4;
            int resetY = this.getY() + 4;
            int resetHeight = 14;

            // Enforce clearance before right-anchored badge / reset button
            int titleLeft = leftX + 2;
            int titleRight = (resetVisible ? resetX : badgeX) - 6;
            int maxTitleWidth = Math.max(10, titleRight - titleLeft);

            // Cache formatted visual text upon width or badge dimension shift (0B heap allocation during frame scrolling)
            if (this.getWidth() != this.lastWidth || badgeWidth != this.lastBadgeWidth || resetVisible != this.lastResetVisible || this.cachedExpandedTitle == null) {
                this.lastWidth = this.getWidth();
                this.lastBadgeWidth = badgeWidth;
                this.lastResetVisible = resetVisible;
                this.cachedExpandedTitle = truncateIfNeeded(font, this.group.expandedLeft(), maxTitleWidth);
                this.cachedCollapsedTitle = truncateIfNeeded(font, this.group.collapsedLeft(), maxTitleWidth);
                this.isTruncated = font.width(this.group.expandedLeft()) > maxTitleWidth
                                || font.width(this.group.collapsedLeft()) > maxTitleWidth;
            }

            // Subtle card background plate (elevated on hover)
            int bgColor = hovered ? 0x24FFFFFF : 0x10FFFFFF;
            graphics.fill(leftX - 4, topY, rightX + 4, bottomY, bgColor);

            // Left accent vertical bar indicating category presence
            int accentColor = this.expanded ? 0xFFFFAA00 : 0xFF55FF55; // Warm gold when expanded, crisp lime/green when collapsed
            graphics.fill(leftX - 4, topY, leftX - 2, bottomY, accentColor);

            Component fullTitle = this.expanded ? this.group.expandedLeft() : this.group.collapsedLeft();
            int fullTitleWidth = font.width(fullTitle);
            int overflow = fullTitleWidth - maxTitleWidth;
            int titleColor = hovered ? 0xFFFFFFAA : 0xFFFFFFFF;

            if (this.isTruncated && hovered && overflow > 0) {
                // Marquee scrolling animation: 1000ms start pause, ~30px/s scroll, 1000ms end pause, ping-pong
                long now = net.minecraft.util.Util.getMillis();
                long scrollDurationMs = (long) (overflow * 35L); // ~28.5 px per second
                long pauseMs = 1000L;
                long totalCycle = 2 * (pauseMs + scrollDurationMs);
                long cycleTime = now % totalCycle;

                int scrollOffset;
                if (cycleTime < pauseMs) {
                    scrollOffset = 0;
                } else if (cycleTime < pauseMs + scrollDurationMs) {
                    float progress = (float) (cycleTime - pauseMs) / (float) scrollDurationMs;
                    scrollOffset = Math.round(progress * overflow);
                } else if (cycleTime < 2 * pauseMs + scrollDurationMs) {
                    scrollOffset = overflow;
                } else {
                    float progress = (float) (cycleTime - (2 * pauseMs + scrollDurationMs)) / (float) scrollDurationMs;
                    scrollOffset = Math.round((1.0F - progress) * overflow);
                }

                graphics.enableScissor(titleLeft, topY, titleRight, bottomY);
                graphics.text(font, fullTitle.getVisualOrderText(), titleLeft - scrollOffset, this.getY() + 7, titleColor);
                graphics.disableScissor();
            } else {
                // Static truncated title
                net.minecraft.util.FormattedCharSequence titleSeq = this.expanded ? this.cachedExpandedTitle : this.cachedCollapsedTitle;
                graphics.text(font, titleSeq, titleLeft, this.getY() + 7, titleColor);
            }

            // Optional Category Reset button plate (compact 14x14 icon plate with hover tooltip)
            if (resetVisible) {
                boolean resetHovered = mouseX >= resetX && mouseX <= resetX + resetWidth && mouseY >= resetY && mouseY <= resetY + resetHeight;
                int resetBg = resetHovered ? 0x44FFAA00 : 0x22FFAA00;
                int resetBorder = resetHovered ? 0x88FFAA00 : 0x44FFAA00;

                graphics.fill(resetX, resetY, resetX + resetWidth, resetY + resetHeight, resetBg);
                graphics.fill(resetX, resetY, resetX + resetWidth, resetY + 1, resetBorder); // Top
                graphics.fill(resetX, resetY + resetHeight - 1, resetX + resetWidth, resetY + resetHeight, resetBorder); // Bottom
                graphics.fill(resetX, resetY, resetX + 1, resetY + resetHeight, resetBorder); // Left
                graphics.fill(resetX + resetWidth - 1, resetY, resetX + resetWidth, resetY + resetHeight, resetBorder); // Right

                graphics.centeredText(font, RESET_ICON, resetX + resetWidth / 2, resetY + 3, resetHovered ? 0xFFFFFFFF : 0xFFFFAA00);

                if (resetHovered) {
                    graphics.setTooltipForNextFrame(RESET_TOOLTIP, mouseX, mouseY);
                }
            }

            // Right-anchored rule count badge directly before scrollbar
            graphics.text(font, badge, badgeX, this.getY() + 7, 0xFFAAAAAA);

            // Subtle separating hairline at card footer
            graphics.fill(leftX - 4, bottomY + 2, rightX + 4, bottomY + 3, 0x22AAAAAA);

            // Tooltip showing full category title when truncated and hovered over the title area
            if (this.isTruncated && hovered && mouseX >= leftX && mouseX <= titleRight) {
                graphics.setTooltipForNextFrame(this.group.displayLabel(), mouseX, mouseY);
            }
        }

        @Override
        public boolean mouseClicked(net.minecraft.client.input.MouseButtonEvent event, boolean doubleClick) {
            if (event.button() == 0) { // Left-click check on [↺ Reset]
                double mx = event.x();
                double my = event.y();
                int modifiedCount = this.countModified();
                boolean resetVisible = net.instantgratification.collapsiblegamerules.util.CategoryResetHelper.canReset(modifiedCount);
                if (resetVisible) {
                    net.minecraft.client.gui.Font font = net.minecraft.client.Minecraft.getInstance().font;
                    int rightX = this.getX() + this.getWidth() - 8;
                    Component badge = this.cachedBadge != null ? this.cachedBadge : this.group.countBadge();
                    int badgeWidth = font.width(badge);
                    int badgeX = rightX - badgeWidth;
                    int resetWidth = 14;
                    int resetX = badgeX - resetWidth - 4;
                    int resetY = this.getY() + 4;
                    int resetHeight = 14;

                    if (mx >= resetX && mx <= resetX + resetWidth && my >= resetY && my <= resetY + resetHeight) {
                        this.resetCategory();
                        return true;
                    }
                }
            }

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
            output.add(NarratedElementType.USAGE, this.cachedBadge != null ? this.cachedBadge : this.group.countBadge());
            if (net.instantgratification.collapsiblegamerules.util.CategoryResetHelper.canReset(this.countModified())) {
                output.add(NarratedElementType.HINT, RESET_TOOLTIP);
            }
        }
    }
}
