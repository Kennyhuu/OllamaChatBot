package ollamachat.ollamachat.Interface;

import discord4j.core.object.entity.Message; // Importing the Message class from Discord4J
import ollamachat.ollamachat.Service.Chat.OllamaSpringChatService; // Importing ChatService for bot responses
import reactor.core.publisher.Mono; // Importing Mono for reactive programming

// Abstract class to handle message-based events in a Discord bot
public abstract class MessageListener {

    /**
     * Processes user messages and generates a response using the ChatService.
     *
     * @param eventMessage The message event received from Discord
     * @param service The ChatService used to generate responses
     * @return A Mono<Void> representing the completion of message processing
     */
    public Mono<Void> processCommand(Message eventMessage, OllamaSpringChatService service) {
        return Mono.just(eventMessage)
                // Filter out messages from bots to prevent infinite loops
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                // Filter out empty messages
                .filter(message -> !message.getContent().isEmpty())
                .flatMap(message -> {
                    // Extract user input from the message
                    String userChatInput = message.getContent();
                    // Generate bot response using the ChatService
                    String botResponse = service.generateResponse(userChatInput);

                    // Send the bot's response in the same channel
                    return message.getChannel()
                            .flatMap(channel -> channel.createMessage(botResponse));
                })
                .then(); // Ensures the Mono completes without returning a value
    }
}
