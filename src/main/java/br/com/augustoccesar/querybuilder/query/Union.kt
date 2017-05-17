package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.builders.SelectBuilder
import br.com.augustoccesar.querybuilder.constants.CommonStrings

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
class Union(var type: Union.Type?, var selectBuilder: SelectBuilder?) : Buildable {
    enum class Type constructor(val value: String) {
        UNION_ALL(CommonStrings.UNION_ALL),
        UNION(CommonStrings.UNION)
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(this.type!!.value)
        stringBuilder.append(CommonStrings.OPEN_PARENTHESES)
        stringBuilder.append(this.selectBuilder!!.build())
        stringBuilder.append(CommonStrings.CLOSE_PARENTHESES)

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmField
        var UNION = Type.UNION
        @JvmField
        var UNION_ALL = Type.UNION_ALL

        @JvmStatic
        fun union(selectBuilder: SelectBuilder): Union {
            return Union(Type.UNION, selectBuilder)
        }

        @JvmStatic
        fun unionAll(selectBuilder: SelectBuilder): Union = Union(Type.UNION_ALL, selectBuilder)
    }
}
