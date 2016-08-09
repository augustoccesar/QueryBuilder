package br.com.augustoccesar.querybuilder.executors.mapper;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class Column {
    private Class clazz;
    private Class type;
    private String name;
    private String prefix;

    public Column(Class clazz, Class type, String prefix, String name) {
        this.clazz = clazz;
        this.type = type;
        this.name = name;
        this.prefix = prefix;
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
