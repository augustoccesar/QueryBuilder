package br.com.augustoccesar.querybuilder.query

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class LimitKotlinTest {
    @Test
    fun shouldCreateIfValidValue() {
        val limit = Limit(5)

        assertEquals(5, limit.value.toLong())
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnInvalidValue() {
        Limit(-1)
    }
}