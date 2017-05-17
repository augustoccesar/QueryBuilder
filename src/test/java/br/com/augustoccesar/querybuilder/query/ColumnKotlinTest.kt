package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class ColumnKotlinTest {
    @Test
    fun shouldCreateWithTableAlias() {
        val column = Column.fromMarkdown("{u}name")

        assertEquals("name", column.name)
        assertEquals("u", column.prefix)
        assertEquals("u_name", column.alias)
    }

    @Test
    fun shouldCreateWithTableAliasAndCustomAlias() {
        val column = Column.fromMarkdown("{u}name{custom_alias}")

        assertEquals("name", column.name)
        assertEquals("u", column.prefix)
        assertEquals("custom_alias", column.alias)
    }

    @Test
    fun shouldCreateWithTableAliasAndWithAliasEmptyMark() {
        val column = Column.fromMarkdown("{u}name{_}")

        assertEquals("name", column.name)
        assertEquals("u", column.prefix)
        assertNull(column.alias)
    }

    @Test
    fun shouldCreateWithDistinctMark() {
        val column = Column.fromMarkdown("{u}*name")

        assertEquals("name", column.name)
        assertEquals("u", column.prefix)
        assertEquals("u_name", column.alias)
        assertTrue(column.isDistinct())
    }

    @Test
    fun shouldCreateWithTableAliasEmptyMark() {
        val column = Column.fromMarkdown("{_}name")

        assertEquals("name", column.name)
        assertNull(column.prefix)
        assertNull(column.alias)
        assertFalse(column.isDistinct())
    }

    @Test
    fun shouldCreateWithTableAliasEmptyMarkAndCustomAlias() {
        val column = Column.fromMarkdown("{_}name{custom_name}")

        assertEquals("name", column.name)
        assertNull(column.prefix)
        assertEquals("custom_name", column.alias)
        assertFalse(column.isDistinct())
    }

    @Test(expected = InvalidPattern::class)
    fun shouldntCreateWithoutTableAliasOrEmptyMark() {
        Column.fromMarkdown("name")
    }
}