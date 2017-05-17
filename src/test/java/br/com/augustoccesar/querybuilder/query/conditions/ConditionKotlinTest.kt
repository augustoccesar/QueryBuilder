package br.com.augustoccesar.querybuilder.query.conditions

import br.com.augustoccesar.querybuilder.query.Comparison
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull


/**
 * Author: augustoccesar
 * Date: 17/05/17
 */
class ConditionKotlinTest {
    @Test
    fun shouldCreateEqualsCondition() {
        val condition = Condition.eq("{u}name", "Augusto")

        assertEquals("Augusto", condition.value)
        assertEquals(Comparison.EQUALS, condition.comparison)
        assertEquals(" u.name = 'Augusto' ", condition.build())
    }

    @Test
    fun shouldCreateNotEqualsCondition() {
        val condition = Condition.neq("{u}name", "Augusto")

        assertEquals("Augusto", condition.value)
        assertEquals(Comparison.DIFFERENT, condition.comparison)
        assertEquals(" u.name <> 'Augusto' ", condition.build())
    }

    @Test
    fun shouldCreateInCondition() {
        val values = ArrayList<String>()
        values.add("Augusto")
        val condition = Condition.`in`("{u}name", values)

        assertEquals("Augusto", (condition.value as ArrayList<*>)[0])
        assertEquals(Comparison.IN, condition.comparison)
        assertEquals(" u.name IN ( 'Augusto' ) ", condition.build())
    }

    @Test
    fun shouldCreateIsNullCondition() {
        val condition = Condition.isNull("{u}name")

        assertNull(condition.value)
        assertEquals(Comparison.IS_NULL, condition.comparison)
        assertEquals(" u.name IS NULL ", condition.build())
    }

    @Test
    fun shouldCreateIsNotNullCondition() {
        val condition = Condition.isNotNull("{u}name")

        assertNull(condition.value)
        assertEquals(Comparison.IS_NOT_NULL, condition.comparison)
        assertEquals(" u.name IS NOT NULL ", condition.build())
    }

    @Test
    fun shouldCreateLikeCondition() {
        val condition = Condition.like("{u}name", "Augusto")

        assertEquals("Augusto", condition.value)
        assertEquals(Comparison.LIKE, condition.comparison)
        assertEquals(" u.name LIKE 'Augusto' ", condition.build())
    }

    @Test
    fun shouldCreateNotLikeCondition() {
        val condition = Condition.nlike("{u}name", "Augusto")

        assertEquals("Augusto", condition.value)
        assertEquals(Comparison.NOT_LIKE, condition.comparison)
        assertEquals(" u.name NOT LIKE 'Augusto' ", condition.build())
    }

    @Test
    fun shouldCreateGreaterThanCondition() {
        val condition = Condition.gt("{u}name", 21)

        assertEquals(21, condition.value)
        assertEquals(Comparison.GREATER_THAN, condition.comparison)
        assertEquals(" u.name > 21 ", condition.build())
    }

    @Test
    fun shouldCreateGreaterThanOrEqualCondition() {
        val condition = Condition.gte("{u}name", 21)

        assertEquals(21, condition.value)
        assertEquals(Comparison.GREATER_THAN_OR_EQUAL, condition.comparison)
        assertEquals(" u.name >= 21 ", condition.build())
    }

    @Test
    fun shouldCreateLessThanCondition() {
        val condition = Condition.lt("{u}name", 21)

        assertEquals(21, condition.value)
        assertEquals(Comparison.LESS_THAN, condition.comparison)
        assertEquals(" u.name < 21 ", condition.build())
    }

    @Test
    fun shouldCreateLessThanOrEqualCondition() {
        val condition = Condition.lte("{u}name", 21)

        assertEquals(21, condition.value)
        assertEquals(Comparison.LESS_THAN_OR_EQUAL, condition.comparison)
        assertEquals(" u.name <= 21 ", condition.build())
    }
}