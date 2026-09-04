// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class CategoryPrettifier {

    private CategoryPrettifier() {}

    /**
     * Prettifies a raw translation key for a game rule category.
     * E.g., "gamerule.category.better-bats.better_bats" -> "Better Bats"
     */
    public static String prettifyCategoryKey(String key) {
        if (key == null) {
            return "";
        }
        String name = key;
        if (name.startsWith("gamerule.category.")) {
            name = name.substring("gamerule.category.".length());
        }

        // Split namespace and path if dot is present
        int dotIndex = name.indexOf('.');
        if (dotIndex != -1) {
            String ns = name.substring(0, dotIndex);
            String path = name.substring(dotIndex + 1);
            
            // If the namespace is "minecraft", just drop it
            if (ns.equals("minecraft")) {
                name = path;
            } else {
                // Normalize for comparison
                String normNs = ns.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
                String normPath = path.replace("-", "").replace("_", "").toLowerCase(Locale.ROOT);
                if (normPath.contains(normNs) || normNs.contains(normPath)) {
                    name = path; // Use the path part since it's more specific or includes namespace
                } else {
                    name = ns + " " + path;
                }
            }
        }

        // Split by whitespace, underscore or dash
        String[] parts = name.split("[\\s_-]+");
        List<String> words = new ArrayList<>();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            String capitalized = part.substring(0, 1).toUpperCase(Locale.ROOT) + part.substring(1);
            words.add(capitalized);
        }
        return String.join(" ", words);
    }
}
