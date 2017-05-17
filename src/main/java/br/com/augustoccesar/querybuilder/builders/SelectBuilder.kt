package br.com.augustoccesar.querybuilder.builders

import br.com.augustoccesar.querybuilder.constants.CommonStrings
import br.com.augustoccesar.querybuilder.query.Comparison
import br.com.augustoccesar.querybuilder.query.Join
import br.com.augustoccesar.querybuilder.query.Order
import br.com.augustoccesar.querybuilder.query.conditions.ConditionSignature
import br.com.augustoccesar.querybuilder.query.trackers.*

/**
 * Created by augustoccesar on 6/13/16.
 */
class SelectBuilder() : Buildable {
    var alias: String? = null

    private val selectTracker = SelectTracker()
    private val fromTracker = FromTracker()
    private val joinTracker = JoinTracker()
    private val conditionsTracker = ConditionsTracker()
    private val orderTracker = OrderTracker()
    private val limitTracker = LimitTracker()
    private val groupByTracker = GroupByTracker()
    private val unionTracker = UnionTracker()

    constructor(alias: String): this() {
        this.alias(alias)
    }

    fun alias(alias: String): SelectBuilder {
        this.alias = alias
        return this
    }

    fun select(vararg fields: Any): SelectBuilder {
        this.selectTracker.addSelect(*fields)
        return this
    }

    fun from(vararg fromTables: String): SelectBuilder {
        this.fromTracker.addMarkedTables(*fromTables)
        return this
    }

    fun join(join: Join): SelectBuilder {
        this.joinTracker.addJoin(join)
        return this
    }

    fun joins(vararg joins: Join): SelectBuilder {
        this.joinTracker.addJoins(*joins)
        return this
    }

    fun innerJoin(markedTable: String, markedLeftOn: String, markedRightOn: String): SelectBuilder {
        this.joinTracker.addJoin(Join(Join.INNER, markedTable, markedLeftOn, markedRightOn))
        return this
    }

    fun leftJoin(markedTable: String, markedLeftOn: String, markedRightOn: String): SelectBuilder {
        this.joinTracker.addJoin(Join(Join.LEFT, markedTable, markedLeftOn, markedRightOn))
        return this
    }

    fun rightJoin(markedTable: String, markedLeftOn: String, markedRightOn: String): SelectBuilder {
        this.joinTracker.addJoin(Join(Join.RIGHT, markedTable, markedLeftOn, markedRightOn))
        return this
    }

    fun where(columnMarkdown: String, comparison: Comparison, value: Any): SelectBuilder {
        conditionsTracker.addCondition(columnMarkdown, comparison, value)
        return this
    }

    fun where(vararg conditions: ConditionSignature): SelectBuilder {
        conditionsTracker.addConditions(*conditions)
        return this
    }

    fun order(order: Order): SelectBuilder {
        orderTracker.addOrder(order)
        return this
    }

    fun orders(vararg orders: Order): SelectBuilder {
        orderTracker.addOrders(*orders)
        return this
    }

    fun limit(value: Int): SelectBuilder {
        limitTracker.setLimit(value)
        return this
    }

    fun groupBy(markedColumn: String): SelectBuilder {
        groupByTracker.setGroupByColumn(markedColumn)
        return this
    }

    fun union(selectBuilder: SelectBuilder): SelectBuilder {
        unionTracker.addUnion(selectBuilder)
        return this
    }

    fun unionAll(selectBuilder: SelectBuilder): SelectBuilder {
        unionTracker.addUnionAll(selectBuilder)
        return this
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        if (this.alias != null || this.unionTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.OPEN_PARENTHESES)
        }

        stringBuilder.append(CommonStrings.SELECT)
        stringBuilder.append(this.selectTracker.build())

        if (this.fromTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.FROM)
            stringBuilder.append(this.fromTracker.build())
        }

        if (this.joinTracker.shouldBuild()) {
            stringBuilder.append(this.joinTracker.build())
        }

        if (this.conditionsTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.WHERE)
            stringBuilder.append(this.conditionsTracker.build())
        }

        if (this.orderTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.ORDER_BY)
            stringBuilder.append(this.orderTracker.build())
        }

        if (this.limitTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.LIMIT)
            stringBuilder.append(this.limitTracker.build())
        }

        if (this.groupByTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.GROUP_BY)
            stringBuilder.append(this.groupByTracker.build())
        }

        // Close with alias

        if (this.alias != null) {
            stringBuilder.append(CommonStrings.CLOSE_PARENTHESES)
            stringBuilder.append(CommonStrings.AS).append(this.alias)
        }

        // Build Unions

        if (this.unionTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.CLOSE_PARENTHESES)
            stringBuilder.append(this.unionTracker.build())
        }

        // Remove unnecessary spaces

        if (" " == stringBuilder[0].toString()) {
            stringBuilder.deleteCharAt(0)
        }

        if (" " == stringBuilder[stringBuilder.length - 1].toString()) {
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }
}
