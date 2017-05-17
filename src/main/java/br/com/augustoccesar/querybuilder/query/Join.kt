package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import java.util.*

/**
 * Created by augustoccesar on 6/2/16.
 */
class Join : Buildable {
    enum class Type constructor(val value: String) {
        LEFT_JOIN("LEFT JOIN"),
        RIGHT_JOIN("RIGHT JOIN"),
        INNER_JOIN("INNER JOIN")
    }

    private var type: Type? = null
    private var table: Table? = null
    private var leftJoinOn: Column? = null
    private var rightJoinOn: Column? = null

    constructor(type: Type) {
        this.type = type
    }

    constructor(type: Type, tableOrTableMarkdown: String, joinOn: String) {
        this.type = type
        this.table = Table.fromMarkdown(tableOrTableMarkdown)
        this.splitJoinOn(joinOn)
    }

    constructor(type: Type, tableOrTableMarkdown: String, leftJoinOn: String, rightJoinOn: String) {
        this.type = type
        this.table = Table.fromMarkdown(tableOrTableMarkdown)
        this.leftJoinOn = Column.fromMarkdown(leftJoinOn)
        this.rightJoinOn = Column.fromMarkdown(rightJoinOn)
    }

    fun table(tableOrTableMarkdown: String): Join {
        this.table = Table.fromMarkdown(tableOrTableMarkdown)
        return this
    }

    fun on(joinOn: String): Join {
        this.splitJoinOn(joinOn)
        return this
    }

    fun on(leftJoinOn: String, rightJoinOn: String): Join {
        this.leftJoinOn = Column.fromMarkdown(leftJoinOn)
        this.rightJoinOn = Column.fromMarkdown(rightJoinOn)
        return this
    }

    private fun splitJoinOn(joinOn: String) {
        val result = joinOn.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        Arrays.asList(*result).forEach { s -> s.replace(" ", "") }

        this.leftJoinOn = Column.fromMarkdown(result[0].replace(" ", ""))
        this.rightJoinOn = Column.fromMarkdown(result[1].replace(" ", ""))
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(" ")
                .append(this.type!!.value).append(" ")
                .append(this.table!!.build()).append(CommonStrings.ON)
                .append(this.leftJoinOn!!.sqlColumnRepresentation()).append(Comparison.EQUALS.value).append(this.rightJoinOn!!.sqlColumnRepresentation())
                .append(" ")

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmField
        val INNER = Type.INNER_JOIN
        @JvmField
        val LEFT = Type.LEFT_JOIN
        @JvmField
        val RIGHT = Type.RIGHT_JOIN
    }
}
