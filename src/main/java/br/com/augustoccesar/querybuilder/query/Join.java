package br.com.augustoccesar.querybuilder.query;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class Join {
    public String type;
    public String tableAndPrefix;
    public String joinOn;

    public Join(Type type, String tableAndPrefix, String joinOn) {
        this.type = type.getValue();
        this.tableAndPrefix = tableAndPrefix;
        this.joinOn = joinOn;
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
