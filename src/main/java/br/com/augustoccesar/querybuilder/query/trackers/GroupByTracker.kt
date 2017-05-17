package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.query.Column

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
class GroupByTracker : Buildable {
    private var column: Column? = null

    fun setGroupByColumn(markedColumn: String): GroupByTracker {
        this.column = Column.fromMarkdown(markedColumn)
        return this
    }

    override fun build(): String {
        return this.column!!.sqlColumnRepresentation()
    }

    override fun shouldBuild(): Boolean {
        return column != null
    }
}
