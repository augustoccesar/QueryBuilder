package se.augustoccesar.querybuilder.query;

import se.augustoccesar.querybuilder.builders.Buildable;
import se.augustoccesar.querybuilder.constants.CommonStrings;

import java.util.Arrays;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class Join implements Buildable {
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

    private Type type;
    private Table table;
    private Column leftJoinOn;
    private Column rightJoinOn;

    public static final Type INNER = Type.INNER_JOIN;
    public static final Type LEFT = Type.LEFT_JOIN;
    public static final Type RIGHT = Type.RIGHT_JOIN;

    // Constructors

    public Join(Type type) {
        this.type = type;
    }

    public Join(Type type, String tableOrTableMarkdown, String joinOn) {
        this.type = type;
        this.table = Table.fromMarkdown(tableOrTableMarkdown);
        this.splitJoinOn(joinOn);
    }

    public Join(Type type, String tableOrTableMarkdown, String leftJoinOn, String rightJoinOn) {
        this.type = type;
        this.table = Table.fromMarkdown(tableOrTableMarkdown);
        this.leftJoinOn = Column.fromMarkdown(leftJoinOn);
        this.rightJoinOn = Column.fromMarkdown(rightJoinOn);
    }

    // Builder

    public Join table(String tableOrTableMarkdown) {
        this.table = Table.fromMarkdown(tableOrTableMarkdown);
        return this;
    }

    public Join on(String joinOn){
        this.splitJoinOn(joinOn);
        return this;
    }

    public Join on(String leftJoinOn, String rightJoinOn) {
        this.leftJoinOn = Column.fromMarkdown(leftJoinOn);
        this.rightJoinOn = Column.fromMarkdown(rightJoinOn);
        return this;
    }

    // Helpers

    private void splitJoinOn(String joinOn){
        String[] result = joinOn.split("=");
        Arrays.asList(result).forEach((s) -> s = s.replace(" ", ""));

        this.leftJoinOn = Column.fromMarkdown(result[0].replace(" ", ""));
        this.rightJoinOn = Column.fromMarkdown(result[1].replace(" ", ""));
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" ")
                .append(this.type.value).append(" ")
                .append(this.table.build()).append(CommonStrings.ON)
                .append(this.leftJoinOn.sqlColumnRepresentation()).append(Comparison.EQUALS.getValue()).append(this.rightJoinOn.sqlColumnRepresentation())
                .append(" ");

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }
}
