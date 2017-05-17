package br.com.augustoccesar.querybuilder.query.conditions

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import java.util.*

/**
 * Author: augustoccesar
 * Date: 05/05/17
 */
class ConditionGroup : ConditionSignature(), Buildable {
    var isNested = false
    val conditionsAndLinks = ArrayList<Any>()

    fun add(conditionSignature: ConditionSignature) {
        if (this.conditionsAndLinks.size == 0) {
            this.conditionsAndLinks.add(conditionSignature)
        } else {
            this.add(ConditionLink.AND, conditionSignature)
        }
    }

    fun add(link: ConditionLink, condition: ConditionSignature) {
        if (this.conditionsAndLinks.size == 0) {
            this.conditionsAndLinks.add(condition)
        } else {
            this.conditionsAndLinks.add(link)
            this.conditionsAndLinks.add(condition)
        }
    }

    fun generateMetadata() {
        this.conditionsAndLinks.forEach { item ->
            if (item is ConditionGroup && item !== conditionsAndLinks[0]) {
                item.isNested = true
                item.generateMetadata()
            }
        }
    }

    override fun build(): String {
        val builder = StringBuilder()

        this.conditionsAndLinks.forEach { item ->
            if (item is ConditionLink) {
                builder.append(item.displayName)
            } else if (item is Condition) {
                builder.append(item.build())
            } else if (item is ConditionGroup) {
                val cg = item

                if (cg.isNested) {
                    builder.append(CommonStrings.OPEN_PARENTHESES)
                            .append(cg.build())
                            .append(CommonStrings.CLOSE_PARENTHESES)

                } else {
                    builder.append(cg.build())
                }
            }
        }

        return builder.toString().replace("\\s+".toRegex(), " ")
    }
}
