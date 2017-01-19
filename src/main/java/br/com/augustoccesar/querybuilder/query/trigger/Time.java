package br.com.augustoccesar.querybuilder.query.trigger;

/**
 * Created by augustoccesar on 6/17/16.
 */
public enum Time {
    BEFORE(" BEFORE "),
    AFTER(" AFTER ");

    private String value;

    Time(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
