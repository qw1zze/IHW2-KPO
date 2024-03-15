package ru.restaurant.features.getOrders

import kotlinx.serialization.Serializable
import ru.restaurant.database.orders.OrderDTO

@Serializable
data class GetOrdersRemote(
    val token: String
)

@Serializable
data class GetOrdersResponce(
    val orders: MutableList<OrderDTO>
)
