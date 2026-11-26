// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.collapsiblegamerules;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * - Mixin refmap declared in mixins.json exists on disk and has valid mappings/data structure.
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

    @Test
    @DisplayName("Verify mixin refmap declared in mixins.json exists on disk and has valid mappings/data structure")
    void testRefmapIntegrity() throws IOException {
        Path root = findProjectRoot();
        Path mixinJsonPath = root.resolve("src/main/resources/collapsible-game-rules.mixins.json");
        assertTrue(Files.exists(mixinJsonPath), "collapsible-game-rules.mixins.json must exist at: " + mixinJsonPath);

        JsonObject mixinJson = JsonParser.parseReader(Files.newBufferedReader(mixinJsonPath, StandardCharsets.UTF_8)).getAsJsonObject();
        if (mixinJson.has("refmap")) {
            String refmapFileName = mixinJson.get("refmap").getAsString();
            Path refmapPath = root.resolve("src/main/resources").resolve(refmapFileName);
            assertTrue(Files.exists(refmapPath), "Referenced refmap file must exist at: " + refmapPath);

            JsonObject refmapJson = JsonParser.parseReader(Files.newBufferedReader(refmapPath, StandardCharsets.UTF_8)).getAsJsonObject();
            assertTrue(refmapJson.has("mappings"), "Refmap must contain 'mappings' JsonObject");
            assertTrue(refmapJson.get("mappings").isJsonObject(), "'mappings' in refmap must be a JsonObject");
            assertTrue(refmapJson.has("data"), "Refmap must contain 'data' JsonObject");
            assertTrue(refmapJson.get("data").isJsonObject(), "'data' in refmap must be a JsonObject");
        }
    }

    @Test
    @DisplayName("Verify GlobalActionsRuleEntry bytecode uses ClientSupportScreenHelper.openKofi(Screen) ABI")
    void testGlobalActionsRuleEntryKofiAbiParity() throws IOException {
        Path root = findProjectRoot();
        Path classFile = root.resolve("build/classes/java/main/net/instantgratification/collapsiblegamerules/ui/GlobalActionsRuleEntry.class");
        assertTrue(Files.exists(classFile), "Compiled GlobalActionsRuleEntry.class must exist at: " + classFile);

        byte[] bytes = Files.readAllBytes(classFile);
        List<String> methodRefs = new ArrayList<>();
        List<String> utf8Entries = new ArrayList<>();

        try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes))) {
            int magic = in.readInt();
            assertEquals(0xCAFEBABE, magic, "Invalid Java class file magic header");
            in.readUnsignedShort(); // minor_version
            in.readUnsignedShort(); // major_version
            int cpCount = in.readUnsignedShort();

            Map<Integer, String> utf8Map = new HashMap<>();
            Map<Integer, Integer> classNameMap = new HashMap<>();
            Map<Integer, int[]> nameAndTypeMap = new HashMap<>();
            List<int[]> rawMethodRefs = new ArrayList<>();

            for (int i = 1; i < cpCount; i++) {
                int tag = in.readUnsignedByte();
                switch (tag) {
                    case 1 -> { // CONSTANT_Utf8
                        String s = in.readUTF();
                        utf8Map.put(i, s);
                        utf8Entries.add(s);
                    }
                    case 3, 4 -> in.readInt(); // Integer, Float
                    case 5, 6 -> { // Long, Double (occupy 2 constant pool slots)
                        in.readLong();
                        i++;
                    }
                    case 7 -> classNameMap.put(i, in.readUnsignedShort()); // Class
                    case 8, 16, 19, 20 -> in.readUnsignedShort(); // String, MethodType, Module, Package
                    case 9, 17, 18 -> { // Fieldref, Dynamic, InvokeDynamic
                        in.readUnsignedShort();
                        in.readUnsignedShort();
                    }
                    case 10, 11 -> { // Methodref, InterfaceMethodref
                        int classIdx = in.readUnsignedShort();
                        int natIdx = in.readUnsignedShort();
                        rawMethodRefs.add(new int[] { classIdx, natIdx });
                    }
                    case 12 -> { // NameAndType
                        int nameIdx = in.readUnsignedShort();
                        int descIdx = in.readUnsignedShort();
                        nameAndTypeMap.put(i, new int[] { nameIdx, descIdx });
                    }
                    case 15 -> { // MethodHandle
                        in.readUnsignedByte();
                        in.readUnsignedShort();
                    }
                    default -> fail("Unsupported constant pool tag " + tag + " at index " + i);
                }
            }

            for (int[] ref : rawMethodRefs) {
                Integer classNameIdx = classNameMap.get(ref[0]);
                int[] nat = nameAndTypeMap.get(ref[1]);
                if (classNameIdx != null && nat != null) {
                    String owner = utf8Map.get(classNameIdx);
                    String name = utf8Map.get(nat[0]);
                    String desc = utf8Map.get(nat[1]);
                    if (owner != null && name != null && desc != null) {
                        methodRefs.add(owner + ";" + name + desc);
                    }
                }
            }
        }

        String obsoleteOwner = "net/dasik/social/api/config/" + "DasikSupport" + "Helper";
        String obsoleteMethod = "open" + "Kofi";
        String screenDesc = "(Lnet/minecraft/client/gui/screens/Screen;)V";
        String obsoleteSig = obsoleteOwner + ";" + obsoleteMethod + screenDesc;
        String obsoleteShortSig = "DasikSupport" + "Helper;" + obsoleteMethod + screenDesc;

        long obsoleteCount = methodRefs.stream().filter(s -> s.equals(obsoleteSig)).count();
        long obsoleteUtf8Count = utf8Entries.stream().filter(s -> s.contains(obsoleteShortSig)).count();

        assertEquals(0L, obsoleteCount, "Forbidden obsolete method invocation found in constant pool: " + obsoleteSig);
        assertEquals(0L, obsoleteUtf8Count, "Forbidden obsolete descriptor string found in UTF-8 constant pool: " + obsoleteShortSig);

        String expectedSig = "net/dasik/social/api/client/gui/ClientSupportScreenHelper;openKofi" + screenDesc;
        long expectedCount = methodRefs.stream().filter(s -> s.equals(expectedSig)).count();
        assertTrue(expectedCount >= 1L, "Expected ClientSupportScreenHelper.openKofi(Screen) invocation not found. Found: " + methodRefs);
    }
}
