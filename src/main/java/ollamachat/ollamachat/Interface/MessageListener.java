package ollamachat.ollamachat.Interface;

import discord4j.core.object.entity.Message; // Importing the Message class from Discord4J
import ollamachat.ollamachat.Service.Chat.ChatService; // Importing ChatService for bot responses
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
    public Mono<Void> processCommand(Message eventMessage, ChatService service) {
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

    /**
     * Processes a specific command "!todo" and responds with a predefined to-do list.
     *
     * @param eventMessage The message event received from Discord
     * @return A Mono<Void> representing the completion of the command processing
     */
    public Mono<Void> old_processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                // Filter out messages from bots
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                // Check if the message content is exactly "!todo"
                .filter(message -> message.getContent().equalsIgnoreCase("!todo"))
                // Retrieve the channel where the message was sent
                .flatMap(Message::getChannel)
                // Send a static to-do list response
                .flatMap(channel -> channel.createMessage("Things to do today:\n - write a bot\n - eat lunch\n - play a game"))
                .then(); // Ensures the Mono completes without returning a value
    }
}
