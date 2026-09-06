# ⚒️ 可折叠游戏规则 Wiki

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

> 📌 **仓库源码声明**：本 Wiki 中的文档反映了**仓库中的当前源代码状态**，可能包含领先于 CurseForge 和 Modrinth 上公开发布版本的最新未发布提交或开发中功能。

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 欢迎使用可折叠游戏规则

**Collapsible Game Rules** 将 Minecraft 原生的游戏规则设置界面重构为结构清晰的折叠式层次界面，支持智能搜索展开、键盘导航、本地状态记忆与内置玩法预设。

---

## 🌟 核心特性与快速索引

| 特性 | 描述 | 参考指南 |
| :--- | :--- | :--- |
| **🗂️ 可折叠类别** | 将静态分类标题替换为带有规则计数徽章的交互切换组件（`▼`/`▶`）。 | [[🗂️ 可折叠类别\|zh_cn-Collapsible-Categories]] |
| **🌎 全局操作顶栏** | 顶部固定的一键“全部展开”与“全部折叠”批量控制按钮。 | [[🌎 全局操作与批量切换\|zh_cn-Global-Actions-and-Bulk-Toggles]] |
| **🔍 智能搜索展开** | 在搜索框中键入关键字时，自动动态展开包含匹配规则的所有分类。 | [[🔍 智能搜索集成\|zh_cn-Smart-Search-Integration]] |
| **⌨️ 键盘导航** | 完整的键盘支持（空格、回车、左右方向键）与屏幕朗读无障碍辅助。 | [[⌨️ 键盘导航与无障碍辅助\|zh_cn-Keyboard-Navigation]] |
| **🧠 状态持久化** | 在 `config/collapsible-game-rules-state.json` 中记忆分类的展开与折叠状态。 | [[🧠 状态持久化与 JSON 配置\|zh_cn-State-Persistence-and-Config]] |
| **✨ 智能名称美化** | 自动将模组未翻译的原始分类键格式化为规范的英文首字母大写标题。 | [[✨ 类别名称美化与格式化\|zh_cn-Category-Prettification-and-Naming]] |
| **🎛️ 规则预设与滑块** | 内置预设（建造者、快速游戏、硬核）以及数值滑块和二值开关组件。 | [[🎛️ 游戏规则预设与控件\|zh_cn-Game-Rule-Presets-and-Controls]] |
| **🧩 DasikLibrary 集成** | 查询 `DynamicGameRuleManager` 动态生成的本地化翻译支持。 | [[📚 DasikLibrary API 集成\|zh_cn-API-and-Library-Integration]] |

---

## 🚀 快速入门与安装

1. 安装 **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`)。
2. 下载 **[Fabric API](https://modrinth.com/mod/fabric-api)**。
3. 下载 **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **硬性前置依赖**)。
4. 将 `collapsible-game-rules-1.0.9+26.2.jar` 放入您的 `.minecraft/mods` 目录。
5. 使用 **Java 25+** 启动 Minecraft。

---

## 📚 完整文档索引

```
Wiki 根目录
├── 🧭 版本兼容性矩阵 ---------> [[🧭 版本兼容性矩阵|zh_cn-Version-Compatibility]]
├── 🎮 核心 UI 机制
│   ├── 可折叠类别 --------------> [[🗂️ 可折叠类别|zh_cn-Collapsible-Categories]]
│   ├── 全局操作与批量切换 -------> [[🌎 全局操作与批量切换|zh_cn-Global-Actions-and-Bulk-Toggles]]
│   ├── 智能搜索集成 ------------> [[🔍 智能搜索集成|zh_cn-Smart-Search-Integration]]
│   └── 键盘导航与无障碍辅助 -> [[⌨️ 键盘导航与无障碍辅助|zh_cn-Keyboard-Navigation]]
├── ⚙️ 配置与预设
│   ├── 状态持久化与 JSON 配置 -----> [[🧠 状态持久化与 JSON 配置|zh_cn-State-Persistence-and-Config]]
│   ├── 类别名称美化与格式化 ----> [[✨ 类别名称美化与格式化|zh_cn-Category-Prettification-and-Naming]]
│   └── 游戏规则预设与控件 ---------> [[🎛️ 游戏规则预设与控件|zh_cn-Game-Rule-Presets-and-Controls]]
├── 📋 游戏范畴与参考
│   ├── 游戏规则参考表 -----------------> [[📜 游戏规则参考表|zh_cn-GameRules-Reference]]
│   ├── Brigadier 命令与范围说明 --> [[💬 Brigadier 命令与范围说明|zh_cn-Commands]]
│   └── 进度与范围说明 --------> [[🏆 进度与范围说明|zh_cn-Advancements]]
└── 💻 技术架构与开发
    ├── HUD、诊断与 UI 渲染 ----> [[🖥️ HUD、诊断与 UI 渲染|zh_cn-HUD-and-Diagnostics]]
    ├── 开发者环境配置与 Gradle 构建 -----> [[🛠️ 开发者环境配置与 Gradle 构建|zh_cn-Developer-Setup-and-Building]]
    ├── 架构与 Mixin 子系统 ------> [[🧩 架构与 Mixin 子系统|zh_cn-Architecture-and-Mixins]]
    └── DasikLibrary API 集成 --------> [[📚 DasikLibrary API 集成|zh_cn-API-and-Library-Integration]]
```

---

## 📜 版权与许可协议

* **作者与首席工程师**: **Dasik (Rifaditya)**
* **开源协议**: **GNU General Public License v3.0 (GPLv3)**
* **代码仓库**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Modrinth 项目**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **CurseForge 项目**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
