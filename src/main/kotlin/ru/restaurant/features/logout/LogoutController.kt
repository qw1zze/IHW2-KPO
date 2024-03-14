package ru.restaurant.features.logout

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.deleteWhere
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.UserDto
import ru.restaurant.database.users.Users
import java.util.*
import kotlin.math.log

class LogoutController(val call: ApplicationCall) {

    suspend fun logoutUser() {
        val registerRecieveRemote = call.receive<LogoutRecieveRemote>()
        val login = Tokens.getByToken(registerRecieveRemote.token)

        if (login == null) {
            call.respond(HttpStatusCode.BadRequest, "User is not in system")
        } else {

            Tokens.delete(registerRecieveRemote.token)

            call.respondText("User logout")
        }
    }
}