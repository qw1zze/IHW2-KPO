package ru.restaurant.database.tokens

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.users.UserDto
import ru.restaurant.database.users.Users

object Tokens: Table("tokens") {
    private val id = Tokens.varchar("id", 50)
    private val login = Tokens.varchar("login", 30)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun delete(token: String) {
        transaction {
            Tokens.deleteWhere { Tokens.token eq token }
        }
    }

    fun inSystem(login: String):Boolean {
        return try {
            transaction {
                val token = Tokens.selectAll().where(Tokens.login eq login).first()
                true;
            }
        } catch (e: Exception) {
            false
        }
    }

    fun getByToken(token: String) : String? {
        return try {
            transaction {
                val login = Tokens.selectAll().where(Tokens.token eq token).first()[Tokens.login]
                login;
            }
        } catch (e: Exception) {
            null
        }
    }

}