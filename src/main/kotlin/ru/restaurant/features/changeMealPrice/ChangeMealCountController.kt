package ru.restaurant.features.changeMealPrice

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users

class ChangeMealPriceController(private val call: ApplicationCall) {

    suspend fun changeMeal() {
        val recieve = call.receive<ChangeMealPriceRemote>()
        val login = Tokens.getByToken(recieve.token)

        if (login == null) {
            call.respond(HttpStatusCode.BadRequest, message = "Incorrect token")
            return
        }

        val isAdmin = Users.isAdmin(login.toString())

        if (!isAdmin) {
            call.respond(HttpStatusCode.BadRequest, message = "User is not admin")
            return
        }

        val meal = Meals.getByName(recieve.name)

        if (meal == null) {
            call.respond(HttpStatusCode.BadRequest, message = "Meal is not exist")
            return
        }

        if (recieve.price < 0) {
            call.respond(HttpStatusCode.BadRequest, message = "Incorrect data")
            return
        }

        Meals.updatePrice(name = recieve.name, price = recieve.price)

        call.respondText("Price changed")
    }
}