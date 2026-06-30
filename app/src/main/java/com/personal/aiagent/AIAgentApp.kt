package com.personal.aiagent

import android.app.Application
import com.personal.aiagent.data.local.AppDatabase
import com.personal.aiagent.data.network.ApiService

class AIAgentApp : Application() {
    lateinit var database: AppDatabase
        private set
    lateinit var apiService: ApiService
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = AppDatabase.getDatabase(this)
        apiService = ApiService()
    }

    companion object {
        lateinit var instance: AIAgentApp
            private set
    }
}