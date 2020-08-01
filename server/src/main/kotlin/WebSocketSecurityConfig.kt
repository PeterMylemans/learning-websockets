package com.github.petermylemans.learning.websockets

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer

/**
 * Optional: example on how to use spring-security-messaging to apply security criteria when clients subscribe to topics.
 */
@Configuration
class WebSocketSecurityConfig : AbstractSecurityWebSocketMessageBrokerConfigurer() {

    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
        with(messages) {
            // Everybody is allowed to subscribe to /topic/greetings
            simpSubscribeDestMatchers("/topic/greetings").permitAll()

            // When clients try to subscribe to /topic/user-greetings, the need to have the role "USER".
            // After subscription is successful, the clients will continue to receive any messages as long as their session and subscription remain active.
            // When clients reconnect (after e.g. timeout or connection loss), this is re-evaluated.
            simpSubscribeDestMatchers("/topic/user-greetings").hasRole("USER")
        }
    }

    override fun sameOriginDisabled() = true
}
