package ru.restaurant.features.cancelOrder

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.orders.Orders
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import ru.restaurant.states.CookingState

class CancelOrderController(private val call: ApplicationCall) {

    suspend fun cancel() {
        val recieve = call.receive<CancelOrderRemote>()
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

        val cookingOrder = Orders.findCookingOrder(login)
        if (cookingOrder == null) {
            call.respond(HttpStatusCode.BadRequest, message = "There is no cooking order")
            return
        }

        Orders.delete(cookingOrder)

        call.respondText("Order was canceled")
    }
}