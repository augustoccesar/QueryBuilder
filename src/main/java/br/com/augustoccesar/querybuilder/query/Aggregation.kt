package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.constants.CommonStrings

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
class Aggregation : Buildable {
    enum class Type constructor(val value: String) {
        AVERAGE(" AVG "),
        COUNT(" COUNT "),
        MAX(" MAX "),
        MIN(" MIN "),
        SUM(" SUM ")
    }

    var type: Type? = null
    var column: Column? = null
    var alias: String? = null


    private constructor(markedColumn: String, type: Type) {
        this.type = type
        this.column = Column.fromMarkdown(markedColumn)
        this.alias = this.type!!.value.toLowerCase().replace("\\s".toRegex(), "") + "_" + this.column!!.alias
    }

    private constructor(markedColumn: String, type: Type, alias: String) {
        this.type = type
        this.column = Column.fromMarkdown(markedColumn)
        this.alias = alias
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()
        stringBuilder
                .append(" ")
                .append(this.type!!.value)
                .append(CommonStrings.OPEN_PARENTHESES)
                .append(this.column!!.prefix).append(".").append(this.column!!.name)
                .append(CommonStrings.CLOSE_PARENTHESES)
                .append(CommonStrings.AS)
                .append(this.alias)
                .append(" ")

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmField
        var AVERAGE = Type.AVERAGE
        @JvmField
        var COUNT = Type.COUNT
        @JvmField
        var MAX = Type.MAX
        @JvmField
        var MIN = Type.MIN
        @JvmField
        var SUM = Type.SUM

        @JvmStatic
        fun average(markedColumn: String): Aggregation {
            return Aggregation(markedColumn, Type.AVERAGE)
        }

        @JvmStatic
        fun average(markedColumn: String, alias: String): Aggregation {
            return Aggregation(markedColumn, Type.AVERAGE, alias)
        }

        @JvmStatic
        fun count(markedColumn: String): Aggregation {
            return Aggregation(markedColumn, Type.COUNT)
        }

        @JvmStatic
        fun count(markedColumn: String, alias: String): Aggregation {
            return Aggregation(markedColumn, Type.COUNT, alias)
        }

        @JvmStatic
        fun max(markedColumn: String): Aggregation {
            return Aggregation(markedColumn, Type.MAX)
        }

        @JvmStatic
        fun max(markedColumn: String, alias: String): Aggregation {
            return Aggregation(markedColumn, Type.MAX, alias)
        }

        @JvmStatic
        fun min(markedColumn: String): Aggregation {
            return Aggregation(markedColumn, Type.MIN)
        }

        @JvmStatic
        fun min(markedColumn: String, alias: String): Aggregation {
            return Aggregation(markedColumn, Type.MIN, alias)
        }

        @JvmStatic
        fun sum(markedColumn: String): Aggregation {
            return Aggregation(markedColumn, Type.SUM)
        }

        @JvmStatic
        fun sum(markedColumn: String, alias: String): Aggregation {
            return Aggregation(markedColumn, Type.SUM, alias)
        }
    }
}
