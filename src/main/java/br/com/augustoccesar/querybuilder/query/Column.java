package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class Column {
    private String prefix;
    private String name;
    private String alias;
    private boolean distinct = false;

    // Constructors

    public Column(String name, boolean distinct) {
        this.name = name;
        this.distinct = distinct;
    }

    public Column(String prefix, String name, boolean distinct) {
        this.prefix = prefix;
        this.name = name;
        this.alias = prefix + "_" + name;
        this.distinct = distinct;
    }

    public Column(String prefix, String name, String customAlias, boolean distinct) {
        this.name = name;
        this.prefix = prefix;
        this.alias = customAlias;
        this.distinct = distinct;
    }

    // Builder

    public static Column fromMarkdown(String columnMarkdown) {
        Pattern markdownWithTableAlias = Pattern.compile("\\{(\\w+)\\}(\\**\\w+)");
        Pattern fullMarkdown = Pattern.compile("\\{(\\w+)\\}(\\**\\w+)\\{(\\w+)\\}");

        Matcher markdownWithTableAliasMatcher = markdownWithTableAlias.matcher(columnMarkdown);
        Matcher fullMarkdownMatcher = fullMarkdown.matcher(columnMarkdown);

        if(markdownWithTableAliasMatcher.matches()){
            String prefix = markdownWithTableAliasMatcher.group(1);
            String name = markdownWithTableAliasMatcher.group(2);
            boolean distinct = false;

            if (name.contains("*")){
                name = name.replace("*", "");
                distinct = true;
            }

            if(prefix.equals("_")){
                return new Column(name, distinct);
            }else {
                return new Column(prefix, name, distinct);
            }
        }else if(fullMarkdownMatcher.matches()){
            String prefix = fullMarkdownMatcher.group(1);
            String name = fullMarkdownMatcher.group(2);
            String customAlias = fullMarkdownMatcher.group(3);
            boolean distinct = false;

            prefix = prefix.equals("_") ? null : prefix;
            customAlias = customAlias.equals("_") ? null : customAlias;

            if (name.contains("*")){
                name = name.replace("*", "");
                distinct = true;
            }

            return new Column(prefix, name, customAlias, distinct);
        }else{
            throw new InvalidPattern("Column");
        }
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isDistinct() {
        return distinct;
    }
}
