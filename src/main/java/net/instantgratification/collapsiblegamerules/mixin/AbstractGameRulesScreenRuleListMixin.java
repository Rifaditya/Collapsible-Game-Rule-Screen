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

                // Strip any existing arrow symbols/prefixes from displayLabel
                String rawText = displayLabel.getString();
                while (!rawText.isEmpty() && (rawText.startsWith("►") || rawText.startsWith("▼") || rawText.startsWith("▶") || rawText.startsWith(">") || rawText.startsWith(" "))) {
                    rawText = rawText.substring(1);
                }
                Component cleanLabel = Component.literal(rawText.trim());

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

        // Force widescreen layout recalculation (520px wide centered)
        int screenWidth = net.minecraft.client.Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int listWidth = Math.min(520, Math.max(310, screenWidth - 40));
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

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            int entryX = this.getX();
            int entryY = this.getY();
            int entryWidth = this.getWidth();

            // Dark glass pill background card for category header
            int cardBg = hovered ? 0xCC252525 : 0xAA151515;
            graphics.fill(entryX + 2, entryY + 2, entryX + entryWidth - 2, entryY + 22, cardBg);
            
            // Dynamic Gold & Cyan Accent Bar: Gold (0xFFFFAA00) when expanded, Cyan (0xFF55FFFF) when collapsed
            int accentColor = this.expanded ? 0xFFFFAA00 : 0xFF55FFFF;
            graphics.fill(entryX + 2, entryY + 2, entryX + 6, entryY + 22, accentColor);

            // Left-aligned category title with clean single directional arrow
            String arrow = this.expanded ? "▼ " : "▶ ";
            Component titleText = Component.literal(arrow).append(this.label);
            Component countBadge = Component.literal("[" + this.childCount + " rules]").withStyle(net.minecraft.ChatFormatting.GRAY);

            // Left Title Text
            graphics.text(net.minecraft.client.Minecraft.getInstance().font, titleText, entryX + 12, entryY + 7, hovered ? 0xFFFFFFAA : 0xFFFFFF55);

            // Right-Anchored Badge: Placed anchored before the scrollbar
            int badgeWidth = net.minecraft.client.Minecraft.getInstance().font.width(countBadge);
            int badgeX = entryX + entryWidth - badgeWidth - 12;
            graphics.text(net.minecraft.client.Minecraft.getInstance().font, countBadge, badgeX, entryY + 7, 0xFFAAAAAA);
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
