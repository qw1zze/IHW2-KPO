package ru.restaurant.database.orders

import ru.restaurant.database.meals.MealDTO

class OrderDTO (
    val name: String,
    val meals: MutableList<String>,
    val totalTime: Int,
    val status: String
)