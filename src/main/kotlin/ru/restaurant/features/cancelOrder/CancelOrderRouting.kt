package ru.restaurant.features.cancelOrder

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCancelOrderRouting() {
    routing {
        post("/cancelOrder") {
            val cancelOrder = CancelOrderController(call)
            cancelOrder.cancel()
        }
    }
}