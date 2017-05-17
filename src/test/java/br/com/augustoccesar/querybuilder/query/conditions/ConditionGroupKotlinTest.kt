package br.com.augustoccesar.querybuilder.query.conditions

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Author: augustoccesar
 * Date: 05/05/17
 */
open class ConditionGroupKotlinTest {
    @Test
    fun shouldBuildCorrectlyIfCorrectConstruction() {
        val conditionGroup: ConditionGroup = Condition.and(
                Condition.eq("{_}name", "Augusto"),
                Condition.gte("{_}age", 21),
                Condition.or(
                        Condition.eq("{_}nationality", "Brazilian"),
                        Condition.eq("{_}nationality", "Japanese"),
                        Condition.and(
                                Condition.eq("{_}job", "Diplomat"),
                                Condition.eq("{_}job_active", true)
                        )
                )
        )

        val expected: String = " name = 'Augusto' AND age >= 21 AND ( nationality = 'Brazilian' OR nationality = 'Japanese' OR ( job = 'Diplomat' AND job_active = true ) ) "
        assertEquals(expected, conditionGroup.build())
    }
}