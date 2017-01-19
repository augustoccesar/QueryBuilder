package br.com.augustoccesar.querybuilder.query.aggregation;

/**
 * Created by augustoccesar on 9/12/16.
 */
public class Aggregation {
    private AggregationType aggregationType;
    private String columnName;

    // Methods

    public Aggregation average(String columnName) {
        this.aggregationType = AggregationType.AVG;
        this.columnName = columnName;
        return this;
    }

    public Aggregation count(String columnName) {
        this.aggregationType = AggregationType.COUNT;
        this.columnName = columnName;
        return this;
    }

    public Aggregation max(String columnName) {
        this.aggregationType = AggregationType.MAX;
        this.columnName = columnName;
        return this;
    }

    public Aggregation min(String columnName) {
        this.aggregationType = AggregationType.MIN;
        this.columnName = columnName;
        return this;
    }

    public Aggregation sum(String columnName) {
        this.aggregationType = AggregationType.SUM;
        this.columnName = columnName;
        return this;
    }

    // Getters and Setters


    public AggregationType getAggregationType() {
        return aggregationType;
    }

    public void setAggregationType(AggregationType aggregationType) {
        this.aggregationType = aggregationType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
