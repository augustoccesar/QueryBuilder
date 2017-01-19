package br.com.augustoccesar.querybuilder.query.insertion;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class InsertColumn {
    private String field;
    private Object value;

    public InsertColumn(String field) {
        this.field = field;
    }

    public InsertColumn(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
