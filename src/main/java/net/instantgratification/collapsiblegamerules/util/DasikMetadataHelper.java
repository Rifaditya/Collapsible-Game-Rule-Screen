// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;

/**
 * Lazy-loaded helper that isolates all DasikLibrary class references
 * into a separate class. The JVM will only attempt to load the
 * DynamicGameRuleManager class when this helper is first accessed,
 * preventing NoClassDefFoundError if the library is absent.
 */
public final class DasikMetadataHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger("collapsible-game-rules");

    private DasikMetadataHelper() {}

    /**
     * Retrieves the localized name for a category from DasikLibrary metadata.
     * Enforced Hard Dependency: This method makes direct calls to DasikLibrary.
     */
    public static String getCategoryTranslation(String categoryLabel) {
        Map<String, String> translations =
                net.dasik.social.api.gamerule.DynamicGameRuleManager.getGeneratedTranslations();

        return translations.getOrDefault(
                "gamerule.category." + categoryLabel.toLowerCase(Locale.ROOT),
                categoryLabel
        );
    }
}
