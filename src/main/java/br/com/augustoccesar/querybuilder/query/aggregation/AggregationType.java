package br.com.augustoccesar.querybuilder.query.aggregation;

/**
 * Created by augustoccesar on 9/12/16.
 */
public enum AggregationType {
    AVG(1, "AVG"),
    COUNT(2, "COUNT"),
    MAX(3, "MAX"),
    MIN(4, "MIN"),
    SUM(5, "SUM");

    private int id;
    private String command;

    AggregationType(int id, String command) {
        this.id = id;
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public String getCommand() {
        return command;
    }
}
