package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;

import java.util.ArrayList;
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
    private String nestedLink;

    private List<Condition> nestedConditions;

    // Build for old versions compatibility

    public Condition() {
    }

    // Constructors

    public Condition(String field, Comparisons comparison, Object value) {
        this.field = field;
        this.comparison = comparison;
        this.value = value;
    }

    public Condition(String nestedLink, String field, Comparisons comparison, Object value) {
        this.nestedLink = nestedLink;
        this.field = field;
        this.comparison = comparison;
        this.value = value;
    }

    public Condition(String nestedLink, String field, Comparisons comparison, Object value, List<Condition> nestedConditions) {
        this.nestedLink = nestedLink;
        this.field = field;
        this.comparison = comparison;
        this.value = value;
        this.nestedConditions = nestedConditions;
    }

    public static Condition build(String field, Comparisons comparison, Object value) {
        return new Condition(field, comparison, value);
    }

    // Methods

    public Condition and(Condition condition){
        if(this.nestedConditions == null){
            this.nestedConditions = new ArrayList<>();
        }
        this.nestedConditions.add(new Condition(Condition.AND, condition.getField(), condition.getComparison(), condition.getValue(), condition.getNestedConditions()));
        return this;
    }

    public Condition or(Condition condition){
        if(this.nestedConditions == null){
            this.nestedConditions = new ArrayList<>();
        }
        this.nestedConditions.add(new Condition(Condition.OR, condition.getField(), condition.getComparison(), condition.getValue(), condition.getNestedConditions()));
        return this;
    }

    // Readable methods

    public Condition column(String field) {
        this.field = field;
        return this;
    }

    public Condition isEqualsTo(Object value) {
        this.value = value;
        this.comparison = Comparisons.EQUALS;
        return this;
    }

    public Condition isIn(Object value) {
        this.value = value;
        this.comparison = Comparisons.IN;
        return this;
    }

    public Condition isNull() {
        this.value = null;
        this.comparison = Comparisons.IS_NULL;
        return this;
    }

    public Condition isNotNull() {
        this.value = null;
        this.comparison = Comparisons.IS_NOT_NULL;
        return this;
    }

    public Condition isLike(Object value) {
        this.value = value;
        this.comparison = Comparisons.LIKE;
        return this;
    }

    public Condition isNotLike(Object value) {
        this.value = value;
        this.comparison = Comparisons.NOT_LIKE;
        return this;
    }

    public Condition isDifferentThan(Object value) {
        this.value = value;
        this.comparison = Comparisons.DIFFERENT;
        return this;
    }

    public Condition isGreaterThan(Object value) {
        this.value = value;
        this.comparison = Comparisons.GREATER_THAN;
        return this;
    }

    public Condition isGreaterThanOrEqualTo(Object value) {
        this.value = value;
        this.comparison = Comparisons.GREATER_THAN_OR_EQUAL;
        return this;
    }

    public Condition isLessThan(Object value) {
        this.value = value;
        this.comparison = Comparisons.LESS_THAN;
        return this;
    }

    public Condition isLessThanOrEqualTo(Object value) {
        this.value = value;
        this.comparison = Comparisons.LESS_THAN_OR_EQUAL;
        return this;
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

    public String getNestedLink() {
        return nestedLink;
    }

    public void setNestedLink(String nestedLink) {
        this.nestedLink = nestedLink;
    }

    public List<Condition> getNestedConditions() {
        return nestedConditions;
    }

    public void setNestedConditions(List<Condition> nestedConditions) {
        this.nestedConditions = nestedConditions;
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
            } else if (value instanceof QueryBuilder) {
                return " ( " + ((QueryBuilder) value).build() + " ) ";
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
