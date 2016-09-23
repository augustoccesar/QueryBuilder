package br.com.augustoccesar.querybuilder.query;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class Column {
    private Class clazz;
    private Class type;
    private String name;
    private String prefix;

    // Builders

    public Column(String name) {
        this.name = name;
    }

    public Column(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    public Column(Class clazz, Class type, String prefix, String name) {
        this.clazz = clazz;
        this.type = type;
        this.name = name;
        this.prefix = prefix;
    }

    // Constructors

    public static Column build(String name) {
        return new Column(name);
    }

    public static Column build(String prefix, String name) {
        return new Column(prefix, name);
    }

    public static Column build(Class clazz, Class type, String prefix, String name) {
        return new Column(clazz, type, prefix, name);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

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
