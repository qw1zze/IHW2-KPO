package ru.restaurant.features.getOrders

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.orders.Orders
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import ru.restaurant.features.payOrder.PayOrderRemote

class GetOrdersController(val call: ApplicationCall) {

    suspend fun get() {

        val recieve = call.receive<GetOrdersRemote>()
        val login = Tokens.getByToken(recieve.token)

        if (login == null) {
            call.respond(HttpStatusCode.BadRequest, message = "Incorrect token")
            return
        }

        val isAdmin = Users.isAdmin(login.toString())

        if (!isAdmin) {
            call.respond(HttpStatusCode.BadRequest, message = "User is not admin, login to admin")
            return
        }

        call.respond(GetOrdersResponce(Orders.getOrders()))
    }
}