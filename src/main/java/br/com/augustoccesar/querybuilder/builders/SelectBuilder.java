package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.constants.CommonStrings;
import br.com.augustoccesar.querybuilder.query.Comparison;
import br.com.augustoccesar.querybuilder.query.Join;
import br.com.augustoccesar.querybuilder.query.Order;
import br.com.augustoccesar.querybuilder.query.conditions.ConditionSignature;
import br.com.augustoccesar.querybuilder.query.trackers.*;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SelectBuilder implements Buildable {
    /**
     * Attributes
     */
    private String alias;

    private SelectTracker selectTracker = new SelectTracker();
    private FromTracker fromTracker = new FromTracker();
    private JoinTracker joinTracker = new JoinTracker();
    private ConditionsTracker conditionsTracker = new ConditionsTracker();
    private OrderTracker orderTracker = new OrderTracker();

    /**
     * Constructors
     */
    public SelectBuilder() {
    }

    public SelectBuilder(String alias) {
        this.alias(alias);
    }

    public SelectBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public SelectBuilder select(Object... fields) {
        this.selectTracker.addSelect(fields);
        return this;
    }

    public SelectBuilder from(String... fromTables) {
        this.fromTracker.addMarkedTables(fromTables);
        return this;
    }

    public SelectBuilder join(Join join) {
        this.joinTracker.addJoin(join);
        return this;
    }

    public SelectBuilder joins(Join... joins) {
        this.joinTracker.addJoins(joins);
        return this;
    }

    public SelectBuilder innerJoin(String markedTable, String markedLeftOn, String markedRightOn) {
        this.joinTracker.addJoin(new Join(Join.INNER, markedTable, markedLeftOn, markedRightOn));
        return this;
    }

    public SelectBuilder leftJoin(String markedTable, String markedLeftOn, String markedRightOn) {
        this.joinTracker.addJoin(new Join(Join.LEFT, markedTable, markedLeftOn, markedRightOn));
        return this;
    }

    public SelectBuilder rightJoin(String markedTable, String markedLeftOn, String markedRightOn) {
        this.joinTracker.addJoin(new Join(Join.RIGHT, markedTable, markedLeftOn, markedRightOn));
        return this;
    }

    public SelectBuilder where(String columnMarkdown, Comparison comparison, Object value) {
        conditionsTracker.addCondition(columnMarkdown, comparison, value);
        return this;
    }

    public SelectBuilder where(ConditionSignature... conditions) {
        conditionsTracker.addConditions(conditions);
        return this;
    }

    public SelectBuilder order(Order order) {
        orderTracker.addOrder(order);
        return this;
    }

    public SelectBuilder orders(Order... orders) {
        orderTracker.addOrders(orders);
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        if (this.alias != null) {
            stringBuilder.append(CommonStrings.OPEN_PARENTHESES);
        }

        stringBuilder.append(CommonStrings.SELECT);
        stringBuilder.append(this.selectTracker.build());

        if (this.fromTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.FROM);
            stringBuilder.append(this.fromTracker.build());
        }

        if (this.joinTracker.shouldBuild()) {
            stringBuilder.append(this.joinTracker.build());
        }

        if (this.conditionsTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.WHERE);
            stringBuilder.append(this.conditionsTracker.build());
        }

        if (this.orderTracker.shouldBuild()) {
            stringBuilder.append(CommonStrings.ORDER_BY);
            stringBuilder.append(this.orderTracker.build());
        }

        if (this.alias != null) {
            stringBuilder.append(CommonStrings.CLOSE_PARENTHESES);
            stringBuilder.append(CommonStrings.AS).append(this.alias);
        }

        // Remove unnecessary spaces

        if (" ".equals(String.valueOf(stringBuilder.charAt(0)))) {
            stringBuilder.deleteCharAt(0);
        }

        if (" ".equals(String.valueOf(stringBuilder.charAt(stringBuilder.length() - 1)))) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    public String getAlias() {
        return alias;
    }
}
