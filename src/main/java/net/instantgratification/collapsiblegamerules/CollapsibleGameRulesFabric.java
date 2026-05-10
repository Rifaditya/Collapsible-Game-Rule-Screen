/*
 * Zenith Sovereign Engineering
 * Verified against: ModInitializer.java (26.*)
 */
package net.instantgratification.collapsiblegamerules;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Collapsible Game Rules [Zenith Protocol 2.1]");

        // Hard Dependency Enforcement (Constitution Section 5.7)
        if (!net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("dasik-library")) {
            throw new RuntimeException("Collapsible Game Rules requires DasikLibrary to function. Please install it.");
        }
    }
    
}
