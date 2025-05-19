package ollamachat.ollamachat.Controller;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import ollamachat.ollamachat.Service.Chat.OllamaSpringChatService;
import ollamachat.ollamachat.Service.Chat.ChatServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;


import java.util.Map;

@RestController
public class ChatController {
    private Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final OllamaSpringChatService ollamaSpringChatService;
    private final ChatLanguageModel chatLanguageModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    @Autowired
    public ChatController(OllamaSpringChatService ollamaSpringChatService, ChatLanguageModel chatLanguageModel, EmbeddingStore<TextSegment> embeddingStore) {
        this.ollamaSpringChatService = ollamaSpringChatService;
        this.chatLanguageModel = chatLanguageModel;
        this.embeddingStore = embeddingStore;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(
            @RequestParam(
                    value = "message",
                    defaultValue = "What's your name?"
            ) String message) {
        String response = ollamaSpringChatService.generateResponse(message);
        return Map.of("generation", response);
    }

    @GetMapping("/chat")
    public String getLLMResponse(
            @RequestParam(
                    value = "userQuery",
                    defaultValue = "Give me a Question?"
            ) String userQuery) {
        ChatServiceInterface chatService = AiServices.builder(ChatServiceInterface.class).chatLanguageModel(chatLanguageModel).chatMemory(MessageWindowChatMemory.withMaxMessages(10)).contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore)).build();

        return chatService.chat(userQuery);
    }

    @GetMapping("/load")
    public void loadDocument() {
        logger.info("Loading document");
        Document document = FileSystemDocumentLoader.loadDocument("/Users/shivamrao/Downloads/baburaoChatbot/src/main/resources/docs/mAstra-support.txt");
        EmbeddingStoreIngestor.ingest(document, embeddingStore);
        logger.info("Document loaded");
    }


}
