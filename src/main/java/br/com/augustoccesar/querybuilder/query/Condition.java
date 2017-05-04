package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.builders.SelectBuilder;

import java.util.List;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class Condition {
    public static final String AND = " AND ";
    public static final String OR = " OR ";

    private String field;
    private Comparisons comparison;
    private Object value;

    // Static methods built to be less verbose than the readable methods

    public static Condition eq(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.EQUALS;
        return condition;
    }

    public static Condition in(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.IN;
        return condition;
    }

    public static Condition isNull(String field) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = null;
        condition.comparison = Comparisons.IS_NULL;
        return condition;
    }

    public static Condition isNotNull(String field) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = null;
        condition.comparison = Comparisons.IS_NOT_NULL;
        return condition;
    }

    public static Condition like(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.LIKE;
        return condition;
    }

    public static Condition nlike(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.NOT_LIKE;
        return condition;
    }

    public static Condition neq(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.DIFFERENT;
        return condition;
    }

    public static Condition gt(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.GREATER_THAN;
        return condition;
    }

    public static Condition gte(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.GREATER_THAN_OR_EQUAL;
        return condition;
    }

    public static Condition lt(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.LESS_THAN;
        return condition;
    }

    public static Condition lte(String field, Object value) {
        Condition condition = new Condition();
        condition.field = field;
        condition.value = value;
        condition.comparison = Comparisons.LESS_THAN_OR_EQUAL;
        return condition;
    }

    // Getters and Setters

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Comparisons getComparison() {
        return comparison;
    }

    public void setComparison(Comparisons comparison) {
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
                    return "\"" + value + "\"";
                }
            } else if (value instanceof List) {
                String response = "(";
                List<Object> list = (List<Object>) value;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (i == size - 1)
                        response += list.get(i).toString();
                    else
                        response += list.get(i).toString() + ", ";
                }
                response += ")";
                return response;
            } else if (value instanceof SelectBuilder) {
                return " ( " + ((SelectBuilder) value).build() + " ) ";
            } else if (value instanceof Column) {
                return ((Column) value).getName();
            } else {
                return value.toString();
            }
        } else {
            return "";
        }
    }
}
