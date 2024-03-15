package ru.restaurant.features.payOrder

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.orders.Orders
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import ru.restaurant.states.CookingState

class PayOrderController(private val call: ApplicationCall) {

    suspend fun pay() {
        val recieve = call.receive<PayOrderRemote>()
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

        val cookedOrder = Orders.findCookedOrder(login)
        if (cookedOrder == null) {
            call.respond(HttpStatusCode.BadRequest, message = "There is no order to pay")
            return
        }

        Orders.payOrder(cookedOrder.id)

        call.respondText("Order was payed")
    }
}