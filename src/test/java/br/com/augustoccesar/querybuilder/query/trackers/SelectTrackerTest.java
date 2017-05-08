package br.com.augustoccesar.querybuilder.query.trackers;

import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import br.com.augustoccesar.querybuilder.query.Column;
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
}