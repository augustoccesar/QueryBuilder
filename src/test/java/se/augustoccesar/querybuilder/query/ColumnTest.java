package se.augustoccesar.querybuilder.query;

import se.augustoccesar.querybuilder.exceptions.InvalidPattern;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class ColumnTest {
    @Test
    public void shouldCreateWithTableAlias() {
        Column column = Column.fromMarkdown("{u}name");

        assertEquals("name", column.getName());
        assertEquals("u", column.getPrefix());
        assertEquals("u_name", column.getAlias());
    }

    @Test
    public void shouldCreateWithTableAliasAndCustomAlias() {
        Column column = Column.fromMarkdown("{u}name{custom_alias}");

        assertEquals("name", column.getName());
        assertEquals("u", column.getPrefix());
        assertEquals("custom_alias", column.getAlias());
    }

    @Test
    public void shouldCreateWithTableAliasAndWithAliasEmptyMark() {
        Column column = Column.fromMarkdown("{u}name{_}");

        assertEquals("name", column.getName());
        assertEquals("u", column.getPrefix());
        assertNull(column.getAlias());
    }

    @Test
    public void shouldCreateWithDistinctMark() {
        Column column = Column.fromMarkdown("{u}*name");

        assertEquals("name", column.getName());
        assertEquals("u", column.getPrefix());
        assertEquals("u_name", column.getAlias());
        assertTrue(column.isDistinct());
    }

    @Test
    public void shouldCreateWithTableAliasEmptyMark() {
        Column column = Column.fromMarkdown("{_}name");

        assertEquals("name", column.getName());
        assertNull(column.getPrefix());
        assertNull(column.getAlias());
        assertFalse(column.isDistinct());
    }

    @Test
    public void shouldCreateWithTableAliasEmptyMarkAndCustomAlias() {
        Column column = Column.fromMarkdown("{_}name{custom_name}");

        assertEquals("name", column.getName());
        assertNull(column.getPrefix());
        assertEquals("custom_name", column.getAlias());
        assertFalse(column.isDistinct());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldntCreateWithoutTableAliasOrEmptyMark() {
        thrown.expect(InvalidPattern.class);

        Column.fromMarkdown("name");
    }
}