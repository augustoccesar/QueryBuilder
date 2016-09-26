package br.com.augustoccesar.querybuilder.query;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class Join {
    public String type;
    public String tableAndPrefix;
    public String joinOn;

    // Constructors

    public Join(Type type) {
        this.type = type.getValue();
    }

    public Join(Type type, String tableAndPrefix, String joinOn) {
        this.type = type.getValue();
        this.tableAndPrefix = tableAndPrefix;
        this.joinOn = joinOn;
    }

    // Builders

    public static Join build(Type type) {
        return new Join(type);
    }

    public static Join build(Type type, String tableAndPrefix, String joinOn) {
        return new Join(type, tableAndPrefix, joinOn);
    }

    // Readable methods

    public Join table(String tableAndPrefix) {
        this.tableAndPrefix = tableAndPrefix;
        return this;
    }

    public Join on(String joinOn) {
        this.joinOn = joinOn;
        return this;
    }

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
}
