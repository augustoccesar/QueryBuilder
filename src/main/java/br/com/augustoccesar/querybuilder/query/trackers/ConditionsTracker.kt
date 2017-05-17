package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.query.Column
import br.com.augustoccesar.querybuilder.query.Comparison
import br.com.augustoccesar.querybuilder.query.conditions.Condition
import br.com.augustoccesar.querybuilder.query.conditions.ConditionGroup
import br.com.augustoccesar.querybuilder.query.conditions.ConditionSignature
import java.util.*

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
class ConditionsTracker : Buildable {
    private val baseConditionGroup = ConditionGroup()

    fun addCondition(columnMarkdown: String, comparison: Comparison, value: Any) {
        this.baseConditionGroup.add(Condition(Column.fromMarkdown(columnMarkdown), comparison, value))
    }

    fun addConditions(vararg conditions: ConditionSignature) {
        Arrays.asList(*conditions).forEach { cond -> this.baseConditionGroup.add(cond) }
        this.baseConditionGroup.generateMetadata()
    }

    override fun build(): String {
        return this.baseConditionGroup.build()
    }

    override fun shouldBuild(): Boolean {
        return this.baseConditionGroup.conditionsAndLinks.size > 0
    }
}
