package ollamachat.ollamachat.Service;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final OllamaChatModel chatModel;

    @Autowired
    public ChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateResponse(String message) {
        return chatModel.call(message);
    }
}
