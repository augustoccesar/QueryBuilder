package br.com.augustoccesar.querybuilder.query.conditions;

import br.com.augustoccesar.querybuilder.builders.Buildable;
import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import br.com.augustoccesar.querybuilder.constants.CommonStrings;
import br.com.augustoccesar.querybuilder.query.Column;
import br.com.augustoccesar.querybuilder.query.Comparison;

import java.util.Arrays;
import java.util.List;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class Condition extends ConditionSignature implements Buildable {
    private Column column;
    private Comparison comparison;
    private Object value;

    // Constructors

    private Condition() {
    }

    public Condition(Column column, Comparison comparison, Object value) {
        this.column = column;
        this.comparison = comparison;
        this.value = value;
    }

    // Groups

    public static ConditionGroup and(ConditionSignature... conditions) {
        List<ConditionSignature> conditionsArray = Arrays.asList(conditions);

        ConditionGroup conditionGroup = new ConditionGroup();
        for (int i = 0; i < conditionsArray.size(); i++) {
            conditionGroup.add(ConditionLink.AND, conditionsArray.get(i));
        }

        conditionGroup.generateMetadata();
        return conditionGroup;
    }

    public static ConditionGroup or(ConditionSignature... conditions) {
        List<ConditionSignature> conditionsArray = Arrays.asList(conditions);

        ConditionGroup conditionGroup = new ConditionGroup();
        for (int i = 0; i < conditionsArray.size(); i++) {
            conditionGroup.add(ConditionLink.OR, conditionsArray.get(i));
        }

        conditionGroup.generateMetadata();
        return conditionGroup;
    }

    // Static methods built to be less verbose than the readable methods

    public static Condition eq(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.EQUALS;
        return condition;
    }

    public static Condition neq(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.DIFFERENT;
        return condition;
    }

    public static Condition in(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.IN;
        return condition;
    }

    public static Condition isNull(String field) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = null;
        condition.comparison = Comparison.IS_NULL;
        return condition;
    }

    public static Condition isNotNull(String field) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = null;
        condition.comparison = Comparison.IS_NOT_NULL;
        return condition;
    }

    public static Condition like(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.LIKE;
        return condition;
    }

    public static Condition nlike(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.NOT_LIKE;
        return condition;
    }

    public static Condition gt(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.GREATER_THAN;
        return condition;
    }

    public static Condition gte(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.GREATER_THAN_OR_EQUAL;
        return condition;
    }

    public static Condition lt(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.LESS_THAN;
        return condition;
    }

    public static Condition lte(String field, Object value) {
        Condition condition = new Condition();
        condition.column = Column.fromMarkdown(field);
        condition.value = value;
        condition.comparison = Comparison.LESS_THAN_OR_EQUAL;
        return condition;
    }

    // Getters and Setters

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Comparison getComparison() {
        return comparison;
    }

    public void setComparison(Comparison comparison) {
        this.comparison = comparison;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public String getValueAsString() {
        if (value != null) {
            if (value instanceof String) {
                if (value.toString().equals("?")) {
                    return "?";
                } else {
                    return "'" + value + "'";
                }
            } else if (value instanceof List) {
                StringBuilder response = new StringBuilder(CommonStrings.OPEN_PARENTHESES);
                List<Object> list = (List<Object>) value;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    String formattedValue = list.get(i) instanceof String ? "'" + list.get(i) + "'" : list.get(i).toString();
                    if (i == size - 1)
                        response.append(formattedValue);
                    else
                        response.append(formattedValue).append(CommonStrings.COMMA);
                }
                response.append(CommonStrings.CLOSE_PARENTHESES);
                return response.toString();
            } else if (value instanceof SelectBuilder) {
                return CommonStrings.OPEN_PARENTHESES + ((SelectBuilder) value).build() + CommonStrings.CLOSE_PARENTHESES;
            } else if (value instanceof Column) {
                return ((Column) value).getName();
            } else {
                return value.toString();
            }
        } else {
            return "";
        }
    }

    @Override
    public String build() {
        return (" " + this.column.sqlColumnRepresentation() + this.comparison.getValue() + this.getValueAsString() + " ").replaceAll("\\s+", " ");
    }
}
