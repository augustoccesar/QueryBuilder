package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.builders.Buildable;
import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import br.com.augustoccesar.querybuilder.constants.CommonStrings;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class Union implements Buildable{
    public enum Type {
        UNION_ALL(CommonStrings.UNION_ALL),
        UNION(CommonStrings.UNION);

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Type UNION = Type.UNION;
    public static Type UNION_ALL = Type.UNION_ALL;

    // Attributes

    private Type type;
    private SelectBuilder selectBuilder;

    // Constructors

    public Union(Type type, SelectBuilder selectBuilder) {
        this.type = type;
        this.selectBuilder = selectBuilder;
    }

    // Builders

    public static Union union(SelectBuilder selectBuilder) {
        return new Union(Type.UNION, selectBuilder);
    }

    public static Union unionAll(SelectBuilder selectBuilder) {
        return new Union(Type.UNION_ALL, selectBuilder);
    }

    // Overrides

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.type.getValue());
        stringBuilder.append(CommonStrings.OPEN_PARENTHESES);
        stringBuilder.append(this.selectBuilder.build());
        stringBuilder.append(CommonStrings.CLOSE_PARENTHESES);

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    // Getters and Setters

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SelectBuilder getSelectBuilder() {
        return selectBuilder;
    }

    public void setSelectBuilder(SelectBuilder selectBuilder) {
        this.selectBuilder = selectBuilder;
    }
}
