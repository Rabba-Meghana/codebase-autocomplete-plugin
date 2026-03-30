# 🤖 Codebase-Aware Autocomplete Plugin for JetBrains IDEs

A self-hosted AI autocomplete plugin for JetBrains IDEs — similar to GitHub Copilot but runs locally and is aware of your entire codebase.

## What I Built
1. A Kotlin plugin that hooks into IntelliJ's CompletionContributor engine
2. A local Python backend that indexes an entire codebase into ChromaDB
3. A RAG pipeline that retrieves the most relevant code chunks on every trigger
4. Groq LLM integration that generates context-aware completions
5. End to end verified pipeline returning real completions from live tests

## Architecture
```
Your Code → Kotlin Plugin → Python RAG Server → ChromaDB → Groq LLM → Completion
```

## Stack
Kotlin · IntelliJ Platform SDK · Python · ChromaDB · Groq API

## Setup
```bash
cd codebase-autocomplete
source venv/bin/activate
python run.py ~/your-project
```
Then install the plugin zip from `build/distributions/` via Settings → Plugins → Install from Disk.
