# ⚒️ Collapsible Game Rules Wiki (Einklappbare Spielregeln)

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

> 📌 **Repository-Quellcode-Hinweis**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere unveröffentlichte Commits oder Entwicklungsfunktionen vor öffentlichen Builds auf CurseForge und Modrinth enthalten kann.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Willkommen bei Collapsible Game Rules

**Collapsible Game Rules** transformiert das standardmäßige Spielregel-Menü von Minecraft in eine übersichtliche, einklappbare Benutzeroberfläche mit intelligenter Sucherweiterung, vollständiger Tastaturnavigation, lokaler Zustandsspeicherung und integrierten Voreinstellungen.

---

## 🌟 Hauptfunktionen & Schnellübersicht

| Funktion | Beschreibung | Referenzleitfaden |
| :--- | :--- | :--- |
| **🗂️ Einklappbare Kategorien** | Ersetzt statische Kategorieüberschriften durch interaktive Umschalt-Widgets (`▼`/`▶`) mit Zähler-Badges. | [[🗂️ Einklappbare Kategorien\|de_de-Collapsible-Categories]] |
| **🌎 Globale Aktionsleiste** | Oben angeheftete Schaltflächen «Alle ausklappen» und «Alle einklappen» für schnelle Massensteuerung. | [[🌎 Globale Aktionen & Massenumschalter\|de_de-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Intelligente Suche** | Klappt Kategorien mit Treffern während der Eingabe in das Suchfeld automatisch aus. | [[🔍 Intelligente Suchintegration\|de_de-Smart-Search-Integration]] |
| **⌨️ Tastaturnavigation** | Vollständige Steuerung per Tastatur (Leertaste, Eingabe, Pfeiltasten) und barrierefreie Sprachausgabe. | [[⌨️ Tastaturnavigation & Barrierefreiheit\|de_de-Keyboard-Navigation]] |
| **🧠 Zustandsspeicherung** | Merkt sich ausgeklappte und eingeklappte Kategorien in `config/collapsible-game-rules-state.json`. | [[🧠 Zustandsspeicherung & JSON-Konfiguration\|de_de-State-Persistence-and-Config]] |
| **✨ Schöne Kategorienamen** | Formatiert rohe Übersetzungsschlüssel unübersetzter Mod-Kategorien dynamisch in lesbaren Title Case. | [[✨ Kategorie-Verschönerung & Formatierung\|de_de-Category-Prettification-and-Naming]] |
| **🎛️ Voreinstellungen & Schieberegler** | Vorkonfigurierte Profile (Baumeister, Schnelles Spiel, Hardcore) und stufenlose Schieberegler. | [[🎛️ Spielregel-Voreinstellungen & Steuerelemente\|de_de-Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary-Integration** | Fragt dynamische Übersetzungen über `DynamicGameRuleManager` für saubere Lokalisierung ab. | [[📚 DasikLibrary API-Integration\|de_de-API-and-Library-Integration]] |

---

## 🚀 Schnelleinstieg & Installation

1. Installiere **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Lade die **[Fabric API](https://modrinth.com/mod/fabric-api)** herunter.
3. Lade die **[DasikLibrary](https://modrinth.com/mod/dasik-library)** herunter (`>=1.7.0`, **Erforderliche Abhängigkeit**).
4. Platziere `collapsible-game-rules-1.0.9+26.2.jar` im Ordner `.minecraft/mods`.
5. Starte Minecraft mit **Java 25+**.

---

## 📚 Vollständiger Dokumentationsindex

```
Wiki-Wurzelverzeichnis
├── 🧭 Versionskompatibilitätsmatrix -----------> [[🧭 Versionskompatibilitätsmatrix|de_de-Version-Compatibility]]
├── 🎮 UI-Kernmechaniken
│   ├── Einklappbare Kategorien -------------> [[🗂️ Einklappbare Kategorien|de_de-Collapsible-Categories]]
│   ├── Globale Aktionen & Massenumschalter --> [[🌎 Globale Aktionen & Massenumschalter|de_de-Global-Actions-and-Bulk-Toggles]]
│   ├── Intelligente Suchintegration ---------> [[🔍 Intelligente Suchintegration|de_de-Smart-Search-Integration]]
│   └── Tastaturnavigation & Barrierefreiheit -> [[⌨️ Tastaturnavigation & Barrierefreiheit|de_de-Keyboard-Navigation]]
├── ⚙️ Konfiguration & Voreinstellungen
│   ├── Zustandsspeicherung & JSON-Config ----> [[🧠 Zustandsspeicherung & JSON-Konfiguration|de_de-State-Persistence-and-Config]]
│   ├── Kategorie-Verschönerung & Formatierung -> [[✨ Kategorie-Verschönerung & Formatierung|de_de-Category-Prettification-and-Naming]]
│   └── Voreinstellungen & Steuerelemente ----> [[🎛️ Spielregel-Voreinstellungen & Steuerelemente|de_de-Game-Rule-Presets-and-Controls]]
├── 📋 Umfang & Spielreferenz
│   ├── Spielregeln-Referenztabelle ----------> [[📜 Spielregeln-Referenztabelle|de_de-GameRules-Reference]]
│   ├── Brigadier-Befehle & Abgrenzung -------> [[💬 Brigadier-Befehle & Abgrenzung|de_de-Commands]]
│   └── Fortschritte & Abgrenzung ------------> [[🏆 Fortschritte & Abgrenzung|de_de-Advancements]]
└── 💻 Technische Architektur & Entwicklung
    ├── HUD, Diagnose & UI-Rendering ---------> [[🖥️ HUD, Diagnose & UI-Rendering|de_de-HUD-and-Diagnostics]]
    ├── Entwickler-Setup & Gradle-Builds -----> [[🛠️ Entwickler-Setup & Gradle-Builds|de_de-Developer-Setup-and-Building]]
    ├── Architektur & Mixin-Subsystem --------> [[🧩 Architektur & Mixin-Subsystem|de_de-Architecture-and-Mixins]]
    └── DasikLibrary API-Integration ---------> [[📚 DasikLibrary API-Integration|de_de-API-and-Library-Integration]]
```

---

## 📜 Urheberrecht & Lizenz

* **Autor & Leitender Entwickler**: **Dasik (Rifaditya)**
* **Lizenz**: **GNU General Public License v3.0 (GPLv3)**
* **GitHub-Repository**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth-Projekt**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge-Projekt**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
