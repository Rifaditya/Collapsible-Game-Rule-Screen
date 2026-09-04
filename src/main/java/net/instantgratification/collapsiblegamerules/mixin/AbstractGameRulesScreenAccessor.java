// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.mixin;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.worldselection.AbstractGameRulesScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractGameRulesScreen.class)
public interface AbstractGameRulesScreenAccessor {

    @Accessor("searchBox")
    EditBox collapsible_game_rules$getSearchBox();

    @Accessor("gameRules")
    net.minecraft.world.level.gamerules.GameRules collapsible_game_rules$getGameRules();

    @org.spongepowered.asm.mixin.gen.Invoker("filterGameRules")
    void collapsible_game_rules$invokeFilterGameRules(String filter);
}
