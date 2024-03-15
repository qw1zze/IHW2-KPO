package ru.restaurant.features.getMenu

import kotlinx.serialization.Serializable
import java.util.Dictionary

@Serializable
data class GetMenuResponce(
    val meals: Map<String, Int>
)
