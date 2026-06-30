package com.personal.aiagent.data.network

import com.personal.aiagent.data.model.Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ChatCompletionRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Float = 0.7f,
    val stream: Boolean = true
)

@Serializable
data class ChatMessage(
    val role: String,
    val content: String
)

@Serializable
data class ChatCompletionResponse(
    val id: String? = null,
    val choices: List<Choice>? = null,
    val error: ErrorInfo? = null
)

@Serializable
data class Choice(
    val index: Int = 0,
    val delta: Delta? = null,
    val message: ChatMessage? = null,
    @SerialName("finish_reason")
    val finishReason: String? = null
)

@Serializable
data class Delta(
    val role: String? = null,
    val content: String? = null
)

@Serializable
data class ErrorInfo(
    val message: String = "",
    val type: String? = null
)

@Serializable
data class StreamChunk(
    val id: String? = null,
    val choices: List<Choice>? = null,
    val error: ErrorInfo? = null
)

object ApiModels {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun messagesFromConversation(messages: List<Message>, systemPrompt: String): List<ChatMessage> {
        val result = mutableListOf<ChatMessage>()
        if (systemPrompt.isNotBlank()) {
            result.add(ChatMessage(role = "system", content = systemPrompt))
        }
        for (msg in messages) {
            result.add(ChatMessage(role = msg.role, content = msg.content))
        }
        return result
    }
}