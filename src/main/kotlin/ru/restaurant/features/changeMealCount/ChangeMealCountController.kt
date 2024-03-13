package ru.restaurant.features.changeMealCount

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users

class ChangeMealCountController(private val call: ApplicationCall) {

    suspend fun changeMeal() {
        val recieve = call.receive<ChangeMealCountRemote>()
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

        if (recieve.count < 0) {
            call.respond(HttpStatusCode.BadRequest, message = "Incorrect data")
            return
        }

        Meals.updateCount(name = recieve.name, count = recieve.count)

        call.respondText("Count changed")
    }
}