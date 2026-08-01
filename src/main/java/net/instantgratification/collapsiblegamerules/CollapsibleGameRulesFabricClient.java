/*
 * Sovereign Engineering
 * Verified against: ClientModInitializer.java (26.*)
 */
package net.instantgratification.collapsiblegamerules;

import net.fabricmc.api.ClientModInitializer;

public class CollapsibleGameRulesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GameRuleStateConfig.load();
    }
}
