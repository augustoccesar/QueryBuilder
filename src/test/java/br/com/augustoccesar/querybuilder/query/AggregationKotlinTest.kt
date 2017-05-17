package br.com.augustoccesar.querybuilder.query

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class AggregationKotlinTest {
    @Test
    fun average() {
        val agg = Aggregation.average("{u}score")

        assertEquals(Aggregation.AVERAGE, agg.type)
        assertEquals("avg_u_score", agg.alias)
        assertEquals(" AVG ( u.score ) AS avg_u_score ", agg.build())

        assertEquals("score", agg.column!!.name)
        assertEquals("u", agg.column!!.prefix)
    }

    @Test
    fun count() {
        val agg = Aggregation.count("{u}id")

        assertEquals(Aggregation.COUNT, agg.type)
        assertEquals("count_u_id", agg.alias)
        assertEquals(" COUNT ( u.id ) AS count_u_id ", agg.build())

        assertEquals("id", agg.column!!.name)
        assertEquals("u", agg.column!!.prefix)
    }

    @Test
    fun max() {
        val agg = Aggregation.max("{u}score")

        assertEquals(Aggregation.MAX, agg.type)
        assertEquals("max_u_score", agg.alias)
        assertEquals(" MAX ( u.score ) AS max_u_score ", agg.build())

        assertEquals("score", agg.column!!.name)
        assertEquals("u", agg.column!!.prefix)
    }

    @Test
    fun min() {
        val agg = Aggregation.min("{u}score")

        assertEquals(Aggregation.MIN, agg.type)
        assertEquals("min_u_score", agg.alias)
        assertEquals(" MIN ( u.score ) AS min_u_score ", agg.build())

        assertEquals("score", agg.column!!.name)
        assertEquals("u", agg.column!!.prefix)
    }

    @Test
    fun sum() {
        val agg = Aggregation.sum("{u}score")

        assertEquals(Aggregation.SUM, agg.type)
        assertEquals("sum_u_score", agg.alias)
        assertEquals(" SUM ( u.score ) AS sum_u_score ", agg.build())

        assertEquals("score", agg.column!!.name)
        assertEquals("u", agg.column!!.prefix)
    }
}