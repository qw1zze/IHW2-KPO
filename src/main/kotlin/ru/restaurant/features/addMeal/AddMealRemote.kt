package ru.restaurant.features.addMeal

import kotlinx.serialization.Serializable

@Serializable
data class AddMealRemote(
    val token: String,
    val name: String,
    val price: Int,
    val time: Int,
    val count: Int
)