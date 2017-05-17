package br.com.augustoccesar.querybuilder.query

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class JoinKotlinTest {
    @Test
    fun shouldCreateWithNestedMethodsAndSeparatedOnClause() {
        val join = Join(Join.LEFT).table("user_profile{up}").on("{u}id", "{up}user_id")

        val expect = " LEFT JOIN user_profile up ON u.id = up.user_id "
        assertEquals(expect, join.build())
    }

    @Test
    fun shouldCreateWithNestedMethodsAndCollapsedOnClause() {
        val join = Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id")

        val expect = " LEFT JOIN user_profile up ON u.id = up.user_id "
        assertEquals(expect, join.build())
    }

    @Test
    fun shouldCreateWithConstructorAndSeparatedOnClause() {
        val join = Join(Join.INNER, "user_profile{up}", "{u}id", "{up}user_id")

        val expect = " INNER JOIN user_profile up ON u.id = up.user_id "
        assertEquals(expect, join.build())
    }

    @Test
    fun shouldCreateWithConstructorAndCollapsedOnClause() {
        val join = Join(Join.INNER, "user_profile{up}", "{u}id = {up}user_id")

        val expect = " INNER JOIN user_profile up ON u.id = up.user_id "
        assertEquals(expect, join.build())
    }
}