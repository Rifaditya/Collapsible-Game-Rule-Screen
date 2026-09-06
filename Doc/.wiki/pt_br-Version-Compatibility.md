# 🧭 Matriz de Compatibilidade de Versões

| Parâmetro | Especificação |
| :--- | :--- |
| **Versão Alvo Ativa** | **Minecraft 26.2** (`minecraft: >=26.2-`) |
| **Versão do Mod** | `1.0.9+26.2` |
| **Lançamentos Suportados** | Minecraft 26.2+ (Era Soberana Moderna) |
| **Requisito de Java** | **Java 25+** (`release = 25`, toolchain Java 25) |
| **Fabric Loader** | `>=0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` (`*`) |
| **Dependência Principal** | **DasikLibrary** `>=1.7.0` (Ativa: `1.7.4`) |
| **Ferramentas de Build** | Loom `1.15.2` / Gradle `9.3+` (`--no-daemon`) |
| **Ambiente** | **Apenas Cliente** (`"environment": "client"`) |
| **Licença** | **GPL-3.0-or-later** |

---

> 📌 **Aviso sobre o Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das compilações públicas no CurseForge e Modrinth.

---

## 📊 Matriz de Ciclo de Vida Multi-Era

O Collapsible Game Rules foi arquitetado para a era moderna (`MC 26.2+`), aproveitando o novo pipeline gráfico `GuiGraphicsExtractor`, a API Stream do Java 25 (`.toList()`) e ambientes de execução sem ofuscação.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           MULTI-ERA RUNTIME LIFECYCLE MATRIX                            │
├───────────────────┬─────────────┬──────────────┬───────────────┬────────────────────────┤
│ Minecraft Anchor  │ Java Level  │ Loader Bound │ DasikLibrary  │ Lifecycle Status       │
├───────────────────┼─────────────┼──────────────┼───────────────┼────────────────────────┤
│ **MC 26.2+**      │ **Java 25** │ `>=0.19.1`   │ `>=1.7.0`     │ 🟢 Active Target (Loom)│
│ MC 26.1.2         │ Java 25     │ `>=0.18.4`   │ `>=1.7.0`     │ 🟡 Forward Compatible  │
│ MC 1.21.x         │ Java 21     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
│ MC 1.20.1         │ Java 17     │ N/A          │ N/A           │ ⚪ Unsupported (Modern)│
└───────────────────┴─────────────┴──────────────┴───────────────┴────────────────────────┘
```

---

## 🔒 Proteção de ClassLoader via ModVersionGuard

Para resguardar os mundos salvos contra possíveis corrupções e evitar loops de travamento em versões incompatíveis, o mod inclui o guardião `net.instantgratification.collapsiblegamerules.util.ModVersionGuard`.

### Mecanismo de Verificação
Durante `ModInitializer.onInitialize()`:
```java
ModVersionGuard.checkClass("Collapsible Game Rules", "net.minecraft.world.level.gamerules.GameRules");
```

Se iniciado em versão incompatível sem a classe requerida ou sem os bindings do Knot ClassLoader, o carregamento é interrompido com o seguinte alerta estruturado:

```
=====================================================================
 [PRE-RELEASE / VERSION GUARD WARNING] Collapsible Game Rules
---------------------------------------------------------------------
 CRITICAL: Incompatible Minecraft Game Runtime or Missing Class!
 Required Class : net.minecraft.world.level.gamerules.GameRules
 Status         : UNRESOLVED AT RUNTIME

 Safety Protection:
 Execution halted to prevent unreleased/incompatible build deployment
 or broken world state save corruption.

 Troubleshooting Steps:
 1. Verify target Minecraft version (26.2+ release drop).
 2. Ensure all required dependencies (Fabric API, DasikLibrary) are loaded.
 3. Build/Download a verified matching release JAR from Modrinth/CurseForge.
=====================================================================
```

---

## 📦 Declaração de Dependências

No arquivo `src/main/resources/fabric.mod.json`:

```json
{
  "schemaVersion": 1,
  "id": "collapsible-game-rules",
  "version": "${version}",
  "name": "Collapsible Game Rules",
  "description": "Makes the GameRules UI screens collapsible by category.",
  "authors": [
    "Dasik (Rifaditya)"
  ],
  "license": "GPL-3.0-or-later",
  "environment": "client",
  "entrypoints": {
    "main": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabric"
    ],
    "client": [
      "net.instantgratification.collapsiblegamerules.CollapsibleGameRulesFabricClient"
    ]
  },
  "mixins": [
    "collapsible-game-rules.mixins.json"
  ],
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": ">=26.2-",
    "java": ">=25",
    "fabric-api": "*",
    "dasik-library": ">=1.7.0"
  }
}
```

---

## 🗄️ Arquivos Históricos de Lançamentos

As compilações oficiais ficam preservadas na pasta `Archive Jar of all versions/`:

* `collapsible-game-rules-1.0.9+26.2.jar` (Versão Ativa)
* `collapsible-game-rules-1.0.8+26.2.jar` (Compilação Anterior)
* `collapsible-game-rules-1.0.7+26.2.jar` (Revisão de Recursos)

---

## 🔗 Documentação Relacionada

* [[🏠 Visão Geral & Página Inicial|pt_br-Home]]
* [[🛠️ Configuração de Desenvolvedor & Compilação Gradle|pt_br-Developer-Setup-and-Building]]
* [[📚 Integração com a API do DasikLibrary|pt_br-API-and-Library-Integration]]
