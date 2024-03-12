package ru.restaurant

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import ru.restaurant.models.login.configureLoginRouting
import ru.restaurant.models.register.configureRegisterRouting
import ru.restaurant.plugins.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
}
