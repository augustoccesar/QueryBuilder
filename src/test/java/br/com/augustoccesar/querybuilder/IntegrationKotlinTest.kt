package br.com.augustoccesar.querybuilder

import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import br.com.augustoccesar.querybuilder.query.Aggregation
import br.com.augustoccesar.querybuilder.query.Comparison
import br.com.augustoccesar.querybuilder.query.Join
import br.com.augustoccesar.querybuilder.query.Order
import br.com.augustoccesar.querybuilder.query.conditions.Condition
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class IntegrationKotlinTest {
    @Test
    fun shouldBeAbleToBasicSelect() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name")
                .from("users u")

        val expected = "SELECT u.name AS u_name FROM users u"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldCreateAliasedQueryOnConstructor() {
        val selectBuilder = SelectBuilder("qb")

        selectBuilder
                .select("{u}name")
                .from("users u")

        val expected = "( SELECT u.name AS u_name FROM users u ) AS qb"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldCreateAliasedQuery() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .alias("qb")
                .select("{u}name")
                .from("users u")

        val expected = "( SELECT u.name AS u_name FROM users u ) AS qb"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToBasicSelectUsingMarkdown() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name{custom_alias}")
                .from("users{u}")

        val expected = "SELECT u.name AS custom_alias FROM users u"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToQueryWithoutAlias() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name{_}")
                .from("users{u}")

        val expected = "SELECT u.name FROM users u"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToQueryDistinctMarkdown() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}*name{_}")
                .from("users{u}")

        val expected = "SELECT DISTINCT u.name FROM users u"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToJoinViaMethod() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name", "{up}phone")
                .from("users{u}")
                .innerJoin("users_profile{up}", "{u}id", "{up}user_id")

        val expected = "SELECT u.name AS u_name , up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToBuildJoinOutsideQueryBuilder() {
        val selectBuilder = SelectBuilder()

        val join = Join(Join.INNER).table("users_profile{up}").on("{u}id", "{up}user_id")
        selectBuilder
                .select("{u}name", "{up}phone")
                .from("users{u}")
                .join(join)

        val expected = "SELECT u.name AS u_name , up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToBuildJoin2OutsideQueryBuilder() {
        val selectBuilder = SelectBuilder()

        val join = Join(Join.INNER).table("users_profile{up}").on("{u}id = {up}user_id")
        selectBuilder
                .select("{u}name", "{up}phone")
                .from("users{u}")
                .join(join)

        val expected = "SELECT u.name AS u_name , up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToPassMultipleJoins() {
        val selectBuilder = SelectBuilder()

        val join1 = Join(Join.INNER).table("users_profile{up}").on("{u}id", "{up}user_id")
        val join2 = Join(Join.INNER).table("user_data{ud}").on("{u}id", "{ud}user_id")
        selectBuilder
                .select("{u}name", "{up}phone", "{ud}public")
                .from("users{u}")
                .joins(join1, join2)

        val expected = "SELECT u.name AS u_name , up.phone AS up_phone , ud.public AS ud_public FROM users u INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToPassMultipleJoinsSeparately() {
        val selectBuilder = SelectBuilder()

        val join1 = Join(Join.INNER).table("users_profile{up}").on("{u}id", "{up}user_id")
        val join2 = Join(Join.INNER).table("user_data{ud}").on("{u}id", "{ud}user_id")
        selectBuilder
                .select("{u}name", "{up}phone", "{ud}public")
                .from("users{u}")
                .join(join1)
                .join(join2)

        val expected = "SELECT u.name AS u_name , up.phone AS up_phone , ud.public AS ud_public FROM users u INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetCondition() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name")
                .from("users{u}")
                .where("{u}name", Comparison.EQUALS, "Augusto")

        val expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetConditionCompact() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name")
                .from("users{u}")
                .where(Condition.eq("{u}name", "Augusto"))

        val expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetMultipleConditionsCompact() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name")
                .from("users{u}")
                .where(
                        Condition.eq("{u}name", "Augusto"),
                        Condition.gte("{u}age", 21)
                )

        val expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' AND u.age >= 21"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetLogicalConditionsCompact() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name")
                .from("users{u}")
                .where(
                        Condition.or(
                                Condition.eq("{u}name", "Augusto"),
                                Condition.eq("{u}name", "Teste")
                        )
                )

        val expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' OR u.name = 'Teste'"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetNestedLogicalConditionsCompact() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}name")
                .from("users{u}")
                .where(
                        Condition.eq("{u}name", "Augusto"),
                        Condition.gte("{u}age", 21),
                        Condition.or(
                                Condition.eq("{u}nationality", "Brazilian"),
                                Condition.eq("{u}nationality", "Italian")
                        )
                )

        val expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' AND u.age >= 21 AND ( u.nationality = 'Brazilian' OR u.nationality = 'Italian' )"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetOrder() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}id", "{u}name")
                .from("users{u}")
                .order(Order.by("{u}id", Order.ASC))

        val expected = "SELECT u.id AS u_id , u.name AS u_name FROM users u ORDER BY u.id ASC"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBeAbleToSetMultipleOrders() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}id", "{u}name")
                .from("users{u}")
                .orders(
                        Order.by("{u}id", Order.ASC),
                        Order.by("{u}name", Order.DESC)
                )

        val expected = "SELECT u.id AS u_id , u.name AS u_name FROM users u ORDER BY u.id ASC , u.name DESC"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldSetLimit() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}id", "{u}name")
                .from("users{u}")
                .limit(5)

        val expected = "SELECT u.id AS u_id , u.name AS u_name FROM users u LIMIT 5"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldSetAggregationAndGroupBy() {
        val selectBuilder = SelectBuilder()

        selectBuilder
                .select("{u}id", "{u}name", Aggregation.count("{u}id"))
                .from("users{u}")
                .groupBy("{u}name")

        val expected = "SELECT u.id AS u_id , u.name AS u_name , COUNT ( u.id ) AS count_u_id FROM users u GROUP BY u.name"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBuildTwoUnionAllSelectBuilders() {
        val selectBuilder = SelectBuilder()
        val selectBuilder2 = SelectBuilder()

        selectBuilder
                .select("{u}id", "{u}name")
                .from("users{u}")

        selectBuilder2
                .select("{c}identifier", "{c}first_name")
                .from("clients{c}")

        selectBuilder.unionAll(selectBuilder2)

        val expected = "( SELECT u.id AS u_id , u.name AS u_name FROM users u ) UNION ALL ( SELECT c.identifier AS c_identifier , c.first_name AS c_first_name FROM clients c )"

        assertEquals(expected, selectBuilder.build())
    }

    @Test
    fun shouldBuildTwoUnionSelectBuilders() {
        val selectBuilder = SelectBuilder()
        val selectBuilder2 = SelectBuilder()

        selectBuilder
                .select("{u}id", "{u}name")
                .from("users{u}")

        selectBuilder2
                .select("{c}identifier", "{c}first_name")
                .from("clients{c}")

        selectBuilder.union(selectBuilder2)

        val expected = "( SELECT u.id AS u_id , u.name AS u_name FROM users u ) UNION ( SELECT c.identifier AS c_identifier , c.first_name AS c_first_name FROM clients c )"

        assertEquals(expected, selectBuilder.build())
    }
}