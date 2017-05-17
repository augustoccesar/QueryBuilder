package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class UnionKotlinTest {
    @Test
    fun shouldBuildCorrectUnion() {
        val selectBuilder = SelectBuilder().select("{u}name").from("users{u}")

        val union = Union.union(selectBuilder)

        assertEquals(Union.Type.UNION, union.type)
        assertEquals(" UNION ( SELECT u.name AS u_name FROM users u ) ", union.build())
    }

    @Test
    fun shouldBuildCorrectUnionAll() {
        val selectBuilder = SelectBuilder().select("{u}name").from("users{u}")

        val union = Union.unionAll(selectBuilder)

        assertEquals(Union.Type.UNION_ALL, union.type)
        assertEquals(" UNION ALL ( SELECT u.name AS u_name FROM users u ) ", union.build())
    }
}