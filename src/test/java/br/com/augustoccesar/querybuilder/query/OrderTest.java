package br.com.augustoccesar.querybuilder.query;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class OrderTest {
    @Test
    public void shouldBuildIfCorrectMarkedUsingBy(){
        Order order = Order.by("{u}name", Order.Type.ASC);

        assertEquals("name", order.getColumn().getName());
        assertEquals(Order.Type.ASC, order.getType());

        String expected = " u.name ASC ";
        assertEquals(expected, order.build());
    }

    @Test
    public void shouldBuildIfCorrectMarkedUsingAsc(){
        Order order = Order.asc("{u}name");

        assertEquals("name", order.getColumn().getName());
        assertEquals(Order.Type.ASC, order.getType());

        String expected = " u.name ASC ";
        assertEquals(expected, order.build());
    }

    @Test
    public void shouldBuildIfCorrectMarkedUsingDesc(){
        Order order = Order.desc("{u}name");

        assertEquals("name", order.getColumn().getName());
        assertEquals(Order.Type.DESC, order.getType());

        String expected = " u.name DESC ";
        assertEquals(expected, order.build());
    }
}