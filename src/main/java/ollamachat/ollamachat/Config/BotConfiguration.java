package ollamachat.ollamachat.Config;

import discord4j.core.DiscordClientBuilder; // Importing the builder class to create a Discord client
import discord4j.core.GatewayDiscordClient; // The client that connects to Discord's gateway
import ollamachat.ollamachat.Interface.EventListener; // Importing the EventListener interface
import discord4j.core.event.domain.Event; // The base event class in Discord4J
import org.springframework.beans.factory.annotation.Value; // To inject property values from the application properties
import org.springframework.context.annotation.Bean; // Marks the method as a Bean producer
import org.springframework.context.annotation.Configuration; // Indicates that this class is a configuration class in Spring

import java.util.List; // For working with lists of event listeners

@Configuration // Marks this class as a configuration class for Spring context
public class BotConfiguration {

    // Injects the token value from the application.properties or application.yml file into this field
    @Value("${token}")
    private String token;

    /**
     * This method is annotated with @Bean to create a bean of type GatewayDiscordClient
     * that will be managed by the Spring container. This bean connects to the Discord gateway.
     *
     * @param eventListeners A list of EventListeners that will handle specific events
     * @return The configured GatewayDiscordClient
     */
    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        // Build the GatewayDiscordClient using the token (loaded from properties)
        GatewayDiscordClient client = DiscordClientBuilder.create(token) // Creates a new Discord client with the token
                .build() // Builds the client
                .login() // Logs in to Discord with the provided token asynchronously
                .block(); // Blocks the thread until the login process completes (synchronous)

        // Iterate over the list of event listeners and register each one with the Discord client
        for (EventListener<T> listener : eventListeners) {
            // Subscribe to the event type the listener is responsible for
            client.on(listener.getEventType()) // This listens for the specified event type
                    .flatMap(listener::execute) // When the event occurs, execute the listener's logic
                    .onErrorResume(listener::handleError) // In case of an error, call the handleError method
                    .subscribe(); // Subscribe to the event stream, effectively enabling the listener
        }

        // Return the configured client after all listeners are registered
        return client;
    }
}
