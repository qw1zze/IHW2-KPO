package ru.restaurant.database.orders

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.restaurant.database.meals.MealDTO
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users

object Orders: Table("orders") {
    private val name = Orders.varchar("name" , 30)
    private val meals = Orders.array<String>("meals",  VarCharColumnType(120))
    private val totalTime = Orders.integer("totaltime")
    private val status = Orders.varchar("status", 30)

    fun insert(orderDTO: OrderDTO) {
        transaction {
            Orders.insert {
                it[Orders.name] = orderDTO.name
                it[Orders.meals] = orderDTO.meals
                it[Orders.totalTime] = orderDTO.totalTime
                it[Orders.status] = orderDTO.status
            }
        }
    }

    fun updateMeals(name:String, meal: MealDTO) {
        transaction {

            val order = findNewOrder(name)
            val meals = order!!.meals
            val totalTime = order.totalTime

            meals.add(meal.name)
            Orders.update({Orders.name eq name}) {
                it[Orders.meals] = meals
                it[Orders.totalTime] = totalTime + meal.time
            }
        }
    }

    fun findNewOrder(name: String): OrderDTO? {
        return try {
            transaction {
                val order = Orders.selectAll().where(Orders.name eq name).first()
                OrderDTO(
                    name = order[Orders.name],
                    meals = order[Orders.meals].toMutableList(),
                    totalTime = order[Orders.totalTime],
                    status = order[Orders.status]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

}