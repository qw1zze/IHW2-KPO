package ru.restaurant.features.payOrder

import kotlinx.serialization.Serializable

@Serializable
data class PayOrderRemote(
    val token: String
)