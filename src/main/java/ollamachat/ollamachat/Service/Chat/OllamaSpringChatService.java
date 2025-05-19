package ollamachat.ollamachat.Service.Chat;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaSpringChatService {
    private final OllamaChatModel chatModel;

    @Autowired
    public OllamaSpringChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateResponse(String message) {
        String prompt = "Respond concisely in a maximum of two sentences: " + message;
        return chatModel.call(prompt);
    }
}
