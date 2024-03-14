package ru.restaurant.features.changeMealTime

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureChangeMealTimeRouting() {
    routing {
        post("/changeMealTime") {
            val changeMealController = ChangeMealTimeController(call)
            changeMealController.changeMeal()
        }
    }
}