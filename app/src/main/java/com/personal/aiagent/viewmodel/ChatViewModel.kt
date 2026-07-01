package com.personal.aiagent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.personal.aiagent.AIAgentApp
import com.personal.aiagent.data.model.Conversation
import com.personal.aiagent.data.model.Message
import com.personal.aiagent.data.network.ApiModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as AIAgentApp).database
    private val apiService = (application as AIAgentApp).apiService
    private val conversationDao = db.conversationDao()
    private val messageDao = db.messageDao()

    val conversations: StateFlow<List<Conversation>> = conversationDao.getAllConversations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentConversation = MutableStateFlow<Conversation?>(null)
    val currentConversation: StateFlow<Conversation?> = _currentConversation.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _streamingContent = MutableStateFlow("")
    val streamingContent: StateFlow<String> = _streamingContent.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private var currentMessageStream = StringBuilder()

    fun setInputText(text: String) {
        _inputText.value = text
    }

    fun selectConversation(conversation: Conversation) {
        _currentConversation.value = conversation
        _streamingContent.value = ""
        _errorMessage.value = null
        viewModelScope.launch {
            messageDao.getMessagesByConversation(conversation.id).collect { msgs ->
                _messages.value = msgs
            }
        }
    }

    fun createNewConversation() {
        val conv = Conversation(
            title = "新对话",
            systemPrompt = "你是一个有用的AI助手。",
            temperature = 0.7f
        )
        viewModelScope.launch {
            conversationDao.insertConversation(conv)
            _currentConversation.value = conv
            _messages.value = emptyList()
            _streamingContent.value = ""
            _errorMessage.value = null
        }
    }

    fun deleteConversation(conversation: Conversation) {
        viewModelScope.launch {
            conversationDao.deleteConversation(conversation)
            if (_currentConversation.value?.id == conversation.id) {
                _currentConversation.value = null
                _messages.value = emptyList()
            }
        }
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isEmpty()) return

        val conversation = _currentConversation.value ?: run {
            createNewConversation()
            return
        }

        if (!apiService.hasValidConfig()) {
            _errorMessage.value = "请先在设置中配置 API 地址和密钥"
            return
        }

        _inputText.value = ""
        _errorMessage.value = null
        _isLoading.value = true
        _streamingContent.value = ""
        currentMessageStream = StringBuilder()

        viewModelScope.launch {
            val userMsg = Message(
                conversationId = conversation.id,
                role = "user",
                content = text
            )
            messageDao.insertMessage(userMsg)

            val currentMsgs = _messages.value
            val request = ApiModels.json.encodeToString(ApiModels.ChatCompletionRequest.serializer(),
                ApiModels.ChatCompletionRequest(
                    model = conversation.modelName.ifBlank { "default" },
                    messages = ApiModels.messagesFromConversation(
                        currentMsgs + userMsg,
                        conversation.systemPrompt
                    ),
                    temperature = conversation.temperature,
                    stream = true
                )
            )

            var fullContent = ""

            apiService.sendChatRequest(
                requestBody = request,
                onChunk = { chunk ->
                    currentMessageStream.append(chunk)
                    _streamingContent.value = currentMessageStream.toString()
                },
                onComplete = { content ->
                    fullContent = content
                    viewModelScope.launch {
                        if (fullContent.isNotBlank()) {
                            val aiMsg = Message(
                                conversationId = conversation.id,
                                role = "assistant",
                                content = fullContent
                            )
                            messageDao.insertMessage(aiMsg)

                            val msgCount = _messages.value.size
                            if (msgCount <= 1) {
                                val updated = conversation.copy(
                                    title = text.take(30) + if (text.length > 30) "..." else "",
                                    updatedAt = System.currentTimeMillis()
                                )
                                conversationDao.updateConversation(updated)
                                _currentConversation.value = updated
                            } else {
                                conversationDao.updateConversation(
                                    conversation.copy(updatedAt = System.currentTimeMillis())
                                )
                            }
                        }
                        _isLoading.value = false
                        _streamingContent.value = ""
                    }
                },
                onError = { error ->
                    _isLoading.value = false
                    _streamingContent.value = ""
                    _errorMessage.value = error
                }
            )
        }
    }

    fun updateConversationConfig(
        apiUrl: String,
        apiKey: String,
        model: String,
        systemPrompt: String,
        temperature: Float
    ) {
        apiService.updateConfig(apiUrl, apiKey)
        val current = _currentConversation.value
        if (current != null) {
            viewModelScope.launch {
                val updated = current.copy(
                    modelName = model,
                    systemPrompt = systemPrompt,
                    temperature = temperature
                )
                conversationDao.updateConversation(updated)
                _currentConversation.value = updated
            }
        }
    }

    fun dismissError() {
        _errorMessage.value = null
    }

    fun saveApiConfig(apiUrl: String, apiKey: String) {
        apiService.updateConfig(apiUrl, apiKey)
    }

    fun getApiUrl(): String = ""
    fun getApiKey(): String = ""
}