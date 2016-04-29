package br.com.augustoccesar.querybuilder.query;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class Order {
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

    private String field;
    private Type type;

    // Constructors

    public Order(String field, Type type) {
        this.field = field;
        this.type = type;
    }

    // Builders

    public static Order build(String field, Type type) {
        return new Order(field, type);
    }

    // Getters and Setters

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
