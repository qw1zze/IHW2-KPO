package ru.restaurant.features.deleteMeal

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureDeleteMealRouting() {
    routing {
        post("/deleteMeal") {
            val deleteMealController = DeleteMealController(call)
            deleteMealController.deleteMeal()
        }
    }
}