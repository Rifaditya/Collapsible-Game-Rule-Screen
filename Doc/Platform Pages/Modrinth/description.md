<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Banner" width="85%">
</p>

<p align="center">
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-purple?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Environment-100%25_Client--Side-brightgreen?style=for-the-badge" alt="100% Client-Side">
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-red?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# ⚒️ Collapsible Game Rules

> **"Tame the chaos of world creation—every rule, cleanly organized at your fingertips."**

---

## 📖 Introduction

Minecraft's default **Edit Game Rules** screen is an intimidating, endless wall of disorganized settings. The moment you install gameplay mods or create a complex world, dozens upon dozens of custom rules get tossed into a flat, sluggish list. Finding a single specific toggle—like keep inventory, mob griefing, or crop ticks—turns into a frustrating marathon of squinting and scrolling.

**Collapsible Game Rules** completely modernizes the Game Rules screen. It transforms the flat list into an elegant, hierarchical interface with folding category drawers, instant global fold/unfold toggles, real-time search auto-expansion, dedicated per-category defaults resetting, and persistent layout memory across game sessions. Built from the ground up as a **100% client-side** quality-of-life upgrade, it runs with zero server footprint and zero allocation churn on the render path.

> [!IMPORTANT]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.
> 
> **Dependency Callout:**
> * **Minecraft 26.2+ (Modern Era):** Requires both **Fabric API** AND **Dasik Library** (`v1.7.0+`).
> * **Zero Server Requirement:** This is a 100% client-side visual overhaul. You can install it on your client and join any vanilla, Fabric, Forge, Paper, or Purpur server without needing the mod installed on the server!

---

## ✨ Features

### 📂 Hierarchical Collapsible Categories
- **Clean Drawer Layout:** Automatically sorts all vanilla and modded game rules into tidy, collapsible category drawers (Player, Mobs, Spawning, World Updates, and modded namespaces).
- **Linear Category Tree Builder:** Features a high-performance single-pass parser (`CategoryTreeBuilder`) and immutable `CategoryGroup` data records that cache titles and rule counts in $O(1)$ time, eliminating nested list scanning overhead.

### 🖱️ Interactive Headers & Expand/Collapse Carats
- **Visual State Indicators:** Clearly indicates folder states with responsive toggle carats (`▼` for expanded, `▶` for collapsed) alongside exact rule counters.
- **Effortless Toggle:** Click any category header to instantly expand or collapse that section. Fold away rules you rarely touch to keep your screen distraction-free.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_11.06.33.png" alt="Collapsible Game Rules Screen In-Game Preview" width="85%"><br><br>
  <img src="https://raw.githubusercontent.com/Rifaditya/Collapsible-Game-Rule-Screen/main/Images/2026-08-04_10.59.57.png" alt="Expanded and Collapsed Categories Overview" width="85%">
</p>

### 🌐 Global Actions: Expand All & Collapse All
- **One-Click Mastery:** A dedicated sticky top control bar provides `[ Expand All ]` and `[ Collapse All ]` buttons.
- **Rapid Navigation:** Expand every single category when you want to audit your entire configuration, or collapse everything with a single tap to view a high-level table of contents.

### 🔍 Smart Vanilla Search Integration & Auto-Expansion
- **Seamless Vanilla Compatibility:** Type keywords directly into Minecraft's native search bar.
- **Dynamic Unfolding:** As you type, any category containing matching rules automatically unfolds, while non-matching categories collapse. Clear the search bar to immediately restore your previous layout!

### ↺ Per-Category Defaults Reset Plate
- **Isolated Reset:** Each category header features a compact `↺` icon plate with dedicated hover tooltips (`gui.collapsible-game-rules.reset_category`).
- **Precision Resetting:** Reset an entire category's rules back to vanilla or mod defaults in one swift action without accidentally wiping custom tweaks made in other categories.

