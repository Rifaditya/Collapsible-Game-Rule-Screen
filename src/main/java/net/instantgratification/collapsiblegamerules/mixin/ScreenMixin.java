/*
 * Sovereign Engineering
 * Verified against: Screen.java (26.*)
 */
package net.instantgratification.collapsiblegamerules.mixin;

import net.instantgratification.collapsiblegamerules.GameRuleStateConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "removed", at = @At("HEAD"))
    private void collapsible_game_rules$onRemoved(CallbackInfo ci) {
        if ((Object) this instanceof AbstractGameRulesScreen) {
            GameRuleStateConfig.saveIfDirty();
        }
    }
}
