package ru.restaurant.database.users

class UserDto (
    val id: String,
    val login: String,
    val password: String,
    val isAdmin: Boolean
)