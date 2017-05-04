package br.com.augustoccesar.querybuilder.query;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class Column {
    private String name;
    private String prefix;

    // Constructors

    public Column(String name) {
        this.name = name;
    }

    public Column(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
