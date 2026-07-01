package com.personal.aiagent.data.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

object ApiModels {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Serializable
    data class ChatCompletionRequest(
        val model: String,
        val messages: List<ChatCompletionMessage>,
        val temperature: Float = 0.7f,
        val stream: Boolean = true
    )

    @Serializable
    data class ChatCompletionMessage(
        val role: String,
        val content: String
    )

    @Serializable
    data class ChatCompletionResponse(
        val id: String? = null,
        val `object`: String? = null,
        val created: Long? = null,
        val model: String? = null,
        val choices: List<Choice>? = null,
        val error: ApiError? = null
    )

    @Serializable
    data class Choice(
        val index: Int? = null,
        val message: ApiMessage? = null,
        val delta: Delta? = null,
        val finish_reason: String? = null
    )

    @Serializable
    data class ApiMessage(
        val role: String? = null,
        val content: String? = null
    )

    @Serializable
    data class Delta(
        val role: String? = null,
        val content: String? = null
    )

    @Serializable
    data class ApiError(
        val message: String = ""
    )

    fun messagesFromConversation(
        messages: List<com.personal.aiagent.data.model.Message>,
        systemPrompt: String
    ): List<ChatCompletionMessage> {
        val result = mutableListOf<ChatCompletionMessage>()
        
        if (systemPrompt.isNotBlank()) {
            result.add(ChatCompletionMessage(role = "system", content = systemPrompt))
        }
        
        for (msg in messages) {
            result.add(ChatCompletionMessage(role = msg.role, content = msg.content))
        }
        
        return result
    }
}

@Serializable
data class StreamChunk(
    val id: String? = null,
    val `object`: String? = null,
    val created: Long? = null,
    val model: String? = null,
    val choices: List<StreamChoice>? = null
)

@Serializable
data class StreamChoice(
    val index: Int? = null,
    val delta: Delta? = null,
    val finish_reason: String? = null
)