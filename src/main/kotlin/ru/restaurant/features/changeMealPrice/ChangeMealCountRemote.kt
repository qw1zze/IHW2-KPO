package ru.restaurant.features.changeMealPrice

import kotlinx.serialization.Serializable

@Serializable
data class ChangeMealPriceRemote(
    val token: String,
    val name: String,
    val price: Int
)