package ollamachat.ollamachat.Controller;

import ollamachat.ollamachat.Service.Chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(@RequestParam(value = "message",
            defaultValue = "What's your name?") String message) {
        String response = chatService.generateResponse(message);
        return Map.of("generation", response);
    }



}
