/*
 * Zenith Sovereign Engineering
 * Verified against: DynamicGameRuleManager.java (26.*)
 */
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
     * Attempts to resolve a category label through DasikLibrary's
     * DynamicGameRuleManager metadata. If the library is missing or
     * the lookup fails, the original label is returned unchanged.
     */
    public static String getCategoryTranslation(String categoryLabel) {
        try {
            Map<String, String> translations =
                    net.dasik.social.api.gamerule.DynamicGameRuleManager.getGeneratedTranslations();
            return translations.getOrDefault(
                    "gamerule.category." + categoryLabel.toLowerCase(Locale.ROOT),
                    categoryLabel
            );
        } catch (Throwable t) {
            LOGGER.warn("DasikLibrary metadata lookup failed for '{}': {}", categoryLabel, t.getMessage());
            return categoryLabel;
        }
    }
}
