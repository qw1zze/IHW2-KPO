package ru.restaurant.cache

import ru.restaurant.models.register.RegisterRecieveRemote

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterRecieveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()

}