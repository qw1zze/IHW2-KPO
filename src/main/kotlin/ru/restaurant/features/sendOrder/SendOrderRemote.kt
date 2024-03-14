package ru.restaurant.features.sendOrder

import kotlinx.serialization.Serializable

@Serializable
data class SendOrderRemote(
    val token: String,
)

@Serializable
data class SendOrderResponse(
    val meals: MutableList<String>,
    val totalTime: Int
)