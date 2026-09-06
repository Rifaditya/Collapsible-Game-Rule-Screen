# ⚒️ 可折疊遊戲規則 Wiki

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

> 📌 **倉庫源碼聲明**：本 Wiki 中的文件反映了**倉庫中的當前原始碼狀態**，可能包含領先於 CurseForge 與 Modrinth 上公開發布版本的最新未發布提交或開發中功能。

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 歡迎使用可折疊遊戲規則

**Collapsible Game Rules** 將 Minecraft 原生的遊戲規則設定介面重構為結構清晰的折疊式層次介面，支援智慧搜尋展開、鍵盤導航、本機狀態記憶與內建玩法預設。

---

## 🌟 核心特性與快速索引

| 特性 | 描述 | 參考指南 |
| :--- | :--- | :--- |
| **🗂️ 可折疊類別** | 將靜態分類標題替換為帶有規則計數徽章的互動切換元件（`▼`/`▶`）。 | [[🗂️ 可折疊類別\|zh_tw-Collapsible-Categories]] |
| **🌎 全域操作頂欄** | 頂部固定的一鍵「全部展開」與「全部折疊」批次控制按鈕。 | [[🌎 全域操作與批次切換\|zh_tw-Global-Actions-and-Bulk-Toggles]] |
| **🔍 智慧搜尋展開** | 在搜尋框中鍵入關鍵字時，自動動態展開包含匹配規則的所有分類。 | [[🔍 智慧搜尋整合\|zh_tw-Smart-Search-Integration]] |
| **⌨️ 鍵盤導航** | 完整的鍵盤支援（空格、Enter、左右方向鍵）與螢幕朗讀無障礙輔助。 | [[⌨️ 鍵盤導航與無障礙輔助\|zh_tw-Keyboard-Navigation]] |
| **🧠 狀態持久化** | 在 `config/collapsible-game-rules-state.json` 中記憶分類的展開與折疊狀態。 | [[🧠 狀態持久化與 JSON 設定\|zh_tw-State-Persistence-and-Config]] |
| **✨ 智慧名稱美化** | 自動將模組未翻譯的原始分類鍵格式化為規範的英文首字母大寫標題。 | [[✨ 類別名稱美化與格式化\|zh_tw-Category-Prettification-and-Naming]] |
| **🎛️ 規則預設與滑桿** | 內建預設（建造者、快速遊戲、硬核）以及數值滑桿和二值開關元件。 | [[🎛️ 遊戲規則預設與控制元件\|zh_tw-Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary 整合** | 查詢 `DynamicGameRuleManager` 動態產生的在地化翻譯支援。 | [[📚 DasikLibrary API 整合\|zh_tw-API-and-Library-Integration]] |

---

## 🚀 快速入門與安裝

1. 安裝 **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`)。
2. 下載 **[Fabric API](https://modrinth.com/mod/fabric-api)**。
3. 下載 **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **硬性前置相依**)。
4. 將 `collapsible-game-rules-1.0.9+26.2.jar` 放入您的 `.minecraft/mods` 目錄。
5. 使用 **Java 25+** 啟動 Minecraft。

---

## 📚 完整文件索引

```
Wiki 根目錄
├── 🧭 版本相容性矩陣 ---------> [[🧭 版本相容性矩陣|zh_tw-Version-Compatibility]]
├── 🎮 核心 UI 機制
│   ├── 可折疊類別 --------------> [[🗂️ 可折疊類別|zh_tw-Collapsible-Categories]]
│   ├── 全域操作與批次切換 -------> [[🌎 全域操作與批次切換|zh_tw-Global-Actions-and-Bulk-Toggles]]
│   ├── 智慧搜尋整合 ------------> [[🔍 智慧搜尋整合|zh_tw-Smart-Search-Integration]]
│   └── 鍵盤導航與無障礙輔助 -> [[⌨️ 鍵盤導航與無障礙輔助|zh_tw-Keyboard-Navigation]]
├── ⚙️ 設定與預設
│   ├── 狀態持久化與 JSON 設定 -----> [[🧠 狀態持久化與 JSON 設定|zh_tw-State-Persistence-and-Config]]
│   ├── 類別名稱美化與格式化 ----> [[✨ 類別名稱美化與格式化|zh_tw-Category-Prettification-and-Naming]]
│   └── 遊戲規則預設與控制元件 ---------> [[🎛️ 遊戲規則預設與控制元件|zh_tw-Game-Rule-Presets-and-Controls]]
├── 📋 遊戲範疇與參考
│   ├── 遊戲規則參考表 -----------------> [[📜 遊戲規則參考表|zh_tw-GameRules-Reference]]
│   ├── Brigadier 指令與範圍說明 --> [[💬 Brigadier 指令與範圍說明|zh_tw-Commands]]
│   └── 進度與範圍說明 --------> [[🏆 進度與範圍說明|zh_tw-Advancements]]
└── 💻 技術架構與開發
    ├── HUD、診斷與 UI 渲染 ----> [[🖥️ HUD、診斷與 UI 渲染|zh_tw-HUD-and-Diagnostics]]
    ├── 開發者環境配置與 Gradle 構建 -----> [[🛠️ 開發者環境配置與 Gradle 構建|zh_tw-Developer-Setup-and-Building]]
    ├── 架構與 Mixin 子系統 ------> [[🧩 架構與 Mixin 子系統|zh_tw-Architecture-and-Mixins]]
    └── DasikLibrary API 整合 --------> [[📚 DasikLibrary API 整合|zh_tw-API-and-Library-Integration]]
```

---

## 📜 版權與許可協議

* **作者與首席工程師**: **Dasik (Rifaditya)**
* **開源協議**: **GNU General Public License v3.0 (GPLv3)**
* **程式碼倉庫**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth 專案**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge 專案**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
