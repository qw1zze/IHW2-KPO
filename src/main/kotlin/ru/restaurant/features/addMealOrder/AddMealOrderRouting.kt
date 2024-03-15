package ru.restaurant.features.addMealOrder

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureAddMealOrderRouting() {
    routing {
        post("/addMealOrder") {
            val addMealController = AddMealOrderController(call)
            addMealController.addMeal()
        }
    }
}