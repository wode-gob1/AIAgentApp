package com.personal.aiagent.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.personal.aiagent.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: ChatViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showSettings by remember { mutableStateOf(false) }

    if (showSettings) {
        SettingsScreen(
            viewModel = viewModel,
            onBack = { showSettings = false }
        )
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val conversations by viewModel.conversations.collectAsState()
            val currentConversation by viewModel.currentConversation.collectAsState()

            ConversationListPanel(
                conversations = conversations,
                currentConversationId = currentConversation?.id,
                onSelectConversation = { conversation ->
                    viewModel.selectConversation(conversation)
                    scope.launch { drawerState.close() }
                },
                onNewConversation = {
                    viewModel.createNewConversation()
                    scope.launch { drawerState.close() }
                },
                onDeleteConversation = { conversation ->
                    viewModel.deleteConversation(conversation)
                },
                onOpenSettings = {
                    scope.launch { drawerState.close() }
                    showSettings = true
                }
            )
        }
    ) {
        ChatScreen(
            viewModel = viewModel,
            onOpenDrawer = {
                scope.launch { drawerState.open() }
            },
            onOpenSettings = {
                showSettings = true
            }
        )
    }
}