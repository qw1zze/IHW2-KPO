package ru.restaurant.models.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRecieveRemote(
    val login: String,
    val password: String,
    val isAdmin: Boolean
)

@Serializable
data class RegisterResponseRemote(
    val token: String
)
