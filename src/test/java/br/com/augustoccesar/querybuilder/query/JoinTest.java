package br.com.augustoccesar.querybuilder.query;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class JoinTest {
    @Test
    public void shouldCreateWithNestedMethodsAndSeparatedOnClause() {
        Join join = new Join(Join.LEFT).table("user_profile{up}").on("{u}id", "{up}user_id");

        String expect = " LEFT JOIN user_profile up ON u.id = up.user_id ";
        assertEquals(expect, join.build());
    }

    @Test
    public void shouldCreateWithNestedMethodsAndCollapsedOnClause() {
        Join join = new Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id");

        String expect = " LEFT JOIN user_profile up ON u.id = up.user_id ";
        assertEquals(expect, join.build());
    }

    @Test
    public void shouldCreateWithConstructorAndSeparatedOnClause() {
        Join join = new Join(Join.INNER, "user_profile{up}", "{u}id", "{up}user_id");

        String expect = " INNER JOIN user_profile up ON u.id = up.user_id ";
        assertEquals(expect, join.build());
    }

    @Test
    public void shouldCreateWithConstructorAndCollapsedOnClause() {
        Join join = new Join(Join.INNER, "user_profile{up}", "{u}id = {up}user_id" );

        String expect = " INNER JOIN user_profile up ON u.id = up.user_id ";
        assertEquals(expect, join.build());
    }
}