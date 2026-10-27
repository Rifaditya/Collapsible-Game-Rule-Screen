// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import com.google.gson.JsonArray;
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
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CollapsibleGameRulesSidednessContractTest: Pillar 5 Sidedness, Manifest & License Hygiene Contract Test Suite.
 *
 * Verifies:
 * - fabric.mod.json strictly specifies environment: client.
 * - Single-line GNU GPLv3 license header is present on line 1 across all source files.
 * - Zero references to obsolete version guard utilities across main and test sources.
 * - Zero unmapped Class.forName invocations in production source code.
 * - All mixin targets declared in collapsible-game-rules.mixins.json exist on disk.
 * - dasik-library dependency is strictly declared in fabric.mod.json depends block.
 */
@DisplayName("Pillar 5: Collapsible Game Rules Sidedness & Hygiene Contract Tests")
public class CollapsibleGameRulesSidednessContractTest {

    private static final String EXPECTED_LICENSE_HEADER = "// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3";

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
    @DisplayName("Verify fabric.mod.json declares client environment")
    void testEnvironmentDeclaration() throws IOException {
        Path root = findProjectRoot();
        Path modJsonPath = root.resolve("src/main/resources/fabric.mod.json");
        assertTrue(Files.exists(modJsonPath), "fabric.mod.json must exist at: " + modJsonPath);

        JsonObject rootJson = JsonParser.parseReader(Files.newBufferedReader(modJsonPath, StandardCharsets.UTF_8)).getAsJsonObject();
        assertTrue(rootJson.has("environment"), "fabric.mod.json must specify 'environment'");
        assertEquals("client", rootJson.get("environment").getAsString(), "Mod must strictly specify environment: client");
    }

    @Test
    @DisplayName("Verify 100% of Java source files declare the GPLv3 license header on line 1")
    void testLicenseHeaders() throws IOException {
        Path root = findProjectRoot();
        Path mainJava = root.resolve("src/main/java");
        assertTrue(Files.exists(mainJava), "src/main/java directory must exist: " + mainJava);

        try (Stream<Path> stream = Files.walk(mainJava)) {
            List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
            assertFalse(javaFiles.isEmpty(), "Java source files must exist in src/main/java");
            for (Path file : javaFiles) {
                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                assertFalse(lines.isEmpty(), "Java source file must not be empty: " + file);
                String line1 = lines.get(0).trim();
                assertEquals(EXPECTED_LICENSE_HEADER, line1,
                        "Missing or incorrect license header on line 1 in: " + file.toString().replace('\\', '/'));
            }
        }
    }

    @Test
    @DisplayName("Verify zero obsolete version guard references across all Java sources and tests")
    void testZeroModVersionGuard() throws IOException {
        Path root = findProjectRoot();
        Path[] roots = new Path[] {
                root.resolve("src/main/java"),
                root.resolve("src/test/java")
        };

        String forbidden = "ModVersion" + "Guard";
        for (Path srcRoot : roots) {
            if (!Files.exists(srcRoot)) {
                continue;
            }
            try (Stream<Path> stream = Files.walk(srcRoot)) {
                List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
                for (Path file : javaFiles) {
                    if (file.getFileName().toString().equals("CollapsibleGameRulesSidednessContractTest.java")) {
                        continue;
                    }
                    String content = Files.readString(file, StandardCharsets.UTF_8);
                    assertFalse(content.contains(forbidden),
                            "Forbidden reference to " + forbidden + " found in " + file.toString().replace('\\', '/'));
                }
            }
        }
    }

    @Test
    @DisplayName("Verify zero unmapped Class.forName invocations in main sources")
    void testZeroUnmappedClassForName() throws IOException {
        Path root = findProjectRoot();
        Path mainJava = root.resolve("src/main/java");
        assertTrue(Files.exists(mainJava), "src/main/java directory must exist: " + mainJava);

        try (Stream<Path> stream = Files.walk(mainJava)) {
            List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
            for (Path file : javaFiles) {
                String content = Files.readString(file, StandardCharsets.UTF_8);
                assertFalse(content.contains("Class.forName"),
                        "Forbidden Class.forName call found in " + file.toString().replace('\\', '/'));
            }
        }
    }

    @Test
    @DisplayName("Verify mixin descriptor targets in collapsible-game-rules.mixins.json exist on disk")
    void testMixinMappingIntegrity() throws IOException {
        Path root = findProjectRoot();
        Path mixinJsonPath = root.resolve("src/main/resources/collapsible-game-rules.mixins.json");
        assertTrue(Files.exists(mixinJsonPath), "collapsible-game-rules.mixins.json must exist at: " + mixinJsonPath);

        JsonObject mixinJson = JsonParser.parseReader(Files.newBufferedReader(mixinJsonPath, StandardCharsets.UTF_8)).getAsJsonObject();
        assertTrue(mixinJson.has("package"), "mixins.json must declare a package");
        String mixinPackage = mixinJson.get("package").getAsString();
        String packagePath = mixinPackage.replace('.', '/');

        Path mixinSourceDir = root.resolve("src/main/java").resolve(packagePath);
        assertTrue(Files.isDirectory(mixinSourceDir), "Mixin package directory must exist: " + mixinSourceDir);

        String[] mixinSections = new String[] { "mixins", "client", "server" };
        int totalMixinsChecked = 0;
        for (String section : mixinSections) {
            if (mixinJson.has(section)) {
                JsonArray mixins = mixinJson.getAsJsonArray(section);
                for (JsonElement el : mixins) {
                    String className = el.getAsString() + ".java";
                    Path classFile = mixinSourceDir.resolve(className);
                    assertTrue(Files.exists(classFile),
                            "Mixin class declared in " + section + " does not exist: " + classFile.toAbsolutePath());
                    totalMixinsChecked++;
                }
            }
        }
        assertTrue(totalMixinsChecked > 0, "At least one mixin class must be declared in mixins.json");
    }

    @Test
    @DisplayName("Verify dasik-library dependency is declared in fabric.mod.json")
    void testDasikLibraryDependency() throws IOException {
        Path root = findProjectRoot();
        Path modJsonPath = root.resolve("src/main/resources/fabric.mod.json");
        assertTrue(Files.exists(modJsonPath), "fabric.mod.json must exist at: " + modJsonPath);

        JsonObject rootJson = JsonParser.parseReader(Files.newBufferedReader(modJsonPath, StandardCharsets.UTF_8)).getAsJsonObject();
        assertTrue(rootJson.has("depends"), "fabric.mod.json must declare 'depends'");
        JsonObject depends = rootJson.getAsJsonObject("depends");
        assertTrue(depends.has("dasik-library"), "fabric.mod.json depends must declare 'dasik-library'");
        assertFalse(depends.get("dasik-library").getAsString().isEmpty(),
                "'dasik-library' version constraint must not be empty");
    }
}
