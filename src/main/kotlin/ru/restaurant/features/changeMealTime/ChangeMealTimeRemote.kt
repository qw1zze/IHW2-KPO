package ru.restaurant.features.changeMealTime

import kotlinx.serialization.Serializable

@Serializable
data class ChangeMealTimeRemote(
    val token: String,
    val name: String,
    val time: Int
)