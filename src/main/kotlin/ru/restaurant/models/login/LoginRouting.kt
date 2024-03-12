package ru.restaurant.models.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.restaurant.cache.InMemoryCache
import ru.restaurant.cache.TokenCache
import java.util.UUID

fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val recieve = call.receive<LoginRecieveRemote>()
            val first = InMemoryCache.userList.firstOrNull {it.login == recieve.login}

            if (first == null) {
                call.respond(HttpStatusCode.BadRequest, message = "User not found")
            } else {
                if (first.password == recieve.password) {
                    val token = UUID.randomUUID().toString()
                    InMemoryCache.token.add(TokenCache(login = recieve.login, token = token))
                    call.respond(LoginResponseRemote(token = token))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "InvalidPassword")
                }
            }
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
