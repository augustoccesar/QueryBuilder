package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class TableTest {
    @Test
    public void shouldBeAbleToCreateUsingMarkdown() throws InvalidPattern {
        Table table = new Table("users{u}");

        assertEquals("users", table.getName());
        assertEquals("u", table.getAlias());
    }

    @Test
    public void shouldBeAbleToCreateWithoutMarkdown() throws InvalidPattern {
        Table table = new Table("users u");

        assertEquals("users", table.getName());
        assertEquals("u", table.getAlias());
    }
}