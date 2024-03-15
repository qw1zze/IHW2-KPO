package ru.restaurant.features.getMenu

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.orders.Orders
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.UserDto
import ru.restaurant.database.users.Users
import java.util.*

class GetMenuController(val call: ApplicationCall) {

    suspend fun get() {
        call.respond(GetMenuResponce(Meals.getMeals()))
    }
}