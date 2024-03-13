package ru.restaurant.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun loginUser() {
        val recieve = call.receive<LoginRecieveRemote>()
        val userDTO = Users.getByLogin(recieve.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, message = "User not found")
        } else {
            if (userDTO.password == recieve.password) {
                val token = UUID.randomUUID().toString()

                Tokens.insert(
                    TokenDTO(
                        id = UUID.randomUUID().toString(),
                        login = recieve.login,
                        token = token)
                )

                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "InvalidPassword")
            }
        }
        call.respond(HttpStatusCode.BadRequest)
    }
}