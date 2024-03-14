package ru.restaurant.features.sendOrder

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSendOrderRouting() {
    routing {
        post("/sendOrder") {
            val sendOrder = SendOrderController(call)
            sendOrder.send()
        }
    }
}