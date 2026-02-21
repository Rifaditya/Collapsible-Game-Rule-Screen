package net.instantgratification.collapsiblegamerules.mixin;

import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractGameRulesScreen.CategoryRuleEntry.class)
public interface CategoryRuleEntryAccessor {
    @Accessor("label")
    Component collapsiblegamerules$getLabel();
}
