package ru.restaurant.states

import io.ktor.server.application.*
import ru.restaurant.database.orders.OrderDTO
import ru.restaurant.database.orders.Orders
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CookingState(private val order: OrderDTO) {
    private val locker = ReentrantLock()
    private fun cookingThread() {
        var totalTiime: Int = order.totalTime

        locker.withLock {
            println(totalTiime)
            Thread.sleep(totalTiime.toLong() * 1000)
        }
        locker.withLock {
            Orders.finishCooking(order.id)
            val ord = Orders.findCookedOrder(order.id)

            if (ord != null) {
                println("Order is ready")
            }
        }
    }

    fun startCook() {
        val thread = Thread {
            cookingThread()
        }
        thread.isDaemon = true
        thread.start()
    }
}