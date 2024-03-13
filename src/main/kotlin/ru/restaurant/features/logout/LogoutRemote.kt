package ru.restaurant.features.logout

import kotlinx.serialization.Serializable

@Serializable
data class LogoutRecieveRemote(
    val token: String,
)