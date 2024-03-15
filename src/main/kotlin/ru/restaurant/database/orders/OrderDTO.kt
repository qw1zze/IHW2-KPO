package ru.restaurant.database.orders

import kotlinx.serialization.Serializable

@Serializable
class OrderDTO (
    val id: String,
    val name: String,
    val meals: MutableList<String>,
    val totalTime: Int,
    val status: String
)