package ru.restaurant.features.changeMealCount

import kotlinx.serialization.Serializable

@Serializable
data class ChangeMealRemote(
    val token: String,
    val name: String,
    val count: Int
)