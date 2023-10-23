package se.augustoccesar.querybuilder.query;

import se.augustoccesar.querybuilder.builders.Buildable;
import se.augustoccesar.querybuilder.constants.CommonStrings;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class Aggregation implements Buildable {
    enum Type {
        AVERAGE(" AVG "),
        COUNT(" COUNT "),
        MAX(" MAX "),
        MIN(" MIN "),
        SUM(" SUM ");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Type AVERAGE = Type.AVERAGE;
    public static Type COUNT = Type.COUNT;
    public static Type MAX = Type.MAX;
    public static Type MIN = Type.MIN;
    public static Type SUM = Type.SUM;

    // Attributes

    private Type type;
    private Column column;
    private String alias;

    // Constructors

    private Aggregation(String markedColumn, Type type) {
        this.type = type;
        this.column = Column.fromMarkdown(markedColumn);
        this.alias = this.type.value.toLowerCase().replaceAll("\\s", "") + "_" + this.column.getAlias();
    }

    private Aggregation(String markedColumn, Type type, String alias) {
        this.type = type;
        this.column = Column.fromMarkdown(markedColumn);
        this.alias = alias;
    }

    // Methods

    public static Aggregation average(String markedColumn) {
        return new Aggregation(markedColumn, Type.AVERAGE);
    }

    public static Aggregation average(String markedColumn, String alias) {
        return new Aggregation(markedColumn, Type.AVERAGE, alias);
    }

    public static Aggregation count(String markedColumn) {
        return new Aggregation(markedColumn, Type.COUNT);
    }

    public static Aggregation count(String markedColumn, String alias) {
        return new Aggregation(markedColumn, Type.COUNT, alias);
    }

    public static Aggregation max(String markedColumn) {
        return new Aggregation(markedColumn, Type.MAX);
    }

    public static Aggregation max(String markedColumn, String alias) {
        return new Aggregation(markedColumn, Type.MAX, alias);
    }

    public static Aggregation min(String markedColumn) {
        return new Aggregation(markedColumn, Type.MIN);
    }

    public static Aggregation min(String markedColumn, String alias) {
        return new Aggregation(markedColumn, Type.MIN, alias);
    }

    public static Aggregation sum(String markedColumn) {
        return new Aggregation(markedColumn, Type.SUM);
    }

    public static Aggregation sum(String markedColumn, String alias) {
        return new Aggregation(markedColumn, Type.SUM, alias);
    }

    // Overrides

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(" ")
                .append(this.type.getValue())
                .append(CommonStrings.OPEN_PARENTHESES)
                .append(this.column.getPrefix()).append(".").append(this.column.getName())
                .append(CommonStrings.CLOSE_PARENTHESES)
                .append(CommonStrings.AS)
                .append(this.alias)
                .append(" ");

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    // Getters and Setters

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
