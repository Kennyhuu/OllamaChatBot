package ollamachat.ollamachat.Interface;

import discord4j.core.event.domain.Event; // Importing the Event class from Discord4J
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

// Defining a generic interface for handling Discord events
public interface EventListener<T extends Event> {

    // Logger instance to log messages and errors
    Logger LOG = LoggerFactory.getLogger(EventListener.class);

    /**
     * Method to specify the type of event this listener is responsible for.
     *
     * @return Class of the event type
     */
    Class<T> getEventType();

    /**
     * Method to handle the event asynchronously.
     * It takes an event as input and returns a Mono<Void>
     * indicating reactive processing (non-blocking).
     *
     * @param event The event to be processed
     * @return A Mono<Void> representing the completion of event handling
     */
    Mono<Void> execute(T event);

    /**
     * Default method to handle errors that may occur while processing events.
     * It logs the error message along with the event type.
     *
     * @param error The error/exception encountered
     * @return A Mono<Void> representing an empty reactive sequence
     */
    default Mono<Void> handleError(Throwable error) {
        // Logs the error message along with the name of the event type
        LOG.error("Unable to process " + getEventType().getSimpleName(), error);

        // Returns an empty Mono to ensure the error doesn't propagate further
        return Mono.empty();
    }
}