package ru.restaurant.features.getMenu

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGetMenuRouting() {
    routing {
        get("/getMenu") {
            val getMenu = GetMenuController(call)
            getMenu.get()
        }
    }
}