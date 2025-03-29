package ollamachat.ollamachat.Service.Discord;

import discord4j.core.event.domain.message.MessageCreateEvent; // Importing the event when a message is created in Discord
import ollamachat.ollamachat.Interface.EventListener;  // Importing the EventListener interface that we are implementing
import ollamachat.ollamachat.Interface.MessageListener; // Importing MessageListener interface, which is extended by this class
import ollamachat.ollamachat.Service.ChatService; // Importing the ChatService that will be used to process the message
import org.springframework.beans.factory.annotation.Autowired; // Used for dependency injection of Spring beans
import org.springframework.stereotype.Service; // Denotes that this class is a service (business logic)
import reactor.core.publisher.Mono; // Importing Mono from Reactor to handle asynchronous operations

// This is a service class that listens for the "MessageCreateEvent" in Discord
@Service
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {

    // Instance of ChatService which will be used to process messages
    private final ChatService chatService;

    /**
     * Constructor for MessageCreateListener. Spring will automatically inject
     * the ChatService bean into this class.
     *
     * @param chatService The ChatService to be used for message processing
     */
    @Autowired
    public MessageCreateListener(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * This method defines the type of event that this listener handles.
     * Here, it is a MessageCreateEvent, which is triggered when a new message
     * is created in a Discord channel.
     *
     * @return Class type of the event, which in this case is MessageCreateEvent.class
     */
    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    /**
     * This method is executed when a MessageCreateEvent is triggered. It processes
     * the message in the event using the ChatService.
     *
     * @param event The event triggered by Discord when a new message is created.
     * @return A Mono<Void> indicating the completion of the event processing
     */
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        // Calls the processCommand method (from MessageListener) to process the message
        // and pass the chatService for further processing (like command handling).
        return processCommand(event.getMessage(), chatService);
    }
}
