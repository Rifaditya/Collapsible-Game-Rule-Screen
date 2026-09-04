// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import net.fabricmc.api.ClientModInitializer;

public class CollapsibleGameRulesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GameRuleStateConfig.load();
    }
}
