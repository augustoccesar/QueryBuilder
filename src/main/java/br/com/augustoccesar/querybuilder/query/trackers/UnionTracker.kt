package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import br.com.augustoccesar.querybuilder.query.Union
import java.util.*

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
class UnionTracker : Buildable {
    private val unions = ArrayList<Union>()

    fun addUnion(selectBuilder: SelectBuilder): UnionTracker {
        this.unions.add(Union.union(selectBuilder))
        return this
    }

    fun addUnionAll(selectBuilder: SelectBuilder): UnionTracker {
        this.unions.add(Union.unionAll(selectBuilder))
        return this
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        this.unions.forEach { union -> stringBuilder.append(union.build()) }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    override fun shouldBuild(): Boolean {
        return this.unions.size > 0
    }
}