### 🧠 Persistent Layout Memory Across Sessions
- **Session Continuity:** Never repeat your collapsing preferences. Layout states are tracked via translation keys and saved locally to `config/collapsible-game-rules-state.json`.
- **Language Resilient:** Because state is saved by translation key rather than localized text, switching language packs will never corrupt or reset your collapsed folders.
- **Zero Disk Thrashing:** State is committed to disk only when exiting the screen (`isDirty` flag), ensuring zero stutter while clicking through menus.

### 🧩 Universal Modded GameRule Compatibility & Prettification
- **Automatic Discovery:** Directly hooks Minecraft's native Game Rules map. Custom rules registered by other mods or datapacks are automatically grouped into clean folders out of the box.
- **Namespace Prettification:** Intelligent fallback formatting cleans up unlocalized mod strings (e.g. converting `gamerule.category.better-bats.better_bats` into a beautiful, readable `Better Bats` header).
- **Dasik Library Integration:** Deep first-class metadata synchronization for all dynamic rules generated through `DasikLibrary`'s `DynamicGameRuleManager`.

### 🛡️ 100% Client-Side Architecture & Zero Server Footprint
- Runs entirely on your local client machine.
- Safe for singleplayer worlds, LAN games, and all multiplayer servers (vanilla, Paper, Purpur, Fabric).

---

## 📊 Quick Reference & Mechanics Matrix

| Feature Dimension | Vanilla Game Rules Screen | Collapsible Game Rules (`v1.0.10+26.2`) |
| :--- | :---: | :---: |
| **Visual Structure** | Giant, flat scrolling list | **Collapsible hierarchical category drawers** |
| **Navigation Speed** | Endless manual scrolling | **One-click folding & unfolding** |
| **Global Controls** | None | **`[ Expand All ]` & `[ Collapse All ]` Header** |
| **Rule Resetting** | Global world reset only | **Dedicated per-category `↺` Reset Plate** |
| **Search Interaction** | Filters flat list; hard to track | **Auto-expands matching category drawers** |
| **Layout Persistence** | Resets on every screen close | **Persistent JSON memory (`collapsible-game-rules-state.json`)** |
| **Modded Rule Support** | Flat, unorganized mod dumping | **Auto-grouped with Namespace Prettification** |
| **Server Footprint** | N/A | **100% Client-Side (Zero server install required)** |
| **Hot-Path Rendering** | Repeated nested scanning | **$O(N)$ Linear Tree Builder & 0B/frame caching** |

---

## 🚀 In-Game Controls & Quick Start

Collapsible Game Rules is ready to use immediately upon installation. Operate the interface with intuitive mouse and keyboard controls:

| Input Action | Target Element | Functionality |
| :--- | :--- | :--- |
| **Left-Click** | Category Header Bar | Expands or collapses the selected category drawer. |
| **Left-Click** | `[ Expand All ]` Button | Expands all category drawers simultaneously. |
| **Left-Click** | `[ Collapse All ]` Button | Collapses all categories and resets scroll to the top. |
| **Left-Click** | `↺` Reset Plate | Resets all game rules within that category to default values. |
| **Right Arrow (`→`)** | Highlighted Category | Keyboard shortcut to expand the selected category. |
| **Left Arrow (`←`)** | Highlighted Category | Keyboard shortcut to collapse the selected category. |
| **Search Input** | Vanilla Search Bar | Real-time filtering with automatic expansion of matching categories. |

---

## ⚙️ Configuration & Persistence Architecture

> [!IMPORTANT]
> **💡 Config vs. In-Game GameRules:** The layout file only defines UI expansion states. In-game rule values are saved directly to your world save (`level.dat` / `game_rules.dat`).

Collapsible Game Rules automatically saves your UI drawer layout to:
```text
config/collapsible-game-rules-state.json
```

### Schema Overview:
```json
{
  "schemaVersion": 1,
  "expandedCategories": [
    "gamerule.category.player",
    "gamerule.category.spawning",
    "gamerule.category.updates"
  ]
}
```

