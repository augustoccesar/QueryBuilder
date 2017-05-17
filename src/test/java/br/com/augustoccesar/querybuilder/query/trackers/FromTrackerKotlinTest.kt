package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.query.Table
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class FromTrackerKotlinTest {
    @Test
    fun shouldBuildIfCorrectTable() {
        val table = Table("users", "u")

        val expected = " users u "
        val result = FromTracker().addTables(table).build()

        assertEquals(expected, result)
    }

    @Test
    fun shouldBuildIfCorrectMarkedTable() {
        val markedTable = "users{u}"

        val expected = " users u "
        val result = FromTracker().addMarkedTables(markedTable).build()

        assertEquals(expected, result)
    }
}