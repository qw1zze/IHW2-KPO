package ru.restaurant.features.sendOrder

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.orders.OrderDTO
import ru.restaurant.database.orders.Orders
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import ru.restaurant.states.CookingState
import kotlin.math.log

class SendOrderController(private val call: ApplicationCall) {

    suspend fun send() {
        val recieve = call.receive<SendOrderRemote>()
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

        val order = Orders.findNewOrder(login)

        if (order == null) {

            call.respondText("User does not have new order")
            return
        }

        val cookingOrder = Orders.findCookingOrder(login)
        if (cookingOrder != null) {
            call.respond(HttpStatusCode.BadRequest, message = "There is cooking order")
            return
        }

        val cookedOrder = Orders.findCookedOrder(login)
        if (cookedOrder != null) {
            call.respond(HttpStatusCode.BadRequest, message = "There is order to pay")
            return
        }

        Orders.startCooking(login)

        val thread = CookingState(order)
        thread.startCook()

        call.respond(SendOrderResponse(meals = order.meals, totalTime = order.totalTime))
    }
}