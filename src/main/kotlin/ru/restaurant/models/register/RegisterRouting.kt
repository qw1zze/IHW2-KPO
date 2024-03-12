package ru.restaurant.models.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.restaurant.cache.InMemoryCache
import ru.restaurant.cache.TokenCache
import ru.restaurant.models.login.LoginResponseRemote
import java.util.*

fun Application.configureRegisterRouting() {
    routing {
        post("/register") {
            val recieve = call.receive<RegisterRecieveRemote>()

            if (InMemoryCache.userList.map { it.login }.contains(recieve.login)) {
                call.respond(HttpStatusCode.BadRequest, "User is already exists")
            }

            val token = UUID.randomUUID().toString()
            InMemoryCache.userList.add(recieve)
            InMemoryCache.token.add(TokenCache(login = recieve.login, token = token))

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}