package net.instantgratification.collapsiblegamerules.mixin;

import net.instantgratification.collapsiblegamerules.GameRuleStateConfig;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractGameRulesScreen.class)
public abstract class AbstractGameRulesScreenMixin {

    @Inject(method = "removed", at = @At("HEAD"))
    private void collapsiblegamerules$onRemoved(CallbackInfo ci) {
        GameRuleStateConfig.saveIfDirty();
    }
}
