package se.augustocesar.querybuilder.query;

import se.augustocesar.querybuilder.builders.SelectBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class UnionTest {
    @Test
    public void shouldBuildCorrectUnion() {
        SelectBuilder selectBuilder = new SelectBuilder().select("{u}name").from("users{u}");

        Union union = Union.union(selectBuilder);

        assertEquals(Union.Type.UNION, union.getType());
        assertEquals(" UNION ( SELECT u.name AS u_name FROM users u ) ", union.build());
    }

    @Test
    public void shouldBuildCorrectUnionAll() {
        SelectBuilder selectBuilder = new SelectBuilder().select("{u}name").from("users{u}");

        Union union = Union.unionAll(selectBuilder);

        assertEquals(Union.Type.UNION_ALL, union.getType());
        assertEquals(" UNION ALL ( SELECT u.name AS u_name FROM users u ) ", union.build());
    }
}