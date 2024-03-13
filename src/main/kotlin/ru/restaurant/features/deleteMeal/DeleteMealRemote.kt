package ru.restaurant.features.deleteMeal

import kotlinx.serialization.Serializable

@Serializable
data class DeleteMealRemote(
    val token: String,
    val name: String,
)