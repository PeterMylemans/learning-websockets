package com.github.petermylemans.learning.websockets.controllers

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

/**
 * Echoes a message back to the client.
 *
 * Messages are received on the destination /app/echo.
 * Responses are sent to the destination /topic/messages.
 *
 * @see com.github.petermylemans.learning.websockets.WebSocketConfig for app destination prefix
 */
@Controller
class EchoController {

    data class EchoRequest(
        val message: String
    )

    data class EchoResponse(
        val message: String
    )

    @MessageMapping("/echo") // Listen for messages with destination <destinationPrefix>/echo
    @SendTo("/topic/messages") // Sends the EchoResponse back to the client on the topic "/topic/messages".
    fun onMessageReceived(request: EchoRequest) = EchoResponse("Echoing back ${request.message}")
}