* **Automatic Migration:** Older unversioned configurations are safely upgraded to `schemaVersion: 1` automatically upon launch.
* **Fail-Safe Recovery:** If corrupted JSON syntax is detected, the mod logs a descriptive warning and gracefully initializes clean defaults without crashing the game.
* **Optimized I/O:** Disk writes only occur when you close the screen after making changes, ensuring 60+ FPS smoothness in menu screens.

---

## 📖 In-Depth How-To & Operational Playbook

### 1. Accessing the Modern Game Rules Screen
1. **World Creation:** Navigate to **Singleplayer** ➔ **Create New World** ➔ **Game Rules** tab.
2. **Existing Worlds:** From the world selection menu, select your world ➔ click **Edit** ➔ click **Edit Game Rules** (or access via pause menu settings where supported).
3. The modern collapsible interface loads instantly with all categories neatly organized.

### 2. Streamlining Heavy Modpack Setup
1. In large modpacks with dozens of mods installed, click **`[ Collapse All ]`** at the top.
2. The entire screen condenses into a tidy list of category headers.
3. Open only the category you need (e.g. `Spawning` or your specific mod category).
4. Tweak your desired rule values without getting lost in hundreds of unrelated options.

### 3. Finding Settings with Smart Search
1. Click the vanilla search bar at the top of the screen.
2. Type any keyword (e.g. `tick`, `drop`, or `fire`).
3. Only categories containing matching rules will remain visible, and they will automatically unfold to display the search results.
4. When you delete your search text, your custom expanded/collapsed view is restored.

### 4. Precision Resetting for a Single Category
1. If you experimented with complex mob or spawning rules and want to revert them without touching player tweaks, locate the **Mobs** category header.
2. Hover over the **`↺`** plate on the right side of the header.
3. Click the plate. All rules in that category revert to their official default values instantly!

### 5. Multi-Session Persistence
1. Once you configure your ideal layout (e.g. leaving `Player` and `Updates` open while keeping `Misc` collapsed), click **Done**.
2. Collapsible Game Rules commits your preferences to `collapsible-game-rules-state.json`.
3. Every time you create a new world or edit settings, your preferred layout is ready.

---

## 🧩 Recommended Sister Mods

If you enjoy **Collapsible Game Rules**, these companion mods plug in seamlessly:

* 🧠 [**Dasik Library**](https://modrinth.com/mod/dasik-library): The core universal foundation powering dynamic GameRule registration and auto-translation injection.
* 📦 [**Stack Size Adjuster**](https://modrinth.com/mod/ig-stack-size-adjuster): Customize item stack limits up to extreme quantities, with clean GameRules organized directly inside Collapsible Game Rules.
* 🌿 [**Natural Reproduction**](https://modrinth.com/mod/vo-natural-reproduction): Autonomous 27-species wildlife breeding and genetics configured through native categorized GameRules.

> 🌟 *Explore my profile for more vanilla-friendly enhancements and standalone tools.*

---

## ☕ Support

If you enjoy my mods and want to support ongoing development, consider fueling future updates!

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

> [!NOTE]
> **🇮🇩 Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

> [!TIP]
> **Dedicated Server Hosting Partner:**
> Looking for a high-performance server to play with friends? Check out **BisectHosting** for 1-click modpack installations, automated backups, and 24/7 customer support. Use promo code **`Dasik`** for 25% off your first month!

---

## 📜 Credits & Modpack Permissions

| Property | Information |
| :--- | :--- |
| **Creator / Author** | **Dasik** (Rifaditya) |
| **Collection** | Universal Standalone Library |
| **License** | [GNU General Public License v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html) |
| **Source Code** | [GitHub - Rifaditya/Collapsible-Game-Rule-Screen](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen/issues) |
| **Documentation / Wiki** | [GitHub Wiki](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen/wiki) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Universal Standalone Library</em>
</p>
