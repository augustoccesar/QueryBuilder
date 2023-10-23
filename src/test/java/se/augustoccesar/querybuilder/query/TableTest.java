package se.augustoccesar.querybuilder.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class TableTest {
    @Test
    public void shouldBeAbleToCreateUsingMarkdown() {
        Table table = Table.fromMarkdown("users{u}");

        assertEquals("users", table.getName());
        assertEquals("u", table.getAlias());
    }

    @Test
    public void shouldBeAbleToCreateUsingEmptyMarkdown() {
        Table table = Table.fromMarkdown("users{_}");

        assertEquals("users", table.getName());
        assertNull(table.getAlias());
    }

    @Test
    public void shouldBeAbleToCreateWithoutMarkdown() {
        Table table = Table.fromMarkdown("users u");

        assertEquals("users", table.getName());
        assertEquals("u", table.getAlias());
    }

    @Test
    public void shouldBeAbleToCreateWithoutMarkdownWithoutAlias() {
        Table table = Table.fromMarkdown("users");

        assertEquals("users", table.getName());
        assertNull(table.getAlias());
    }
}