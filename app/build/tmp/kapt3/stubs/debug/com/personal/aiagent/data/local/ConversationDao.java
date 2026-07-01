package com.personal.aiagent.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\nH\'J\u0018\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u0012"}, d2 = {"Lcom/personal/aiagent/data/local/ConversationDao;", "", "deleteAllConversations", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteConversation", "conversation", "Lcom/personal/aiagent/data/model/Conversation;", "(Lcom/personal/aiagent/data/model/Conversation;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllConversations", "Lkotlinx/coroutines/flow/Flow;", "", "getConversationById", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertConversation", "updateConversation", "app_debug"})
@androidx.room.Dao()
public abstract interface ConversationDao {
    
    @androidx.room.Query(value = "SELECT * FROM conversations ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.personal.aiagent.data.model.Conversation>> getAllConversations();
    
    @androidx.room.Query(value = "SELECT * FROM conversations WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getConversationById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.personal.aiagent.data.model.Conversation> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertConversation(@org.jetbrains.annotations.NotNull()
    com.personal.aiagent.data.model.Conversation conversation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateConversation(@org.jetbrains.annotations.NotNull()
    com.personal.aiagent.data.model.Conversation conversation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteConversation(@org.jetbrains.annotations.NotNull()
    com.personal.aiagent.data.model.Conversation conversation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM conversations")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllConversations(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}