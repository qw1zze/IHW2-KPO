package ru.restaurant.features.addMeal

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddMealRouting() {
    routing {
        post("/addMeal") {
            val addMealController = AddMealController(call)
            addMealController.addMeal()
        }
    }
}