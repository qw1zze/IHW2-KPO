package ru.restaurant.models.login

import com.sun.org.apache.xpath.internal.operations.Bool
import kotlinx.serialization.Serializable


@Serializable
data class LoginRecieveRemote(
    val login: String,
    val password: String,
    //val isAdmin: Boolean
)

@Serializable
data class LoginResponseRemote(
    val token: String
)