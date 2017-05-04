package br.com.augustoccesar.querybuilder;

import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import br.com.augustoccesar.querybuilder.query.Condition;
import br.com.augustoccesar.querybuilder.query.Join;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: augustoccesar
 * Date: 03/05/17
 */
public class IntegrationTest {
//    @Test
//    public void shouldBeAbleToBasicSelect() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("u.name")
//                .from("users u");
//
//        String expected = "SELECT u.name AS u_name FROM users u";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldCreateAliasedQueryOnConstructor() {
//        SelectBuilder selectBuilder = new SelectBuilder("qb");
//
//        selectBuilder
//                .select("u.name")
//                .from("users u");
//
//        String expected = "( SELECT u.name AS u_name FROM users u ) AS qb";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldCreateAliasedQuery() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .alias("qb")
//                .select("u.name")
//                .from("users u");
//
//        String expected = "( SELECT u.name AS u_name FROM users u ) AS qb";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToBasicSelectUsingMarkdown() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name{custom_alias}")
//                .from("users{u}");
//
//        String expected = "SELECT u.name AS custom_alias FROM users u";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToQueryWithoutAlias() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name{_}")
//                .from("users{u}");
//
//        String expected = "SELECT u.name FROM users u";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToQueryDistinctMarkdown() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}*name{_}")
//                .from("users{u}");
//
//        String expected = "SELECT DISTINCT u.name FROM users u";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToExplicitJoin() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name", "{up}phone")
//                .from("users{u}")
//                .join(Join.INNER, "user_profile{up}", "{u}id", "{up}user_id");
//
//        String expected = "SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToJoinViaMethod() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name", "{up}phone")
//                .from("users{u}")
//                .innerJoin("user_profile{up}", "{u}id", "{up}user_id");
//
//        String expected = "SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToBuildJoinOutsideQueryBuilder() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        Join join = new Join(Join.INNER).table("user_profile{up}").on("{u}id", "{up}user_id");
//        selectBuilder
//                .select("{u}name", "{up}phone")
//                .from("users{u}")
//                .join(join);
//
//        String expected = "SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToBuildJoin2OutsideQueryBuilder() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        Join join = new Join(Join.INNER).table("user_profile{up}").on("{u}id = {up}user_id");
//        selectBuilder
//                .select("{u}name", "{up}phone")
//                .from("users{u}")
//                .join(join);
//
//        String expected = "SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToPassMultipleJoins() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        Join join1 = new Join(Join.INNER).table("user_profile{up}").on("{u}id", "{up}user_id");
//        Join join2 = new Join(Join.INNER).table("user_data{ud}").on("{u}id", "{ud}user_id");
//        selectBuilder
//                .select("{u}name", "{up}phone", "{ud}public")
//                .from("users{u}")
//                .joins(join1, join2);
//
//        String expected = "SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToPassMultipleJoinsSeparately() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        Join join1 = new Join(Join.INNER).table("user_profile{up}").on("{u}id", "{up}user_id");
//        Join join2 = new Join(Join.INNER).table("user_data{ud}").on("{u}id", "{ud}user_id");
//        selectBuilder
//                .select("{u}name", "{up}phone", "{ud}public")
//                .from("users{u}")
//                .join(join1)
//                .join(join2);
//
//        String expected = "SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToSetCondition() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name")
//                .from("users{u}")
//                .where("{u}name", Condition.EQUALS, "Augusto");
//
//        String expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToSetConditionCompact() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name")
//                .from("users{u}")
//                .where(Condition.eq("{u}name", "Augusto"));
//
//        String expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'";
//
//        assertEquals(expected, selectBuilder.build());
//    }
//
//    @Test
//    public void shouldBeAbleToSetMultipleConditionsCompact() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name")
//                .from("users{u}")
//                .where(
//                        Condition.eq("{u}name", "Augusto"),
//                        Condition.gte("{u}age", 21)
//                );
//
//        String expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' AND u.age >= 21";
//
//        assertEquals(expected, selectBuilder.build());
//    }

//    @Test
//    public void shouldBeAbleToSetLogicalConditionsCompact() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name")
//                .from("users{u}")
//                .where(
//                        Condition.or(
//                                Condition.eq("{u}name", "Augusto"),
//                                Condition.eq("{u}name", "Teste")
//                        )
//                );
//
//        String expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' OR u.name = 'Teste'";
//
//        assertEquals(expected, selectBuilder.build());
//    }

//    @Test
//    public void shouldBeAbleToSetNestedLogicalConditionsCompact() {
//        SelectBuilder selectBuilder = new SelectBuilder();
//
//        selectBuilder
//                .select("{u}name")
//                .from("users{u}")
//                .where(
//                        Condition.eq("{u}name", "Augusto"),
//                        Condition.gte("{u}age", 21),
//                        Condition.or(
//                                Condition.eq("{u}nationality", "Brazilian"),
//                                Condition.eq("{u}nationality", "Italian")
//                        )
//                );
//
//        String expected = "SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' AND u.age >= 21 AND (u.nationality = 'Brazilian' OR u.nationality = 'Italian')";
//
//        assertEquals(expected, selectBuilder.build());
//    }
}
