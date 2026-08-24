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

## 🧭 Master Version Selector Portal

Welcome to the official technical documentation for **Collapsible Game Rules**, a client-side Minecraft Fabric mod designed under the **Instant Gratification** philosophy to transform the cluttered, unorganized Game Rules screen into a clean, collapsible, and high-productivity interface.

Select your target Minecraft version below to enter the dedicated encyclopedic documentation tree:

| Minecraft Version Anchor | Release Era | Supported Range | Build Target | Direct Portal Entry |
| :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.2** | **Modern Sovereign Era** | `minecraft: >=26.2-` | `1.0.9+26.2` | [[👉 Enter MC 26.2 Wiki\|26.2-Home]] |

---

## 🌟 Core Feature Matrix

| Feature | Description | Reference Page |
| :--- | :--- | :--- |
| **🗂️ Collapsible Categories** | Intercepts the rule list to group entries under collapsible headers with directional indicators (`▼`/`▶`), hover highlights, and child count badges. | [[Collapsible Categories\|26.2-Collapsible-Categories]] |
| **🌎 Global Actions Header** | Pinned index 0 control offering instant one-click `[ Expand All ]` and `[ Collapse All ]` operations. | [[Global Actions & Bulk Toggles\|26.2-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Smart Search Expansion** | Integrates with the vanilla search bar to automatically expand categories containing matching game rules in real-time. | [[Smart Search Integration\|26.2-Smart-Search-Integration]] |
| **⌨️ Keyboard Navigation** | Full keyboard support via Space, Enter, Left Arrow (collapse), and Right Arrow (expand) with sound effects and screen narration. | [[Keyboard Navigation & Accessibility\|26.2-Keyboard-Navigation]] |
| **🧠 State Persistence** | Preserves category expansion states locally in `config/collapsible-game-rules-state.json` across game launches without frame lag. | [[State Persistence & JSON Config\|26.2-State-Persistence-and-Config]] |
| **✨ Intelligent Prettification** | Formats raw unlocalized modded category keys (e.g. `gamerule.category.modid.name` $\to$ `Modid Name`) dynamically. | [[Category Prettification & Naming\|26.2-Category-Prettification-and-Naming]] |
| **🎛️ Game Rule Presets & Sliders** | Built-in presets (Builder Mode, Fast Play, Hardcore Realism), interactive numeric sliders, and visual boolean toggle switches. | [[Game Rule Presets & Widgets\|26.2-Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary Integration** | Queries `DynamicGameRuleManager` generated translations for first-class category localization and metadata grouping. | [[DasikLibrary API Integration\|26.2-API-and-Library-Integration]] |

---

## 🚀 Quick Start Guide

### 1. Installation
1. Install **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Download **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Download **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Hard Requirement**).
4. Download `collapsible-game-rules-1.0.9+26.2.jar` and place it into your `.minecraft/mods` directory.
5. Launch Minecraft with **Java 25+**.

### 2. Basic In-Game Usage
- Navigate to **Singleplayer** $\to$ **Create New World** $\to$ **Game Rules** (or access Game Rules from the in-game world settings).
- Click any category header (e.g. `Spawning`, `Player`, `Mobs`) or press **Space**/**Enter** to expand or collapse.
- Click `[ Expand All ]` or `[ Collapse All ]` at the top of the list for bulk operations.
- Type any term into the search bar: categories with matching rules will automatically open!

---

## 📚 Quick Navigation Index

```
Wiki Root
├── 🧭 Version Compatibility Matrix ---------> [[Version-Compatibility]]
├── 🎮 Gameplay & UI Mechanics
│   ├── Collapsible Categories --------------> [[26.2-Collapsible-Categories]]
│   ├── Global Actions & Bulk Toggles -------> [[26.2-Global-Actions-and-Bulk-Toggles]]
│   ├── Smart Search Integration ------------> [[26.2-Smart-Search-Integration]]
│   └── Keyboard Navigation & Accessibility -> [[26.2-Keyboard-Navigation]]
├── ⚙️ Configuration & Presets
│   ├── State Persistence & JSON Config -----> [[26.2-State-Persistence-and-Config]]
│   ├── Category Prettification & Naming ----> [[26.2-Category-Prettification-and-Naming]]
│   └── Game Rule Presets & Widgets ---------> [[26.2-Game-Rule-Presets-and-Controls]]
├── 📋 Scope & Game Reference
│   ├── GameRules Reference -----------------> [[26.2-GameRules-Reference]]
│   ├── Brigadier Commands & Absence Scope --> [[26.2-Commands]]
│   └── Advancements & Absence Scope --------> [[26.2-Advancements]]
└── 💻 Technical Architecture & Development
    ├── HUD, Diagnostics & UI Rendering ----> [[26.2-HUD-and-Diagnostics]]
    ├── Developer Setup & Gradle Builds -----> [[26.2-Developer-Setup-and-Building]]
    ├── Architecture & Mixin Subsystem ------> [[26.2-Architecture-and-Mixins]]
    └── DasikLibrary API Integration --------> [[26.2-API-and-Library-Integration]]
```

---

## 📜 Credits & License

* **Author & Lead Engineer**: **Dasik (Rifaditya)**
* **License**: **GNU General Public License v3.0 (GPLv3)**
* **Repository**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth Project**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge Project**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
