# ⚒️ Collapsible Game Rules Wiki

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

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 🧭 Welcome to Collapsible Game Rules

**Collapsible Game Rules** transforms Minecraft's standard Game Rules screen into an organized, collapsible interface with smart search expansion, keyboard navigation, persistent memory, and built-in presets.

---

## 🌟 Core Features & Quick Index

| Feature | Description | Reference Guide |
| :--- | :--- | :--- |
| **🗂️ Collapsible Categories** | Replaces static category headers with interactive toggle widgets (`▼`/`▶`) displaying child rule count badges. | [[Collapsible Categories|Collapsible-Categories]] |
| **🌎 Global Actions Header** | Top-pinned `[ Expand All ]` and `[ Collapse All ]` bulk controls. | [[Global Actions & Bulk Toggles|Global-Actions-and-Bulk-Toggles]] |
| **🔍 Smart Search Expansion** | Automatically expands matching categories as you type in the search bar. | [[Smart Search Integration|Smart-Search-Integration]] |
| **⌨️ Keyboard Navigation** | Full keyboard support (Space, Enter, Left/Right arrows) and accessibility screen narration. | [[Keyboard Navigation & Accessibility|Keyboard-Navigation]] |
| **🧠 State Persistence** | Preserves category expansion states locally in `config/collapsible-game-rules-state.json`. | [[State Persistence & JSON Config|State-Persistence-and-Config]] |
| **✨ Intelligent Prettification** | Formats raw modded category keys dynamically into clean Title Case strings. | [[Category Prettification & Naming|Category-Prettification-and-Naming]] |
| **🎛️ Game Rule Presets & Sliders** | Built-in presets (Builder, Fast Play, Hardcore), interactive numeric sliders, and toggle switches. | [[Game Rule Presets & Widgets|Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary Integration** | Queries `DynamicGameRuleManager` generated translations for first-class category localization. | [[DasikLibrary API Integration|API-and-Library-Integration]] |

---

## 🚀 Quick Start & Installation

1. Install **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Download **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Download **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Hard Requirement**).
4. Download `collapsible-game-rules-1.0.9+26.2.jar` into your `.minecraft/mods` directory.
5. Launch Minecraft with **Java 25+**.

---

## 📚 Complete Documentation Index

```
Subproject Wiki Root
├── 🧭 Version Compatibility Matrix ---------> [[Version-Compatibility]]
├── 🎮 Core UI Mechanics
│   ├── Collapsible Categories --------------> [[Collapsible-Categories]]
│   ├── Global Actions & Bulk Toggles -------> [[Global-Actions-and-Bulk-Toggles]]
│   ├── Smart Search Integration ------------> [[Smart-Search-Integration]]
│   └── Keyboard Navigation & Accessibility -> [[Keyboard-Navigation]]
├── ⚙️ Configuration & Presets
│   ├── State Persistence & JSON Config -----> [[State-Persistence-and-Config]]
│   ├── Category Prettification & Naming ----> [[Category-Prettification-and-Naming]]
│   └── Game Rule Presets & Widgets ---------> [[Game-Rule-Presets-and-Controls]]
├── 📋 Scope & Game Reference
│   ├── GameRules Reference -----------------> [[GameRules-Reference]]
│   ├── Brigadier Commands & Absence Scope --> [[Commands]]
│   └── Advancements & Absence Scope --------> [[Advancements]]
└── 💻 Technical Architecture & Development
    ├── HUD, Diagnostics & UI Rendering ----> [[HUD-and-Diagnostics]]
    ├── Developer Setup & Gradle Builds -----> [[Developer-Setup-and-Building]]
    ├── Architecture & Mixin Subsystem ------> [[Architecture-and-Mixins]]
    └── DasikLibrary API Integration --------> [[API-and-Library-Integration]]
```

---

## 📜 Credits & License

* **Author & Lead Engineer**: **Dasik (Rifaditya)**
* **License**: **GNU General Public License v3.0 (GPLv3)**
* **Repository**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth Project**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge Project**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
