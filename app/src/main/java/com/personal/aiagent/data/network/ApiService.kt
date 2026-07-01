package com.personal.aiagent.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSource
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiService {
    private var baseUrl: String = ""
    private var apiKey: String = ""

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun updateConfig(baseUrl: String, apiKey: String) {
        this.baseUrl = baseUrl.trimEnd('/')
        this.apiKey = apiKey
    }

    fun hasValidConfig(): Boolean {
        return baseUrl.isNotBlank() && apiKey.isNotBlank()
    }

    suspend fun sendChatRequest(
        requestBody: String,
        onChunk: (String) -> Unit,
        onComplete: (String) -> Unit,
        onError: (String) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            val mediaType = "application/json".toMediaType()
            val body = requestBody.toRequestBody(mediaType)

            var url = baseUrl
            if (!url.endsWith("/chat/completions")) {
                url = url.trimEnd('/') + "/chat/completions"
            }

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: ""
                onError("HTTP ${response.code}: $errorBody")
                return@withContext
            }

            val source: BufferedSource = response.body?.source() ?: run {
                onError("Empty response body")
                return@withContext
            }

            val fullContent = StringBuilder()

            while (!source.exhausted()) {
                val line = source.readUtf8LineStrict()
                if (line.startsWith("data: ")) {
                    val data = line.removePrefix("data: ").trim()
                    if (data == "[DONE]") {
                        break
                    }
                    try {
                        val chunk = ApiModels.json.decodeFromString<StreamChunk>(data)
                        val content = chunk.choices?.firstOrNull()?.delta?.content
                        if (content != null) {
                            fullContent.append(content)
                            onChunk(content)
                        }
                    } catch (e: Exception) {
                    }
                } else if (line.isNotBlank()) {
                    try {
                        val responseJson = ApiModels.json.decodeFromString<ChatCompletionResponse>(line)
                        if (responseJson.error != null) {
                            onError(responseJson.error.message)
                            return@withContext
                        }
                    } catch (_: Exception) {}
                }
            }

            onComplete(fullContent.toString())
        } catch (e: IOException) {
            onError("网络错误: ${e.message}")
        } catch (e: Exception) {
            onError("请求错误: ${e.message}")
        }
    }
}