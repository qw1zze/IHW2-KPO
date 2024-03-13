package ru.restaurant.features.changeMealCount

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureChangeMealRouting() {
    routing {
        post("/changeMeal") {
            val changeMealController = ChangeMealController(call)
            changeMealController.changeMeal()
        }
    }
}