package com.github.petermylemans.learning.websockets

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

/**
 * Basic setup of WebSockets. This is all you need to get started.
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // Enable a simple builtin message broker. Note that this one is not cluster aware (think local hash maps for storing session information)
        // All destinations prefixed by /topic are considered destinations that clients should listen to, and thus are managed by the message broker.
        // This is a good convention to follow.
        registry.enableSimpleBroker("/topic")

        // All destinations prefixed by /app are considered destinations that application is listening on for requests from clients.
        // These are not routed to the broker, but are handled by methods annotated with @MessageMapping (with a topic without the prefix).
        registry.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // STOMP clients should use <your server address>/ws to connect to this server.
        registry.addEndpoint("/ws")
        // Optional: setup a custom error handler for mapping exceptions to your own ErrorMessage.
        // The default will use the Throwable.message as the message of a standard error response.
        registry.setErrorHandler(WebSocketErrorHandler())
    }
}
