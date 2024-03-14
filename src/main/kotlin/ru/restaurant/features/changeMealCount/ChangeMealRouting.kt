package ru.restaurant.features.changeMealCount

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureChangeMealCountRouting() {
    routing {
        post("/changeMealCount") {
            val changeMealController = ChangeMealCountController(call)
            changeMealController.changeMeal()
        }
    }
}