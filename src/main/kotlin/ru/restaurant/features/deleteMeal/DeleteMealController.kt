package ru.restaurant.features.deleteMeal

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import ru.restaurant.database.meals.MealDTO
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import ru.restaurant.features.login.LoginRecieveRemote
import java.util.*

class DeleteMealController(private val call: ApplicationCall) {

    suspend fun deleteMeal() {
        val recieve = call.receive<DeleteMealRemote>()
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
            call.respond(HttpStatusCode.BadRequest, message = "Meal is not exists")
            return
        }


        Meals.delete(recieve.name)

        call.respondText("Meal deleted")
    }
}