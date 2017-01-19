package br.com.augustoccesar.querybuilder.query;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class Order {
    private String field;
    private Type type;

    public Order by(String column, Type type) {
        this.field = column;
        this.type = type;
        return this;
    }

    // Readable methods

    public String getField() {
        return field;
    }

    // Getters and Setters

    public void setField(String field) {
        this.field = field;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        ASC(" ASC "),
        DESC(" DESC ");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
