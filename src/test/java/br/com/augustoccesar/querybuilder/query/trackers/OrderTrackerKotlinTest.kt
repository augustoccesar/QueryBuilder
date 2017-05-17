package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.query.Order
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class OrderTrackerKotlinTest {
    @Test
    fun shouldCreateWithOneOrder() {
        val orderTracker = OrderTracker()
        orderTracker.addOrder(Order.asc("{u}name"))

        val expected = " u.name ASC "
        assertEquals(expected, orderTracker.build())
    }

    @Test
    fun shouldCreateWithMultipleOrderOneByOne() {
        val orderTracker = OrderTracker()
        orderTracker.addOrder(Order.asc("{u}name"))
        orderTracker.addOrder(Order.desc("{u}id"))

        val expected = " u.name ASC , u.id DESC "
        assertEquals(expected, orderTracker.build())
    }

    @Test
    fun shouldCreateWithMultipleDirectly() {
        val orderTracker = OrderTracker()
        orderTracker.addOrders(
                Order.asc("{u}name"),
                Order.desc("{u}id")
        )

        val expected = " u.name ASC , u.id DESC "
        assertEquals(expected, orderTracker.build())
    }
}