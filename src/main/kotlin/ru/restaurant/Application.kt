package ru.restaurant

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.restaurant.features.addMeal.configureAddMealRouting
import ru.restaurant.features.addMealOrder.configureAddMealOrderRouting
import ru.restaurant.features.cancelOrder.configureCancelOrderRouting
import ru.restaurant.features.changeMealCount.configureChangeMealCountRouting
import ru.restaurant.features.changeMealPrice.configureChangeMealPriceRouting
import ru.restaurant.features.changeMealTime.configureChangeMealTimeRouting
import ru.restaurant.features.deleteMeal.configureDeleteMealRouting
import ru.restaurant.features.getMenu.configureGetMenuRouting
import ru.restaurant.features.login.configureLoginRouting
import ru.restaurant.features.logout.configureLogoutRouting
import ru.restaurant.features.payOrder.configurePayOrderRouting
import ru.restaurant.features.register.configureRegisterRouting
import ru.restaurant.features.sendOrder.configureSendOrderRouting
import ru.restaurant.plugins.*

fun main() {


    Database.connect("jdbc:postgresql://localhost:5432/restaurantdb", driver = "org.postgresql.Driver", user = "admin", password = "admin")

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureRegisterRouting()
    configureLoginRouting()
    configureAddMealRouting()
    configureDeleteMealRouting()
    configureChangeMealCountRouting()
    configureChangeMealPriceRouting()
    configureChangeMealTimeRouting()
    configureLogoutRouting()
    configureAddMealOrderRouting()
    configureSendOrderRouting()
    configureCancelOrderRouting()
    configurePayOrderRouting()
    configureGetMenuRouting()
    configureSerialization()
}
