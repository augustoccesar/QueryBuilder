package br.com.augustoccesar.querybuilder.query.update;

/**
 * Created by augustoccesar on 6/17/16.
 */
public class UpdateColumn {
    private String field;
    private Object value;

    // Constructors

    public UpdateColumn() {
    }

    public UpdateColumn(String field) {
        this.field = field;
    }

    public UpdateColumn(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    // Readable methods

    public UpdateColumn column(String column) {
        this.field = column;
        return this;
    }

    public UpdateColumn set(Object value) {
        this.value = value;
        return this;
    }

    // Getters and Setters

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
