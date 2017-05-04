package br.com.augustoccesar.querybuilder.query;

import java.util.Arrays;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class Join {
    public enum Type {
        LEFT_JOIN("LEFT JOIN"),
        RIGHT_JOIN("RIGHT JOIN"),
        INNER_JOIN("INNER JOIN");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Type type;
    public Table table;
    public String leftJoinOn;
    public String rightJoinOn;

    public static final Type INNER = Type.INNER_JOIN;
    public static final Type LEFT = Type.LEFT_JOIN;
    public static final Type RIGHT = Type.RIGHT_JOIN;

    // Constructors

    public Join(Type type) {
        this.type = type;
    }

    public Join(Type type, String tableOrTableMarkdown, String joinOn) {
        this.type = type;
        this.table = new Table(tableOrTableMarkdown);
        this.splitJoinOn(joinOn);
    }

    public Join(Type type, String tableOrTableMarkdown, String leftJoinOn, String rightJoinOn) {
        this.type = type;
        this.table = new Table(tableOrTableMarkdown);
        this.leftJoinOn = leftJoinOn;
        this.rightJoinOn = rightJoinOn;
    }

    // Builder

    public Join table(String tableOrTableMarkdown) {
        this.table = new Table(tableOrTableMarkdown);
        return this;
    }

    public Join on(String joinOn){
        this.splitJoinOn(joinOn);
        return this;
    }

    public Join on(String leftJoinOn, String rightJoinOn) {
        this.leftJoinOn = leftJoinOn;
        this.rightJoinOn = rightJoinOn;
        return this;
    }

    // Helpers

    private void splitJoinOn(String joinOn){
        String[] result = joinOn.split("=");
        Arrays.asList(result).forEach((s) -> s = s.replace(" ", ""));

        this.leftJoinOn = result[0];
        this.rightJoinOn = result[1];
    }
}
