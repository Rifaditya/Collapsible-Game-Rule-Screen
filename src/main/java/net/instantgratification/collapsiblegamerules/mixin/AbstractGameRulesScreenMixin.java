// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractGameRulesScreen.class)
public abstract class AbstractGameRulesScreenMixin extends Screen {

    protected AbstractGameRulesScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void collapsible_game_rules$onInit(CallbackInfo ci) {
        // Expand selection list to 560px widescreen layout centered on screen
        int listWidth = Math.min(560, Math.max(310, this.width - 40));
        int listX = (this.width - listWidth) / 2;

        for (GuiEventListener child : this.children()) {
            if (child instanceof AbstractGameRulesScreen.RuleList ruleList) {
                ruleList.updateSizeAndPosition(listWidth, this.height - 64, listX, 32);
            }
        }
    }
}
