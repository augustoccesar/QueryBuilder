package br.com.augustoccesar.querybuilder.query

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class TableKotlinTest {
    @Test
    fun shouldBeAbleToCreateUsingMarkdown() {
        val table = Table.fromMarkdown("users{u}")

        assertEquals("users", table.name)
        assertEquals("u", table.alias)
    }

    @Test
    fun shouldBeAbleToCreateUsingEmptyMarkdown() {
        val table = Table.fromMarkdown("users{_}")

        assertEquals("users", table.name)
        assertNull(table.alias)
    }

    @Test
    fun shouldBeAbleToCreateWithoutMarkdown() {
        val table = Table.fromMarkdown("users u")

        assertEquals("users", table.name)
        assertEquals("u", table.alias)
    }

    @Test
    fun shouldBeAbleToCreateWithoutMarkdownWithoutAlias() {
        val table = Table.fromMarkdown("users")

        assertEquals("users", table.name)
        assertNull(table.alias)
    }
}