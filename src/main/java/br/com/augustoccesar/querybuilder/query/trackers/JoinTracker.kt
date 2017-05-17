package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.query.Join
import java.util.*

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
class JoinTracker : Buildable {
    private val joins = ArrayList<Join>()

    fun addJoin(join: Join): JoinTracker {
        this.joins.add(join)
        return this
    }

    fun addJoins(vararg joins: Join): JoinTracker {
        Collections.addAll(this.joins, *joins)
        return this
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        this.joins.forEach { join -> stringBuilder.append(join.build()) }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    override fun shouldBuild(): Boolean {
        return this.joins.size > 0
    }
}
