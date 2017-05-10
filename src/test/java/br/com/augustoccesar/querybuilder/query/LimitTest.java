package br.com.augustoccesar.querybuilder.query;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class LimitTest {
    @Test
    public void shouldCreateIfValidValue() {
        Limit limit = new Limit(5);

        assertEquals(5, limit.getValue());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionOnInvalidValue() {
        thrown.expect(IllegalArgumentException.class);

        new Limit(-1);
    }
}