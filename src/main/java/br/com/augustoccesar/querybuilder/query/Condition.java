package br.com.augustoccesar.querybuilder.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class Condition {
    public enum Comparisons{
        EQUALS(" = "),
        IN(" IN "),
        IS_NULL(" IS NULL "),
        IS_NOT_NULL(" IS NOT NULL "),
        LIKE(" LIKE "),
        NOT_LIKE(" NOT LIKE "),
        DIFFERENT(" <> "),
        GREATER_THAN(" > "),
        GREATER_THAN_OR_EQUAL(" >= "),
        LESS_THAN(" < "),
        LESS_THAN_OR_EQUAL(" <= ");

        private final String value;

        Comparisons(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String field;
    private Comparisons comparison;
    private Object value;
    private String nestedLink;

    private List<Condition> nestedConditions;

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

    // Builder

    public static Condition build(String field, Comparisons comparison, Object value){
        return new Condition(field, comparison, value);
    }

    // Methods

    public Condition and(Condition condition){
        if(this.nestedConditions == null){
            this.nestedConditions = new ArrayList<>();
        }
        this.nestedConditions.add(new Condition(" AND ", condition.getField(), condition.getComparison(), condition.getValue()));
        return this;
    }

    public Condition or(Condition condition){
        if(this.nestedConditions == null){
            this.nestedConditions = new ArrayList<>();
        }
        this.nestedConditions.add(new Condition(" OR ", condition.getField(), condition.getComparison(), condition.getValue()));
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
}
