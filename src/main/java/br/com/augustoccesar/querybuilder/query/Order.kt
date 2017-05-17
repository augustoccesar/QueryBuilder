package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.Buildable

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
class Order private constructor(columnMarked: String, var type: Order.Type?) : Buildable {
    enum class Type constructor(val value: String) {
        ASC(" ASC "),
        DESC(" DESC ")
    }

    var column: Column? = null

    init {
        this.column = Column.fromMarkdown(columnMarked)
    }

    override fun build(): String {
        return (" " + this.column!!.sqlColumnRepresentation() + this.type!!.value + " ").replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmField
        var ASC = Type.ASC
        @JvmField
        var DESC = Type.DESC

        @JvmStatic
        fun by(columnMarked: String, type: Type): Order {
            return Order(columnMarked, type)
        }

        @JvmStatic
        fun asc(columnMarked: String): Order {
            return Order(columnMarked, Type.ASC)
        }

        @JvmStatic
        fun desc(columnMarked: String): Order {
            return Order(columnMarked, Type.DESC)
        }
    }
}
