package com.github.rabbameghana.codebaseautocompleteplugin

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import org.json.JSONObject

class CodebaseCompletionProvider : CompletionContributor() {
    private val client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build()

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    println("[Autocomplete] TRIGGERED!")
                    val editor = parameters.editor
                    val offset = parameters.offset
                    val text = editor.document.text
                    val prefix = text.substring(0, offset).takeLast(500)
                    val suffix = text.substring(offset).take(200)
                    val filename = parameters.originalFile.name
                    val language = parameters.originalFile.language.id.lowercase()

                    try {
                        val body = JSONObject()
                        body.put("prefix", prefix)
                        body.put("suffix", suffix)
                        body.put("filename", filename)
                        body.put("language", language)

                        val request = HttpRequest.newBuilder()
                            .uri(URI.create("http://127.0.0.1:8765/complete"))
                            .header("Content-Type", "application/json")
                            .timeout(Duration.ofSeconds(8))
                            .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                            .build()

                        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
                        val completion = response.body().trim()
                        println("[Autocomplete] Got completion: $completion")

                        if (completion.isNotEmpty()) {
                            val firstLine = completion.lines().first().take(60)
                            result.addElement(
                                LookupElementBuilder.create(completion)
                                    .withPresentableText("🤖 $firstLine")
                                    .withTailText("  [AI]", true)
                                    .bold()
                            )
                            result.stopHere()
                        }
                    } catch (e: Exception) {
                        println("[Autocomplete] Error: ${e.message}")
                    }
                }
            }
        )
    }
}
