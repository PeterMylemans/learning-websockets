package com.github.petermylemans.learning.websockets

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler

/**
 * Optional: example of an error handler to map exceptions to custom error message responses.
 *
 * @see WebSocketConfig
 */
class WebSocketErrorHandler : StompSubProtocolErrorHandler() {

    private val objectMapper = ObjectMapper()

    data class ErrorMessageVO(
        val code: String,
        val message: String
    )

    override fun handleClientMessageProcessingError(clientMessage: Message<ByteArray>?, ex: Throwable): Message<ByteArray>? {
        val accessor = StompHeaderAccessor.create(StompCommand.ERROR)
        val errorMessageVO = when (ex.cause) {
            is AccessDeniedException -> ErrorMessageVO(
                "access_denied",
                ex.cause!!.localizedMessage
            )
            else -> ErrorMessageVO("service_error", ex.localizedMessage)
        }
        accessor.message = errorMessageVO.message
        accessor.contentType = MediaType.APPLICATION_JSON

        var clientHeaderAccessor: StompHeaderAccessor? = null
        if (clientMessage != null) {
            clientHeaderAccessor = MessageHeaderAccessor.getAccessor(
                clientMessage,
                StompHeaderAccessor::class.java
            )
            if (clientHeaderAccessor != null) {
                val receiptId = clientHeaderAccessor.receipt
                if (receiptId != null) {
                    accessor.receiptId = receiptId
                }
            }
        }

        return handleInternal(accessor, objectMapper.writeValueAsBytes(errorMessageVO), ex, clientHeaderAccessor)
    }
}
