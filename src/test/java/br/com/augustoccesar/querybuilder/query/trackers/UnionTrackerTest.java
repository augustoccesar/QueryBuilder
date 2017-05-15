package br.com.augustoccesar.querybuilder.query.trackers;

import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class UnionTrackerTest {
    @Test
    public void shouldBuildCorrectUnion() {
        SelectBuilder selectBuilder = new SelectBuilder().select("{u}name").from("users{u}");

        UnionTracker unionTracker = new UnionTracker();
        unionTracker.addUnion(selectBuilder);

        assertEquals(" UNION ( SELECT u.name AS u_name FROM users u ) ", unionTracker.build());
    }

    @Test
    public void shouldBuildCorrectMultipleUnion() {
        SelectBuilder selectBuilder = new SelectBuilder().select("{u}name").from("users{u}");
        SelectBuilder selectBuilder2 = new SelectBuilder().select("{c}first_name").from("clients{c}");

        UnionTracker unionTracker = new UnionTracker();
        unionTracker.addUnion(selectBuilder);
        unionTracker.addUnionAll(selectBuilder2);

        assertEquals(" UNION ( SELECT u.name AS u_name FROM users u ) UNION ALL ( SELECT c.first_name AS c_first_name FROM clients c ) ", unionTracker.build());
    }
}