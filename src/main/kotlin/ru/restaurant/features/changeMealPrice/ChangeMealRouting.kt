package ru.restaurant.features.changeMealPrice

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureChangeMealPriceRouting() {
    routing {
        post("/changeMealPrice") {
            val changeMealController = ChangeMealPriceController(call)
            changeMealController.changeMeal()
        }
    }
}