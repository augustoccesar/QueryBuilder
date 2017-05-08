package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class Table {
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

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
}
