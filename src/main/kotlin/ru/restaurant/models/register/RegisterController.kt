package ru.restaurant.models.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.UserDto
import ru.restaurant.database.users.Users
import java.util.*

class RegisterController(val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerRecieveRemote = call.receive<RegisterRecieveRemote>()
        val userDTO = Users.getByLogin(registerRecieveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.BadRequest, "User is already exists")
        } else {
            val token = UUID.randomUUID().toString()

            Users.insert(
                UserDto(
                    id = UUID.randomUUID().toString(),
                    login = registerRecieveRemote.login,
                    password = registerRecieveRemote.password,
                    isAdmin = registerRecieveRemote.isAdmin
                )
            )

            Tokens.insert(
                TokenDTO(
                    id = UUID.randomUUID().toString(),
                    login = registerRecieveRemote.login,
                    token = token)
            )

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}