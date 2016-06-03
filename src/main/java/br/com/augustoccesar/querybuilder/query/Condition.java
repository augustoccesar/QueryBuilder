package br.com.augustoccesar.querybuilder.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class Condition {
    public static final String AND = "AND";
    public static final String OR = "OR";

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
        if (value instanceof Comparisons) {
            if (((Comparisons) value).getValue().equals(Comparisons.VARIABLE.getValue())) {
                return new Condition(field, comparison, Comparisons.VARIABLE.getValue());
            }
        }
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
