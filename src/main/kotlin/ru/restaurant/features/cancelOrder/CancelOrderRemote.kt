package ru.restaurant.features.cancelOrder

import kotlinx.serialization.Serializable

@Serializable
data class CancelOrderRemote(
    val token: String,
)