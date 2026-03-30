# 🤖 Codebase-Aware Autocomplete Plugin for JetBrains IDEs

A self-hosted AI autocomplete plugin for JetBrains IDEs — similar to GitHub Copilot but runs locally and is aware of your entire codebase.

## How It Works
1. Indexes your codebase using ChromaDB (vector database)
2. Retrieves relevant context via RAG when you trigger autocomplete
3. Generates completions using Groq LLM API with streaming
4. Shows suggestions directly in the JetBrains completion dropdown

## Stack
- Kotlin + IntelliJ Platform SDK (IDE plugin)
- Python + Flask (backend server)
- ChromaDB (vector database)
- Groq API (LLM completions)

## Setup
```bash
cd codebase-autocomplete
source venv/bin/activate
python run.py ~/your-project
```
Then install the plugin zip from `build/distributions/` via Settings → Plugins → Install from Disk.
