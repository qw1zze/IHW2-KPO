package ru.restaurant.features.addMeal

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

class AddMealController(private val call: ApplicationCall) {

    suspend fun addMeal() {
        val recieve = call.receive<AddMealRemote>()
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

        if (meal != null) {
            call.respond(HttpStatusCode.BadRequest, message = "Meal already exists")
            return
        }

        if (recieve.price < 0 || recieve.time <= 0 || recieve.count < 0) {
            call.respond(HttpStatusCode.BadRequest, message = "Incorrect data")
            return
        }

        Meals.insert(
            MealDTO(
                name = recieve.name,
                price = recieve.price,
                time = recieve.time,
                count = recieve.count
            )
        )

        call.respondText("Meal added")
    }
}