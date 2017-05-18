package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import br.com.augustoccesar.querybuilder.exceptions.InvalidSelectBuilder
import br.com.augustoccesar.querybuilder.query.Aggregation
import br.com.augustoccesar.querybuilder.query.Column
import java.util.*

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
class SelectTracker : Buildable {
    private val columns = ArrayList<Column>()
    private val selectBuilders = ArrayList<SelectBuilder>()
    private val aggregations = ArrayList<Aggregation>()

    fun addSelect(vararg selectObjects: Any): SelectTracker {
        Arrays.asList(*selectObjects).forEach { item ->
            if (item is String)
                this.columns.add(Column.fromMarkdown(item.toString()))
            if (item is Column)
                this.columns.add(item)
            if (item is SelectBuilder)
                this.selectBuilders.add(item)
            if (item is Aggregation)
                this.aggregations.add(item)
            if (item is MutableList<*>)
                item.forEach { this.columns.add(Column.fromMarkdown(it.toString())) }
        }

        return this
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(" ")
        for (i in this.columns.indices) {
            stringBuilder.append(this.columns[i].build())
            if (i < this.columns.size - 1 || this.aggregations.size > 0 || this.selectBuilders.size > 0) {
                stringBuilder.append(CommonStrings.COMMA)
            }
        }

        for (i in this.aggregations.indices) {
            stringBuilder.append(this.aggregations[i].build())
            if (i < this.aggregations.size - 1 || this.selectBuilders.size > 0) {
                stringBuilder.append(CommonStrings.COMMA)
            }
        }

        for (i in this.selectBuilders.indices) {
            val sb = this.selectBuilders[i]

            if (sb.alias != null) {
                stringBuilder.append(this.selectBuilders[i].build())
            } else {
                throw InvalidSelectBuilder("Nested SelectBuilder must have an alias.")
            }

            if (i < this.selectBuilders.size - 1) {
                stringBuilder.append(CommonStrings.COMMA)
            }
        }

        stringBuilder.append(" ")

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    override fun shouldBuild(): Boolean {
        return this.columns.size > 0 || this.selectBuilders.size > 0 || this.aggregations.size > 0
    }
}
