package se.augustocesar.querybuilder.query.trackers;

import se.augustocesar.querybuilder.query.Join;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class JoinTrackerTest {
    @Test
    public void shouldCreateWithOneJoin() {
        JoinTracker tracker = new JoinTracker();
        tracker.addJoin(new Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id"));

        String expect = " LEFT JOIN user_profile up ON u.id = up.user_id ";
        assertEquals(expect, tracker.build());
    }

    @Test
    public void shouldCreateWithMoreThanOneJoin() {
        JoinTracker tracker = new JoinTracker();

        Join join1 = new Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id");
        Join join2 = new Join(Join.INNER, "user_data{ud}", "{u}id = {ud}user_id" );

        tracker.addJoins(join1, join2);

        String expect = " LEFT JOIN user_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id ";
        assertEquals(expect, tracker.build());
    }
}