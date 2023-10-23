package se.augustoccesar.querybuilder.query.trackers;

import se.augustoccesar.querybuilder.builders.SelectBuilder;
import se.augustoccesar.querybuilder.query.Aggregation;
import se.augustoccesar.querybuilder.query.Column;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class SelectTrackerTest {
    @Test
    public void shouldBuildIfCorrectColumnMarked() {
        SelectTracker tracker = new SelectTracker().addSelect("{u}name");

        String expect = " u.name AS u_name ";
        assertEquals(expect, tracker.build());
    }

    @Test
    public void shouldBuildIfCorrectColumn() {
        SelectTracker tracker = new SelectTracker().addSelect(new Column("u", "name", false));

        String expect = " u.name AS u_name ";
        assertEquals(expect, tracker.build());
    }

    @Test
    public void shouldBuildIfCorrectSelectBuilder() {
        SelectBuilder selectBuilder = new SelectBuilder("qb").select("{u}name").from("users{u}");
        SelectTracker tracker = new SelectTracker().addSelect(selectBuilder);

        String expect = " ( SELECT u.name AS u_name FROM users u ) AS qb ";
        assertEquals(expect, tracker.build());
    }

    @Test
    public void shouldBuildWithColumnAndSelectBuilder() {
        SelectBuilder selectBuilder = new SelectBuilder("qb").select("{u}name").from("users{u}");
        Column column = new Column("u", "name", false);

        SelectTracker tracker = new SelectTracker().addSelect(selectBuilder);
        tracker.addSelect(column);

        String expect = " u.name AS u_name , ( SELECT u.name AS u_name FROM users u ) AS qb ";
        assertEquals(expect, tracker.build());
    }

    @Test
    public void shouldBuildWithColumnAndSelectBuilderAndAggregation() {
        SelectBuilder selectBuilder = new SelectBuilder("qb").select("{u}name").from("users{u}");
        Column column = new Column("u", "name", false);
        Aggregation agg = Aggregation.count("{u}id");

        SelectTracker tracker = new SelectTracker().addSelect(selectBuilder);
        tracker.addSelect(column);
        tracker.addSelect(agg);

        String expect = " u.name AS u_name , COUNT ( u.id ) AS count_u_id , ( SELECT u.name AS u_name FROM users u ) AS qb ";
        assertEquals(expect, tracker.build());
    }
}