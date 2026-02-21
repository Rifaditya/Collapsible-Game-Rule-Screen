package net.instantgratification.collapsiblegamerules;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollapsibleGameRulesFabric implements ModInitializer {
    public static final String MOD_ID = "collapsible-game-rules";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Collapsible Game Rules Initialized");
    }
}
