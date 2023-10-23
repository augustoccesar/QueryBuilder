package se.augustoccesar.querybuilder.query.trackers;

import se.augustoccesar.querybuilder.query.Table;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class FromTrackerTest {
    @Test
    public void shouldBuildIfCorrectTable() {
        Table table = new Table("users", "u");

        String expected = " users u ";
        String result = new FromTracker().addTables(table).build();

        assertEquals(expected, result);
    }

    @Test
    public void shouldBuildIfCorrectMarkedTable() {
        String markedTable = "users{u}";

        String expected = " users u ";
        String result = new FromTracker().addMarkedTables(markedTable).build();

        assertEquals(expected, result);
    }
}