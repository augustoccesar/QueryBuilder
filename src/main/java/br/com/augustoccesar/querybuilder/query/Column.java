package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.builders.Buildable;
import br.com.augustoccesar.querybuilder.constants.CommonStrings;
import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class Column implements Buildable{
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

            if("_".equals(prefix)){
                return new Column(name, distinct);
            }else {
                return new Column(prefix, name, distinct);
            }
        }else if(fullMarkdownMatcher.matches()){
            String prefix = fullMarkdownMatcher.group(1);
            String name = fullMarkdownMatcher.group(2);
            String customAlias = fullMarkdownMatcher.group(3);
            boolean distinct = false;

            prefix = "_".equals(prefix) ? null : prefix;
            customAlias = "_".equals(customAlias) ? null : customAlias;

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

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        if(this.distinct) {
            stringBuilder.append(CommonStrings.DISTINCT);
        }

        if(this.prefix != null){
            stringBuilder.append(this.prefix).append(".");
        }

        stringBuilder.append(this.name);

        if(this.alias != null){
            stringBuilder.append(CommonStrings.AS).append(this.alias);
        }

        return stringBuilder.toString();
    }

    @Override
    public String build(boolean withDistinct, boolean withAlias) {
        StringBuilder stringBuilder = new StringBuilder();

        if(withDistinct) {
            stringBuilder.append(CommonStrings.DISTINCT);
        }

        if(this.prefix != null){
            stringBuilder.append(this.prefix).append(".");
        }

        stringBuilder.append(this.name);

        if(withAlias){
            stringBuilder.append(CommonStrings.AS).append(this.alias);
        }

        return stringBuilder.toString();
    }
}
