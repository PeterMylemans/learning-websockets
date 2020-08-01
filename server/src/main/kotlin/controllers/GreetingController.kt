package com.github.petermylemans.learning.websockets.controllers

import org.apache.logging.log4j.LogManager
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import kotlin.random.Random

@Controller
class GreetingController(
    val messageTemplate: SimpMessageSendingOperations
) {

    data class Greeting(val content: String)

    companion object {
        private val logger = LogManager.getLogger()
    }

    @Scheduled(fixedRate = 1000)
    fun greeting() {
        val nextInt = Random.nextInt(1, Int.MAX_VALUE)
        logger.trace("Sending message to user {}", nextInt)

        // Use the message template to send a message on a topic the client.
        // The message is automatically converted to JSON by the message converters managed by Spring.
        messageTemplate.convertAndSend("/topic/greetings", Greeting("Hello, user $nextInt!"))
    }
}
