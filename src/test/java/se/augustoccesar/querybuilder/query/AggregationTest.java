package se.augustoccesar.querybuilder.query;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class AggregationTest {
    @Test
    public void average() throws Exception {
        Aggregation agg = Aggregation.average("{u}score");

        assertEquals(Aggregation.AVERAGE, agg.getType());
        assertEquals("avg_u_score", agg.getAlias());
        assertEquals(" AVG ( u.score ) AS avg_u_score ", agg.build());

        assertEquals("score", agg.getColumn().getName());
        assertEquals("u", agg.getColumn().getPrefix());
    }

    @Test
    public void count() throws Exception {
        Aggregation agg = Aggregation.count("{u}id");

        assertEquals(Aggregation.COUNT, agg.getType());
        assertEquals("count_u_id", agg.getAlias());
        assertEquals(" COUNT ( u.id ) AS count_u_id ", agg.build());

        assertEquals("id", agg.getColumn().getName());
        assertEquals("u", agg.getColumn().getPrefix());
    }

    @Test
    public void max() throws Exception {
        Aggregation agg = Aggregation.max("{u}score");

        assertEquals(Aggregation.MAX, agg.getType());
        assertEquals("max_u_score", agg.getAlias());
        assertEquals(" MAX ( u.score ) AS max_u_score ", agg.build());

        assertEquals("score", agg.getColumn().getName());
        assertEquals("u", agg.getColumn().getPrefix());
    }

    @Test
    public void min() throws Exception {
        Aggregation agg = Aggregation.min("{u}score");

        assertEquals(Aggregation.MIN, agg.getType());
        assertEquals("min_u_score", agg.getAlias());
        assertEquals(" MIN ( u.score ) AS min_u_score ", agg.build());

        assertEquals("score", agg.getColumn().getName());
        assertEquals("u", agg.getColumn().getPrefix());
    }

    @Test
    public void sum() throws Exception {
        Aggregation agg = Aggregation.sum("{u}score");

        assertEquals(Aggregation.SUM, agg.getType());
        assertEquals("sum_u_score", agg.getAlias());
        assertEquals(" SUM ( u.score ) AS sum_u_score ", agg.build());

        assertEquals("score", agg.getColumn().getName());
        assertEquals("u", agg.getColumn().getPrefix());
    }
}