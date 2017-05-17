package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import br.com.augustoccesar.querybuilder.query.Aggregation
import br.com.augustoccesar.querybuilder.query.Column
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class SelectTrackerKotlinTest {
    @Test
    fun shouldBuildIfCorrectColumnMarked() {
        val tracker = SelectTracker().addSelect("{u}name")

        val expect = " u.name AS u_name "
        assertEquals(expect, tracker.build())
    }

    @Test
    fun shouldBuildIfCorrectColumn() {
        val tracker = SelectTracker().addSelect(Column("u", "name", false))

        val expect = " u.name AS u_name "
        assertEquals(expect, tracker.build())
    }

    @Test
    fun shouldBuildIfCorrectSelectBuilder() {
        val selectBuilder = SelectBuilder("qb").select("{u}name").from("users{u}")
        val tracker = SelectTracker().addSelect(selectBuilder)

        val expect = " ( SELECT u.name AS u_name FROM users u ) AS qb "
        assertEquals(expect, tracker.build())
    }

    @Test
    fun shouldBuildWithColumnAndSelectBuilder() {
        val selectBuilder = SelectBuilder("qb").select("{u}name").from("users{u}")
        val column = Column("u", "name", false)

        val tracker = SelectTracker().addSelect(selectBuilder)
        tracker.addSelect(column)

        val expect = " u.name AS u_name , ( SELECT u.name AS u_name FROM users u ) AS qb "
        assertEquals(expect, tracker.build())
    }

    @Test
    fun shouldBuildWithColumnAndSelectBuilderAndAggregation() {
        val selectBuilder = SelectBuilder("qb").select("{u}name").from("users{u}")
        val column = Column("u", "name", false)
        val agg = Aggregation.count("{u}id")

        val tracker = SelectTracker().addSelect(selectBuilder)
        tracker.addSelect(column)
        tracker.addSelect(agg)

        val expect = " u.name AS u_name , COUNT ( u.id ) AS count_u_id , ( SELECT u.name AS u_name FROM users u ) AS qb "
        assertEquals(expect, tracker.build())
    }
}