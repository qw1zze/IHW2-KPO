package ru.restaurant.features.getOrders

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetOrdersRouting() {
    routing {
        post("/getOrders") {
            val getOrders = GetOrdersController(call)
            getOrders.get()
        }
    }
}