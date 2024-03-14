package ru.restaurant.features.addMealOrder

import kotlinx.serialization.Serializable

@Serializable
data class AddMealOrderRemote(
    val token: String,
    val meal: String
)