package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import br.com.augustoccesar.querybuilder.query.Table
import java.util.*

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
class FromTracker : Buildable {
    private val tables = ArrayList<Table>()

    fun addMarkedTables(vararg tables: String): FromTracker {
        this.tables.addAll(Table.multipleFromMarkdown(*tables))
        return this
    }

    fun addTables(vararg tables: Table): FromTracker {
        Collections.addAll(this.tables, *tables)
        return this
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        for (i in this.tables.indices) {
            stringBuilder.append(this.tables[i].build())
            if (i < this.tables.size - 1) {
                stringBuilder.append(CommonStrings.COMMA)
            }
        }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    override fun shouldBuild(): Boolean {
        return this.tables.size > 0
    }
}
