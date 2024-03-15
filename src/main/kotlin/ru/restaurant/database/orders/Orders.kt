package ru.restaurant.database.orders

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.restaurant.database.meals.MealDTO
import ru.restaurant.database.meals.Meals
import ru.restaurant.database.tokens.TokenDTO
import ru.restaurant.database.tokens.Tokens
import ru.restaurant.database.users.Users
import java.util.*

object Orders: Table("orders") {
    private val id = Orders.varchar("id" , 50)
    private val name = Orders.varchar("name" , 30)
    private val meals = Orders.array<String>("meals",  VarCharColumnType(120))
    private val totalTime = Orders.integer("totaltime")
    private val status = Orders.varchar("status", 30)

    fun insert(orderDTO: OrderDTO) {
        transaction {
            Orders.insert {
                it[Orders.id] = orderDTO.id
                it[Orders.name] = orderDTO.name
                it[Orders.meals] = orderDTO.meals
                it[Orders.totalTime] = orderDTO.totalTime
                it[Orders.status] = orderDTO.status
            }
        }
    }

    fun delete(orderDTO: OrderDTO) {
        transaction {
            Orders.deleteWhere {Orders.id eq orderDTO.id}
        }
    }

    fun updateMeals(name:String, meal: MealDTO) {
        transaction {

            val order = findNewOrder(name)
            val meals = order!!.meals
            val totalTime = order.totalTime

            meals.add(meal.name)
            Orders.update({Orders.name eq name and(Orders.status eq "New")}) {
                it[Orders.meals] = meals
                it[Orders.totalTime] = totalTime + meal.time
            }
        }
    }

    fun startCooking(name:String) {
        transaction {
            Orders.update({Orders.name eq name and(Orders.status eq "New")}) {
                it[Orders.status] = "Cooking"
            }
        }
    }

    fun finishCooking(id:String) {
        transaction {
            Orders.update({Orders.id eq id and(Orders.status eq "Cooking")}) {
                it[Orders.status] = "Cooked"
            }
        }
    }

    fun payOrder(id:String) {
        transaction {
            Orders.update({Orders.id eq id and(Orders.status eq "Cooked")}) {
                it[Orders.status] = "Payed"
            }
        }
    }

    fun findNewOrder(name: String): OrderDTO? {
        return try {
            transaction {
                val order = Orders.selectAll().andWhere { Orders.name eq name }.andWhere { Orders.status eq "New" }.first()
                OrderDTO(
                    id = order[Orders.id],
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

    fun findCookingOrder(name: String): OrderDTO? {
        return try {
            transaction {
                val order = Orders.selectAll().andWhere { Orders.name eq name }.andWhere { Orders.status eq "Cooking" }.first()
                OrderDTO(
                    id = order[Orders.id],
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

    fun findCookedOrder(name: String): OrderDTO? {
        return try {
            transaction {
                val order = Orders.selectAll().andWhere { Orders.name eq name }.andWhere { Orders.status eq "Cooked" }.first()
                OrderDTO(
                    id = order[Orders.id],
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
    fun findCookedOrderById(id: String): OrderDTO? {
        return try {
            transaction {
                val order = Orders.selectAll().andWhere { Orders.id eq id }.andWhere { Orders.status eq "Cooked" }.first()
                OrderDTO(
                    id = order[Orders.id],
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

    fun getOrders() : MutableList<OrderDTO> {
        val orders: MutableList<OrderDTO> = mutableListOf()
        return transaction {
            for (order in Orders.selectAll().iterator()) {
                orders.add(
                    OrderDTO(
                        id = order[Orders.id],
                        name = order[Orders.name],
                        meals = order[Orders.meals].toMutableList(),
                        totalTime = order[Orders.totalTime],
                        status = order[Orders.status],
                ))
            }
            orders
        }
    }

}