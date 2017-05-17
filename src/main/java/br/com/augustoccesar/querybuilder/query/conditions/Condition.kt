package br.com.augustoccesar.querybuilder.query.conditions

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import br.com.augustoccesar.querybuilder.query.Column
import br.com.augustoccesar.querybuilder.query.Comparison
import java.util.*

/**
 * Created by augustoccesar on 4/29/16.
 */
class Condition() : ConditionSignature(), Buildable {
    var column: Column? = null
    var comparison: Comparison? = null
    var value: Any? = null

    constructor(column: Column, comparison: Comparison, value: Any): this() {
        this.column = column
        this.comparison = comparison
        this.value = value
    }

    val valueAsString: String
        get() {
            if (value != null) {
                if (value is String) {
                    if (value!!.toString() == "?") {
                        return "?"
                    } else {
                        return "'$value'"
                    }
                } else if (value is List<*>) {
                    val response = StringBuilder(CommonStrings.OPEN_PARENTHESES)
                    val list = value as List<Any>?
                    val size = list!!.size
                    for (i in 0..size - 1) {
                        val formattedValue = if (list[i] is String) "'" + list[i] + "'" else list[i].toString()
                        if (i == size - 1)
                            response.append(formattedValue)
                        else
                            response.append(formattedValue).append(CommonStrings.COMMA)
                    }
                    response.append(CommonStrings.CLOSE_PARENTHESES)
                    return response.toString()
                } else if (value is SelectBuilder) {
                    return CommonStrings.OPEN_PARENTHESES + (value as SelectBuilder).build() + CommonStrings.CLOSE_PARENTHESES
                } else if (value is Column) {
                    return (value as Column).name
                } else {
                    return value!!.toString()
                }
            } else {
                return ""
            }
        }

    override fun build(): String {
        return (" " + this.column!!.sqlColumnRepresentation() + this.comparison!!.value + this.valueAsString + " ").replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmStatic
        fun and(vararg conditions: ConditionSignature): ConditionGroup {
            val conditionsArray = Arrays.asList(*conditions)

            val conditionGroup = ConditionGroup()
            for (i in conditionsArray.indices) {
                conditionGroup.add(ConditionLink.AND, conditionsArray[i])
            }

            conditionGroup.generateMetadata()
            return conditionGroup
        }

        @JvmStatic
        fun or(vararg conditions: ConditionSignature): ConditionGroup {
            val conditionsArray = Arrays.asList(*conditions)

            val conditionGroup = ConditionGroup()
            for (i in conditionsArray.indices) {
                conditionGroup.add(ConditionLink.OR, conditionsArray[i])
            }

            conditionGroup.generateMetadata()
            return conditionGroup
        }

        @JvmStatic
        fun eq(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.EQUALS
            return condition
        }

        @JvmStatic
        fun neq(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.DIFFERENT
            return condition
        }

        @JvmStatic
        fun `in`(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.IN
            return condition
        }

        @JvmStatic
        fun isNull(field: String): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = null
            condition.comparison = Comparison.IS_NULL
            return condition
        }

        @JvmStatic
        fun isNotNull(field: String): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = null
            condition.comparison = Comparison.IS_NOT_NULL
            return condition
        }

        @JvmStatic
        fun like(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.LIKE
            return condition
        }

        @JvmStatic
        fun nlike(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.NOT_LIKE
            return condition
        }

        @JvmStatic
        fun gt(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.GREATER_THAN
            return condition
        }

        @JvmStatic
        fun gte(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.GREATER_THAN_OR_EQUAL
            return condition
        }

        @JvmStatic
        fun lt(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.LESS_THAN
            return condition
        }

        @JvmStatic
        fun lte(field: String, value: Any): Condition {
            val condition = Condition()
            condition.column = Column.fromMarkdown(field)
            condition.value = value
            condition.comparison = Comparison.LESS_THAN_OR_EQUAL
            return condition
        }
    }
}
