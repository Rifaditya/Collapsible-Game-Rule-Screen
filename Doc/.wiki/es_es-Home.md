# ⚒️ Collapsible Game Rules Wiki (Reglas de Juego Desplegables)

<div align="center">

<img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="800">

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge&logo=minecraft" alt="Minecraft 26.2+">
  <img src="https://img.shields.io/badge/Fabric-0.145.4+-blue?style=for-the-badge&logo=fabric" alt="Fabric">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" alt="Java 25">
  <img src="https://img.shields.io/badge/DasikLibrary-1.7.4-purple?style=for-the-badge" alt="DasikLibrary 1.7.4">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
</p>

</div>

---

> 📌 **Descargo de Responsabilidad del Código del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Bienvenido a Collapsible Game Rules

**Collapsible Game Rules** transforma la pantalla estándar de reglas de juego de Minecraft en una interfaz organizada y desplegable con expansión inteligente por búsqueda, navegación por teclado, memoria persistente local y ajustes preestablecidos integrados.

---

## 🌟 Características Principales e Índice Rápido

| Característica | Descripción | Guía de Referencia |
| :--- | :--- | :--- |
| **🗂️ Categorías Desplegables** | Reemplaza los encabezados estáticos con widgets interactivos (`▼`/`▶`) que muestran insignias con el recuento de reglas. | [[🗂️ Categorías Desplegables\|es_es-Collapsible-Categories]] |
| **🌎 Barra de Acciones Globales** | Botones superiores fijos de «Expandir Todo» y «Contraer Todo» para control masivo con un solo clic. | [[🌎 Acciones Globales y Alternancia Masiva\|es_es-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Búsqueda Inteligente** | Expande automáticamente las categorías coincidentes mientras escribes en la barra de búsqueda. | [[🔍 Integración de Búsqueda Inteligente\|es_es-Smart-Search-Integration]] |
| **⌨️ Navegación por Teclado** | Soporte completo de teclado (Espacio, Enter, flechas izquierda/derecha) y narración accesible de pantalla. | [[⌨️ Navegación por Teclado y Accesibilidad\|es_es-Keyboard-Navigation]] |
| **🧠 Persistencia de Estado** | Recuerda las categorías expandidas y contraídas localmente en `config/collapsible-game-rules-state.json`. | [[🧠 Persistencia de Estado y Configuración JSON\|es_es-State-Persistence-and-Config]] |
| **✨ Embellecimiento Inteligente** | Convierte claves crudas de categorías de mods en títulos limpios en formato Title Case. | [[✨ Embellecimiento y Nombres de Categorías\|es_es-Category-Prettification-and-Naming]] |
| **🎛️ Presets y Controles Deslizantes** | Perfiles preconfigurados (Constructor, Juego Rápido, Extremo) y controles deslizantes interactivos. | [[🎛️ Ajustes Preestablecidos y Controles de Game Rules\|es_es-Game-Rule-Presets-and-Controls]] |
| **🧩 Integración con DasikLibrary** | Consulta traducciones generadas en `DynamicGameRuleManager` para soporte de localización nativo. | [[📚 Integración con la API de DasikLibrary\|es_es-API-and-Library-Integration]] |

---

## 🚀 Inicio Rápido e Instalación

1. Instala **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Descarga **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Descarga **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Requisito Obligatorio**).
4. Coloca el archivo `collapsible-game-rules-1.0.9+26.2.jar` en tu carpeta `.minecraft/mods`.
5. Ejecuta Minecraft con **Java 25+**.

---

## 📚 Índice Completo de Documentación

```
Raíz de la Wiki
├── 🧭 Matriz de Compatibilidad de Versiones ----> [[🧭 Matriz de Compatibilidad de Versiones|es_es-Version-Compatibility]]
├── 🎮 Mecánicas Principales de la UI
│   ├── Categorías Desplegables ----------------> [[🗂️ Categorías Desplegables|es_es-Collapsible-Categories]]
│   ├── Acciones Globales y Alternancia --------> [[🌎 Acciones Globales y Alternancia Masiva|es_es-Global-Actions-and-Bulk-Toggles]]
│   ├── Búsqueda Inteligente -------------------> [[🔍 Integración de Búsqueda Inteligente|es_es-Smart-Search-Integration]]
│   └── Navegación por Teclado y Accesibilidad -> [[⌨️ Navegación por Teclado y Accesibilidad|es_es-Keyboard-Navigation]]
├── ⚙️ Configuración y Presets
│   ├── Persistencia de Estado y JSON ----------> [[🧠 Persistencia de Estado y Configuración JSON|es_es-State-Persistence-and-Config]]
│   ├── Embellecimiento y Nombres --------------> [[✨ Embellecimiento y Nombres de Categorías|es_es-Category-Prettification-and-Naming]]
│   └── Presets y Controles Deslizantes --------> [[🎛️ Ajustes Preestablecidos y Controles de Game Rules|es_es-Game-Rule-Presets-and-Controls]]
├── 📋 Alcance y Referencia
│   ├── Tabla de Referencia de GameRules -------> [[📜 Tabla de Referencia de GameRules|es_es-GameRules-Reference]]
│   ├── Comandos Brigadier y Alcance -----------> [[💬 Comandos Brigadier y Alcance|es_es-Commands]]
│   └── Progresos y Alcance --------------------> [[🏆 Progresos y Alcance|es_es-Advancements]]
└── 💻 Arquitectura Técnica y Desarrollo
    ├── HUD, Diagnósticos y Renderizado -------> [[🖥️ HUD, Diagnósticos y Renderizado de UI|es_es-HUD-and-Diagnostics]]
    ├── Configuración de Desarrollador y Gradle -> [[🛠️ Configuración de Desarrollador y Compilación Gradle|es_es-Developer-Setup-and-Building]]
    ├── Arquitectura y Subsistema Mixin --------> [[🧩 Arquitectura y Subsistema Mixin|es_es-Architecture-and-Mixins]]
    └── Integración con la API de DasikLibrary -> [[📚 Integración con la API de DasikLibrary|es_es-API-and-Library-Integration]]
```

---

## 📜 Créditos y Licencia

* **Autor e Ingeniero Principal**: **Dasik (Rifaditya)**
* **Licencia**: **GNU General Public License v3.0 (GPLv3)**
* **Repositorio en GitHub**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Proyecto en Modrinth**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **Proyecto en CurseForge**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
