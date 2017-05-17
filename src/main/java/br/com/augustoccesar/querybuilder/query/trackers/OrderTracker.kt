package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import br.com.augustoccesar.querybuilder.query.Order
import java.util.*

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
class OrderTracker : Buildable {
    private val orders = ArrayList<Order>()

    fun addOrder(order: Order): OrderTracker {
        this.orders.add(order)
        return this
    }

    fun addOrders(vararg orders: Order): OrderTracker {
        this.orders.addAll(Arrays.asList(*orders))
        return this
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        for (i in this.orders.indices) {
            stringBuilder.append(this.orders[i].build())
            if (i < this.orders.size - 1) {
                stringBuilder.append(CommonStrings.COMMA)
            }
        }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    override fun shouldBuild(): Boolean {
        return orders.size > 0
    }
}
