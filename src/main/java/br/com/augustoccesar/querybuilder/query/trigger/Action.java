package br.com.augustoccesar.querybuilder.query.trigger;

/**
 * Created by augustoccesar on 6/17/16.
 */
public enum Action {
    INSERT(" INSERT "),
    UPDATE(" UPDATE "),
    DELETE(" DELETE ");

    private String value;

    Action(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
