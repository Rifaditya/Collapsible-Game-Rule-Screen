# ⚒️ Collapsible Game Rules Wiki (Regras de Jogo Dobráveis)

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

> 📌 **Aviso sobre o Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das compilações públicas no CurseForge e Modrinth.

---

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

## 🧭 Bem-vindo ao Collapsible Game Rules

O **Collapsible Game Rules** reformula a interface padrão de regras de jogo do Minecraft, transformando-a em uma tela organizada e dobrável com expansão inteligente na pesquisa, navegação completa pelo teclado, persistência de estado local e predefinições de jogo integradas.

---

## 🌟 Recursos Principais e Índice Rápido

| Recurso | Descrição | Guia de Referência |
| :--- | :--- | :--- |
| **🗂️ Categorias Dobráveis** | Substitui os cabeçalhos estáticos por widgets interativos (`▼`/`▶`) com emblemas de contagem de regras. | [[🗂️ Categorias Dobráveis\|pt_br-Collapsible-Categories]] |
| **🌎 Barra de Ações Globais** | Botões superiores fixados para «Expandir Tudo» e «Recolher Tudo» com apenas um clique. | [[🌎 Ações Globais & Alternância em Massa\|pt_br-Global-Actions-and-Bulk-Toggles]] |
| **🔍 Pesquisa Inteligente** | Expande automaticamente as categorias correspondentes conforme o jogador digita na barra de pesquisa. | [[🔍 Integração de Busca Inteligente\|pt_br-Smart-Search-Integration]] |
| **⌨️ Navegação por Teclado** | Suporte total a atalhos de teclado (Espaço, Enter, setas) e narração por leitores de tela para acessibilidade. | [[⌨️ Navegação por Teclado & Acessibilidade\|pt_br-Keyboard-Navigation]] |
| **🧠 Persistência de Estado** | Salva as categorias expandidas e recolhidas localmente em `config/collapsible-game-rules-state.json`. | [[🧠 Persistência de Estado & Configuração JSON\|pt_br-State-Persistence-and-Config]] |
| **✨ Embelezamento de Nomes** | Converte chaves brutas de categorias de mods não traduzidas em títulos legíveis no padrão Title Case. | [[✨ Embelezamento & Nomeação de Categorias\|pt_br-Category-Prettification-and-Naming]] |
| **🎛️ Predefinições e Sliders** | Perfis prontos (Construtor, Jogo Rápido, Hardcore), sliders numéricos e botões de alternância direta. | [[🎛️ Predefinições de Regras de Jogo & Controles\|pt_br-Game-Rule-Presets-and-Controls]] |
| **🧩 Integração com DasikLibrary** | Consulta traduções geradas no `DynamicGameRuleManager` para localização dinâmica de categorias. | [[📚 Integração com a API do DasikLibrary\|pt_br-API-and-Library-Integration]] |

---

## 🚀 Início Rápido e Instalação

1. Instale o **[Fabric Loader](https://fabricmc.net/)** (`>=0.19.1`).
2. Baixe a **[Fabric API](https://modrinth.com/mod/fabric-api)**.
3. Baixe a **[DasikLibrary](https://modrinth.com/mod/dasik-library)** (`>=1.7.0`, **Requisito Obrigatório**).
4. Coloque o arquivo `collapsible-game-rules-1.0.9+26.2.jar` dentro da pasta `.minecraft/mods`.
5. Inicie o Minecraft utilizando o **Java 25+**.

---

## 📚 Índice Completo de Documentação

```
Raiz da Wiki
├── 🧭 Matriz de Compatibilidade de Versões ----> [[🧭 Matriz de Compatibilidade de Versões|pt_br-Version-Compatibility]]
├── 🎮 Mecânicas Principais da Interface
│   ├── Categorias Dobráveis -------------------> [[🗂️ Categorias Dobráveis|pt_br-Collapsible-Categories]]
│   ├── Ações Globais e Alternância em Massa ---> [[🌎 Ações Globais & Alternância em Massa|pt_br-Global-Actions-and-Bulk-Toggles]]
│   ├── Integração de Busca Inteligente --------> [[🔍 Integração de Busca Inteligente|pt_br-Smart-Search-Integration]]
│   └── Navegação por Teclado e Acessibilidade -> [[⌨️ Navegação por Teclado & Acessibilidade|pt_br-Keyboard-Navigation]]
├── ⚙️ Configuração e Predefinições
│   ├── Persistência de Estado e JSON ----------> [[🧠 Persistência de Estado & Configuração JSON|pt_br-State-Persistence-and-Config]]
│   ├── Embelezamento e Nomes de Categorias ----> [[✨ Embelezamento & Nomeação de Categorias|pt_br-Category-Prettification-and-Naming]]
│   └── Predefinições de Regras e Controles ----> [[🎛️ Predefinições de Regras de Jogo & Controles|pt_br-Game-Rule-Presets-and-Controls]]
├── 📋 Escopo e Referência
│   ├── Tabela de Referência de Regras ---------> [[📜 Tabela de Referência de Regras de Jogo|pt_br-GameRules-Reference]]
│   ├── Comandos Brigadier e Escopo ------------> [[💬 Comandos Brigadier & Escopo|pt_br-Commands]]
│   └── Progressos e Escopo --------------------> [[🏆 Progressos & Escopo|pt_br-Advancements]]
└── 💻 Arquitetura Técnica e Desenvolvimento
    ├── HUD, Diagnósticos e Renderização ------> [[🖥️ HUD, Diagnósticos & Renderização de Interface|pt_br-HUD-and-Diagnostics]]
    ├── Configuração do Desenvolvedor e Gradle -> [[🛠️ Configuração de Desenvolvedor & Compilação Gradle|pt_br-Developer-Setup-and-Building]]
    ├── Arquitetura e Subsistema Mixin ---------> [[🧩 Arquitetura & Subsistema Mixin|pt_br-Architecture-and-Mixins]]
    └── Integração com a API do DasikLibrary ---> [[📚 Integração com a API do DasikLibrary|pt_br-API-and-Library-Integration]]
```

---

## 📜 Créditos e Licença

* **Autor e Engenheiro Líder**: **Dasik (Rifaditya)**
* **Licença**: **GNU General Public License v3.0 (GPLv3)**
* **Repositório GitHub**: [GitHub Repository](https://github.com/Rifaditya/Collapsible-Game-Rule-Screen)
* **Projeto no Modrinth**: [Modrinth](https://modrinth.com/mod/collapsible-game-rules) (`lObgjyJl`)
* **Projeto no CurseForge**: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/collapsible-game-rules) (`1468932`)
