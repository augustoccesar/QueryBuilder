package br.com.augustoccesar.querybuilder.query;

import java.util.regex.Pattern;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class Column {
    private String prefix;
    private String name;
    private String customAlias;
    private boolean distinct = false;

    // Constructors

    public Column(String name) {
        this.name = name;
    }

    public Column(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    public Column(String prefix, String name, String customAlias) {
        this.name = name;
        this.prefix = prefix;
        this.customAlias = customAlias;
    }

    // Builder

    public Column fromMarkdown(String columnMarkdown) {
        Pattern markdownWithTableAlias = Pattern.compile("\\{(\\w+)\\}(\\w+)");
        Pattern markdownWithCustomAlias = Pattern.compile("(\\w+)\\{(\\w+)\\}");
        Pattern fullMarkdown = Pattern.compile("\\{(\\w+)\\}\\w+\\{(\\w+)\\}");

//        return new Column();
        return null;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public boolean isDistinct() {
        return distinct;
    }
}
