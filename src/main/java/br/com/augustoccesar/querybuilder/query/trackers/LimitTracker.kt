package br.com.augustoccesar.querybuilder.query.trackers

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.query.Limit

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
class LimitTracker : Buildable {
    private var limit: Limit? = null

    fun setLimit(value: Int): LimitTracker {
        this.limit = Limit(value)
        return this
    }
    
    override fun build(): String {
        return (" " + this.limit!!.value + " ").replace("\\s+".toRegex(), " ")
    }

    override fun shouldBuild(): Boolean {
        return limit != null
    }
}
