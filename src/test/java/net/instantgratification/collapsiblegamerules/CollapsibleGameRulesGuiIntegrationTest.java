// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CollapsibleGameRulesGuiIntegrationTest: Pillar 3 Optional GUI, Translation AST & Integration Validation.
 *
 * Verifies:
 * - ModMenu creator links (Ko-fi donation and creator profiles) are strictly configured in fabric.mod.json.
 * - Language translation keys (en_us.json) exist, are non-empty, and contain zero placeholder artifacts.
 */
@DisplayName("Pillar 3: Collapsible Game Rules GUI & Translation AST Integration Tests")
public class CollapsibleGameRulesGuiIntegrationTest {

    private static Path findProjectRoot() {
        Path dir = Paths.get(System.getProperty("user.dir"));
        if (Files.exists(dir.resolve("src/main/resources/fabric.mod.json"))) {
            return dir;
        }
        Path candidate = dir.resolve("Collapsible Game Rule Screen/collapsible-game-rules");
        if (Files.exists(candidate.resolve("src/main/resources/fabric.mod.json"))) {
            return candidate;
        }
        Path current = dir;
        while (current != null) {
            if (Files.exists(current.resolve("src/main/resources/fabric.mod.json"))) {
                return current;
            }
            if (Files.exists(current.resolve("Collapsible Game Rule Screen/collapsible-game-rules/src/main/resources/fabric.mod.json"))) {
                return current.resolve("Collapsible Game Rule Screen/collapsible-game-rules");
            }
            current = current.getParent();
        }
        return dir;
    }

    @Test
    @DisplayName("Verify ModMenu creator links in fabric.mod.json point to Ko-fi")
    void testModMenuCreatorLinks() throws IOException {
        Path root = findProjectRoot();
        Path modJsonPath = root.resolve("src/main/resources/fabric.mod.json");
        assertTrue(Files.exists(modJsonPath), "fabric.mod.json must exist at " + modJsonPath);

        String content = Files.readString(modJsonPath, StandardCharsets.UTF_8);
        JsonObject rootJson = JsonParser.parseString(content).getAsJsonObject();
        assertNotNull(rootJson, "fabric.mod.json root must not be null");

        assertTrue(rootJson.has("custom"), "fabric.mod.json must contain 'custom' block");
        JsonObject custom = rootJson.getAsJsonObject("custom");
        assertNotNull(custom, "custom block must be a JsonObject");

        assertTrue(custom.has("modmenu"), "custom block must contain 'modmenu' entry");
        JsonObject modmenu = custom.getAsJsonObject("modmenu");
        assertNotNull(modmenu, "modmenu block must be a JsonObject");

        assertTrue(modmenu.has("links"), "modmenu block must contain 'links' entry");
        JsonObject links = modmenu.getAsJsonObject("links");
        assertNotNull(links, "links block must be a JsonObject");

        String expectedUrl = "https://ko-fi.com/dasikigaijin";

        assertTrue(links.has("modmenu.donate"), "links must contain 'modmenu.donate'");
        assertEquals(expectedUrl, links.get("modmenu.donate").getAsString(),
                "modmenu.donate link must match expected Ko-fi URL");

        assertTrue(links.has("modmenu.kofi"), "links must contain 'modmenu.kofi'");
        assertEquals(expectedUrl, links.get("modmenu.kofi").getAsString(),
                "modmenu.kofi link must match expected Ko-fi URL");
    }

    @Test
    @DisplayName("Verify en_us.json translation keys exist, are non-empty, and contain zero placeholder text")
    void testLangTranslationKeyCompleteness() throws IOException {
        Path root = findProjectRoot();
        Path langPath = root.resolve("src/main/resources/assets/collapsiblegamerules/lang/en_us.json");
        assertTrue(Files.exists(langPath), "en_us.json must exist at " + langPath);

        String content = Files.readString(langPath, StandardCharsets.UTF_8);
        JsonObject langJson = JsonParser.parseString(content).getAsJsonObject();
        assertNotNull(langJson, "en_us.json root must be a valid JSON object");

        Set<String> requiredKeys = Set.of(
                "gui.collapsible-game-rules.expand_all",
                "gui.collapsible-game-rules.collapse_all",
                "gui.collapsible-game-rules.reset_category"
        );

        for (String key : requiredKeys) {
            assertTrue(langJson.has(key), "Missing required translation key: " + key);
            JsonElement element = langJson.get(key);
            assertNotNull(element, "Key element must not be null: " + key);
            assertTrue(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString(),
                    "Key value must be a string: " + key);

            String value = element.getAsString();
            assertFalse(value.isBlank(), "Translation key value must not be empty or blank: " + key);

            String lower = value.toLowerCase(Locale.ROOT);
            assertFalse(lower.contains("todo"), "Translation key must not contain 'TODO': " + key + " = " + value);
            assertFalse(lower.contains("placeholder"), "Translation key must not contain 'placeholder': " + key + " = " + value);
            assertFalse(lower.contains("missing"), "Translation key must not contain 'missing': " + key + " = " + value);
            assertFalse(lower.contains("translate"), "Translation key must not contain 'translate': " + key + " = " + value);
        }

        // Validate all keys in en_us.json for general integrity
        for (Map.Entry<String, JsonElement> entry : langJson.entrySet()) {
            String key = entry.getKey();
            assertFalse(key.isBlank(), "Translation key must not be blank");
            assertTrue(entry.getValue().isJsonPrimitive() && entry.getValue().getAsJsonPrimitive().isString(),
                    "Value for " + key + " must be a string");
            String val = entry.getValue().getAsString();
            assertFalse(val.isBlank(), "Value for " + key + " must not be blank");
        }
    }
}
