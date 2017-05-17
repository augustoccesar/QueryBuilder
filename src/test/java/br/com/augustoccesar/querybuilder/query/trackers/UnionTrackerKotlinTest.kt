package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class UnionTrackerKotlinTest {
    @Test
    fun shouldBuildCorrectUnion() {
        val selectBuilder = SelectBuilder().select("{u}name").from("users{u}")

        val unionTracker = UnionTracker()
        unionTracker.addUnion(selectBuilder)

        assertEquals(" UNION ( SELECT u.name AS u_name FROM users u ) ", unionTracker.build())
    }

    @Test
    fun shouldBuildCorrectMultipleUnion() {
        val selectBuilder = SelectBuilder().select("{u}name").from("users{u}")
        val selectBuilder2 = SelectBuilder().select("{c}first_name").from("clients{c}")

        val unionTracker = UnionTracker()
        unionTracker.addUnion(selectBuilder)
        unionTracker.addUnionAll(selectBuilder2)

        assertEquals(" UNION ( SELECT u.name AS u_name FROM users u ) UNION ALL ( SELECT c.first_name AS c_first_name FROM clients c ) ", unionTracker.build())
    }
}