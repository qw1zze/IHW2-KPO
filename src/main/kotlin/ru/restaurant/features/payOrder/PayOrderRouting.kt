package ru.restaurant.features.payOrder

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configurePayOrderRouting() {
    routing {
        post("/payOrder") {
            val payOrder = PayOrderController(call)
            payOrder.pay()
        }
    }
}