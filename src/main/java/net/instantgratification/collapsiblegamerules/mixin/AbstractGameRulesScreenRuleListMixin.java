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
                        .map(e -> {
                            Component lbl = ((CategoryRuleEntryAccessor) e).collapsible_game_rules$getLabel();
                            if (lbl.getContents() instanceof net.minecraft.network.chat.contents.TranslatableContents translatable) {
                                return translatable.getKey();
                            }
                            return lbl.getString();
                        })
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

        for (int i = 0; i < this.collapsible_game_rules$allEntries.size(); i++) {
            AbstractGameRulesScreen.RuleEntry entry = this.collapsible_game_rules$allEntries.get(i);
            if (entry instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
                // Calculate how many rule entries belong to this category
                int childCount = 0;
                for (int j = i + 1; j < this.collapsible_game_rules$allEntries.size(); j++) {
                    if (this.collapsible_game_rules$allEntries.get(j) instanceof AbstractGameRulesScreen.CategoryRuleEntry) {
                        break;
                    }
                    childCount++;
                }

                Component label = ((CategoryRuleEntryAccessor) entry).collapsible_game_rules$getLabel();
                
                String categoryKey = label.getString();
                String persistenceKey = categoryKey;
                if (label.getContents() instanceof net.minecraft.network.chat.contents.TranslatableContents translatable) {
                    persistenceKey = translatable.getKey();
                }

                // 3. DasikLibrary Metadata Integration Hook (lazy-loaded via helper)
                if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("dasik-library")) {
                    categoryKey = DasikMetadataHelper.getCategoryTranslation(categoryKey);
                }

                Component displayLabel = label;
                if (label.getContents() instanceof net.minecraft.network.chat.contents.TranslatableContents translatable) {
                    String key = translatable.getKey();
                    if (!net.minecraft.locale.Language.getInstance().has(key)) {
                        displayLabel = Component.literal(net.instantgratification.collapsiblegamerules.util.CategoryPrettifier.prettifyCategoryKey(key));
                    }
                }

                // Strip any existing arrow symbols/prefixes from displayLabel using regex
                String rawText = displayLabel.getString();
                rawText = rawText.replaceAll("^[\\s\\u25BA\\u25B6\\u25BC\\u25BD>►▼▶]+", "").trim();
                Component cleanLabel = Component.literal(rawText);

                // Smart Search: if there's an active filter and we are populating children,
                // vanilla already filters the list. If this category header is here, it means
                // a child match OR the category name matched. We should expand it to show results.
                boolean isSearching = !this.collapsible_game_rules$currentFilter.isEmpty();

                final String finalPersistenceKey = persistenceKey;
                boolean isExpanded = isSearching || GameRuleStateConfig.isExpanded(finalPersistenceKey);

                CollapsibleCategoryRuleEntry newEntry = new CollapsibleCategoryRuleEntry(cleanLabel,
                        isExpanded, childCount, () -> {
                            boolean newState = !GameRuleStateConfig.isExpanded(finalPersistenceKey);
                            GameRuleStateConfig.setExpanded(finalPersistenceKey, newState);
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

        // Force widescreen layout recalculation (560px wide centered)
        int screenWidth = net.minecraft.client.Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int listWidth = Math.min(560, Math.max(310, screenWidth - 40));
        int listX = (screenWidth - listWidth) / 2;
        this.updateSizeAndPosition(listWidth, this.getHeight(), listX, this.getY());
    }

    @Unique
    private class CollapsibleCategoryRuleEntry extends AbstractGameRulesScreen.RuleEntry implements NarratableEntry {
        private final Component label;
        private final boolean expanded;
        private final int childCount;
        private final Runnable toggleAction;

        public CollapsibleCategoryRuleEntry(Component label, boolean expanded, int childCount, Runnable toggleAction) {
            super(null);
            this.label = label;
            this.expanded = expanded;
            this.childCount = childCount;
            this.toggleAction = toggleAction;
        }

        private String getIconPrefix() {
            return this.expanded ? "📂 " : "📁 ";
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            int entryX = this.getX();
            int entryY = this.getY();
            int entryWidth = this.getWidth();

            net.minecraft.client.gui.Font font = net.minecraft.client.Minecraft.getInstance().font;

            // Right-Anchored Badge: Placed anchored before the scrollbar
            Component countBadge = Component.literal("[" + this.childCount + " rules]").withStyle(net.minecraft.ChatFormatting.GRAY);
            int badgeWidth = font.width(countBadge);
            int badgeX = entryX + entryWidth - badgeWidth - 14;

            String iconPrefix = getIconPrefix();
            Component fullTitle = Component.literal(iconPrefix).append(this.label);
            int maxAllowedWidth = badgeX - (entryX + 16);

            boolean isTwoLine = maxAllowedWidth > 20 && font.width(fullTitle) > maxAllowedWidth;
            int cardHeight = isTwoLine ? 30 : 22;
            int badgeY = entryY + (cardHeight - 8) / 2;

            // Glassmorphic dark card background
            int cardBg = hovered ? 0xFF222838 : 0xDD12141C;
            graphics.fill(entryX + 2, entryY + 2, entryX + entryWidth - 2, entryY + cardHeight, cardBg);
            
            // Dynamic Gold & Electric Cyan Accent Bar: Gold (0xFFFFAA00) when expanded, Cyan (0xFF00E5FF) when collapsed
            int accentColor = this.expanded ? 0xFFFFAA00 : 0xFF00E5FF;
            graphics.fill(entryX + 2, entryY + 2, entryX + 6, entryY + cardHeight, accentColor);

            if (isTwoLine) {
                // Split title across 2 lines so full text fits without '...' truncation
                List<net.minecraft.util.FormattedCharSequence> lines = font.split(fullTitle, maxAllowedWidth);
                int lineY = entryY + 4;
                for (int i = 0; i < Math.min(2, lines.size()); i++) {
                    graphics.text(font, lines.get(i), entryX + 12, lineY, hovered ? 0xFFFFFFAA : 0xFFFFFF55);
                    lineY += 12;
                }
            } else {
                // Single line title
                graphics.text(font, fullTitle, entryX + 12, entryY + 7, hovered ? 0xFFFFFFAA : 0xFFFFFF55);
            }

            // Right Badge Text
            graphics.text(font, countBadge, badgeX, badgeY, 0xFF80D8FF);
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
