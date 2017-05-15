package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.builders.Buildable;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class Order implements Buildable{
    public enum Type {
        ASC(" ASC "),
        DESC(" DESC ");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Static Type access

    public static Type ASC = Type.ASC;
    public static Type DESC = Type.DESC;

    // Attributes

    private Column column;
    private Type type;

    // Constructors

    private Order(String columnMarked, Type type) {
        this.column = Column.fromMarkdown(columnMarked);
        this.type = type;
    }

    // Builders

    public static Order by(String columnMarked, Type type) {
        return new Order(columnMarked, type);
    }

    public static Order asc(String columnMarked) {
        return new Order(columnMarked, Type.ASC);
    }

    public static Order desc(String columnMarked) {
        return new Order(columnMarked, Type.DESC);
    }

    // Implements

    @Override
    public String build() {
        return (" " + this.column.sqlColumnRepresentation() + this.type.value + " ").replaceAll("\\s+", " ");
    }

    // Getters and Setters

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
