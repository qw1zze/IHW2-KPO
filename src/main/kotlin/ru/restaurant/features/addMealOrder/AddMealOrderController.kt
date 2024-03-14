package ru.restaurant.features.addMealOrder

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.meals.MealDTO
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.orders.OrderDTO
import ru.restaurant.database.orders.Orders
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users

class AddMealOrderController(private val call: ApplicationCall) {

    suspend fun addMeal() {
        val recieve = call.receive<AddMealOrderRemote>()
        val login = Tokens.getByToken(recieve.token)

        if (login == null) {
            call.respond(HttpStatusCode.BadRequest, message = "Incorrect token")
            return
        }

        val isAdmin = Users.isAdmin(login.toString())

        if (isAdmin) {
            call.respond(HttpStatusCode.BadRequest, message = "User is admin, login to usual user")
            return
        }

        val meal = Meals.getByName(recieve.meal)

        if (meal == null) {
            call.respond(HttpStatusCode.BadRequest, message = "There are not that meal in menu")
            return
        }

        val order = Orders.findNewOrder(login)

        if (meal.count <= 0) {
            call.respondText("Meal is not available")
            return
        }

        if (order != null) {

            Orders.updateMeals(login, meal)
            Meals.reserveMeal(meal.name)
            call.respondText("Meal added to order")
            return
        }

        Orders.insert(
            OrderDTO(
                name = login,
                meals = mutableListOf(recieve.meal),
                totalTime = meal.time,
                status = "New"
            )
        )

        Meals.reserveMeal(meal.name)

        call.respondText("Meal added to order")
    }
}