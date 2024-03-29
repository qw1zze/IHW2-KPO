package ru.restaurant.database.meals

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.restaurant.database.orders.Orders

object Meals: Table("meals") {
    private val name = Meals.varchar("name", 30)
    private val price = Meals.integer("price")
    private val time = Meals.integer("time")
    private val count = Meals.integer("count")

    fun insert(mealDTO: MealDTO) {
        transaction {
            Meals.insert {
                it[name] = mealDTO.name
                it[price] = mealDTO.price
                it[time] = mealDTO.time
                it[count] = mealDTO.count
            }
        }
    }

    fun delete(name: String) {
        transaction {
            Meals.deleteWhere { Meals.name eq name }
        }
    }


    fun getMeals() : MutableMap<String, Int> {
        val meals: MutableMap<String, Int> = mutableMapOf()
        return transaction {
            for (meal in Meals.selectAll().iterator()) {
                meals[meal[Meals.name]] = meal[Meals.price]
            }
            meals
        }
    }

    fun updateCount(name: String, count: Int) {
        transaction {
            Meals.update({Meals.name eq name}) {
                it[Meals.count] = count
            }
        }
    }

    fun updatePrice(name: String, price: Int) {
        transaction {
            Meals.update({Meals.name eq name}) {
                it[Meals.price] = price
            }
        }
    }

    fun updateTime(name: String, time: Int) {
        transaction {
            Meals.update({Meals.name eq name}) {
                it[Meals.time] = time
            }
        }
    }

    fun reserveMeal(meal: String) {
        transaction {
            val count = Meals.getByName(meal)!!.count
            Meals.update({Meals.name eq meal}) {
                it[Meals.count] = count - 1
            }
        }
    }

    fun getByName(name: String): MealDTO? {
        return try {
            transaction {
                val meal = Meals.selectAll().where(Meals.name eq name).first()
                MealDTO(
                    name = meal[Meals.name],
                    price = meal[Meals.price],
                    time = meal[Meals.time],
                    count = meal[Meals.count]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}