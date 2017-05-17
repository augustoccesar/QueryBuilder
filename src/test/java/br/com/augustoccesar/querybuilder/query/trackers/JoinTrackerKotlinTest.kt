package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.query.Join
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class JoinTrackerKotlinTest {
    @Test
    fun shouldCreateWithOneJoin() {
        val tracker = JoinTracker()
        tracker.addJoin(Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id"))

        val expect = " LEFT JOIN user_profile up ON u.id = up.user_id "
        assertEquals(expect, tracker.build())
    }

    @Test
    fun shouldCreateWithMoreThanOneJoin() {
        val tracker = JoinTracker()

        val join1 = Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id")
        val join2 = Join(Join.INNER, "user_data{ud}", "{u}id = {ud}user_id")

        tracker.addJoins(join1, join2)

        val expect = " LEFT JOIN user_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id "
        assertEquals(expect, tracker.build())
    }
}