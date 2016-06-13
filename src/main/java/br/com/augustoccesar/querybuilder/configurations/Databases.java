package br.com.augustoccesar.querybuilder.configurations;

/**
 * Created by augustoccesar on 6/13/16.
 */
public enum Databases {
    SQLITE(1, "SQLITE");

    private int id;
    private String value;

    Databases(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
