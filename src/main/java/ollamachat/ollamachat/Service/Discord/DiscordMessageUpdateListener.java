package ollamachat.ollamachat.Service.Discord;

import discord4j.core.event.domain.message.MessageUpdateEvent; // Importing the event class for message updates
import ollamachat.ollamachat.Interface.EventListener; // Importing the custom EventListener interface
import ollamachat.ollamachat.Interface.MessageListener; // Importing the custom MessageListener interface
import ollamachat.ollamachat.Service.Chat.OllamaSpringChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono; // Mono for reactive programming (non-blocking)

@Service // Marks this class as a Spring service bean to be managed by the Spring container
public class DiscordMessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent> {

    private final OllamaSpringChatService ollamaSpringChatService; // The ChatService is injected and used for message processing

    // Constructor to inject ChatService into the MessageUpdateListener
    @Autowired
    public DiscordMessageUpdateListener(OllamaSpringChatService ollamaSpringChatService) {
        this.ollamaSpringChatService = ollamaSpringChatService; // Initialize the chatService
    }

    /**
     * This method defines the type of event that this listener will handle.
     * Here, we specify that this listener is responsible for handling MessageUpdateEvent.
     *
     * @return the class of the event type handled by this listener
     */
    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class; // Returning the MessageUpdateEvent class as the event type
    }

    /**
     * This method handles the actual processing of the event when a message is updated.
     * If the message content has changed, it processes the message.
     *
     * @param event The MessageUpdateEvent containing the details of the updated message
     * @return A Mono<Void> representing the asynchronous operation for processing the message
     */
    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        // Using Mono to process the event reactively
        return Mono.just(event) // Wrap the event into a Mono
                .filter(MessageUpdateEvent::isContentChanged) // Filter only if the message content has changed
                .flatMap(MessageUpdateEvent::getMessage) // Extract the updated message from the event
                .flatMap(message -> processCommand(message, this.ollamaSpringChatService)); // Process the command for the updated message
    }

    /**
     * Processes the command in the message.
     * This method is assumed to be implemented in a parent class (MessageListener) or elsewhere.
     *
     * @param message The message that needs processing
     * @param ollamaSpringChatService The chat service used to process the command
     * @return A Mono<Void> representing the asynchronous processing of the message
     */
    private Mono<Void> processCommand(Object message, OllamaSpringChatService ollamaSpringChatService) {
        // Here you'd implement logic to process the command in the message, like handling commands
        return Mono.empty(); // Placeholder, as the actual logic is dependent on your implementation
    }
}
