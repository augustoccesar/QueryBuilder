package br.com.augustoccesar.querybuilder.query

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class OrderKotlinTest {
    @Test
    fun shouldBuildIfCorrectMarkedUsingBy() {
        val order = Order.by("{u}name", Order.Type.ASC)

        assertEquals("name", order.column!!.name)
        assertEquals(Order.Type.ASC, order.type)

        val expected = " u.name ASC "
        assertEquals(expected, order.build())
    }

    @Test
    fun shouldBuildIfCorrectMarkedUsingAsc() {
        val order = Order.asc("{u}name")

        assertEquals("name", order.column!!.name)
        assertEquals(Order.Type.ASC, order.type)

        val expected = " u.name ASC "
        assertEquals(expected, order.build())
    }

    @Test
    fun shouldBuildIfCorrectMarkedUsingDesc() {
        val order = Order.desc("{u}name")

        assertEquals("name", order.column!!.name)
        assertEquals(Order.Type.DESC, order.type)

        val expected = " u.name DESC "
        assertEquals(expected, order.build())
    }
}