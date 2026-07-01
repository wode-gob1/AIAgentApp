package com.personal.aiagent;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000f"}, d2 = {"Lcom/personal/aiagent/AIAgentApp;", "Landroid/app/Application;", "()V", "<set-?>", "Lcom/personal/aiagent/data/network/ApiService;", "apiService", "getApiService", "()Lcom/personal/aiagent/data/network/ApiService;", "Lcom/personal/aiagent/data/local/AppDatabase;", "database", "getDatabase", "()Lcom/personal/aiagent/data/local/AppDatabase;", "onCreate", "", "Companion", "app_debug"})
public final class AIAgentApp extends android.app.Application {
    private com.personal.aiagent.data.local.AppDatabase database;
    private com.personal.aiagent.data.network.ApiService apiService;
    private static com.personal.aiagent.AIAgentApp instance;
    @org.jetbrains.annotations.NotNull()
    public static final com.personal.aiagent.AIAgentApp.Companion Companion = null;
    
    public AIAgentApp() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.personal.aiagent.data.local.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.personal.aiagent.data.network.ApiService getApiService() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/personal/aiagent/AIAgentApp$Companion;", "", "()V", "<set-?>", "Lcom/personal/aiagent/AIAgentApp;", "instance", "getInstance", "()Lcom/personal/aiagent/AIAgentApp;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.personal.aiagent.AIAgentApp getInstance() {
            return null;
        }
    }
}