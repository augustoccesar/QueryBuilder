package se.augustocesar.querybuilder.query;

import se.augustocesar.querybuilder.builders.Buildable;
import se.augustocesar.querybuilder.exceptions.InvalidPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class Table implements Buildable {
    private String name;
    private String alias;

    // Constructors

    public Table(String name) {
        this.name = name;
    }

    public Table(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    // Builders

    public static Table fromMarkdown(String creationString) {
        Pattern patternMarkdown = Pattern.compile("(\\w+)\\{(\\w+)\\}");
        Pattern patternPlain = Pattern.compile("(\\w+)(\\s(\\w+))*");

        Matcher matcherMarkdown = patternMarkdown.matcher(creationString);
        Matcher matcherPlain = patternPlain.matcher(creationString);

        if (matcherMarkdown.matches()) {
            String name = matcherMarkdown.group(1);
            String alias = matcherMarkdown.group(2);

            alias = "_".equals(alias) ? null : alias;

            return new Table(name, alias);
        } else if (matcherPlain.matches()) {
            String name = matcherPlain.group(1);
            String alias = matcherPlain.group(3);

            return new Table(name, alias);
        } else {
            throw new InvalidPattern("Table");
        }
    }

    public static ArrayList<Table> multipleFromMarkdown(String... markedStrings) {
        ArrayList<Table> response = new ArrayList<>();
        Arrays.asList(markedStrings).forEach((markedString) -> response.add(fromMarkdown(markedString)));
        return response;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" ").append(this.name).append(" ");

        if(this.alias != null){
            stringBuilder.append(this.alias).append(" ");
        }

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    public String build(boolean withAlias) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" ").append(this.name).append(" ");

        if(withAlias){
            if(this.alias != null) {
                stringBuilder.append(this.alias);
            }
        }

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }
}
