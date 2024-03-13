package ru.restaurant.database.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {
    private val id = Users.varchar("id" , 50)
    private val login = Users.varchar("login", 30)
    private val password = Users.varchar("password", 30)
    private val isAdmin = Users.bool("isadmin")

    fun insert(userDto: UserDto) {
        transaction {
            Users.insert {
                it[id] = userDto.id
                it[login] = userDto.login
                it[password] = userDto.password
                it[isAdmin] = userDto.isAdmin
            }
        }
    }

    fun getById(id: String): UserDto {
        val user = Users.select {Users.id.eq(id)}.single()
        return UserDto(
            id = user[Users.id],
            login = user[Users.login],
            password = user[Users.password],
            isAdmin = user[Users.isAdmin]
        )
    }

    fun getByLogin(login: String): UserDto? {
        return try {
            transaction {
                val user = Users.selectAll().where(Users.login eq login).first()
                UserDto(
                    id = user[Users.id],
                    login = user[Users.login],
                    password = user[Users.password],
                    isAdmin = user[Users.isAdmin]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}