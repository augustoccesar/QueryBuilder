package br.com.augustoccesar.querybuilder.query.update;

/**
 * Created by augustoccesar on 6/17/16.
 */
public class UpdateColumn {
    private String field;
    private Object value;

    public UpdateColumn(String field) {
        this.field = field;
    }

    public UpdateColumn(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public static UpdateColumn build(String field) {
        return new UpdateColumn(field);
    }

    public static UpdateColumn build(String field, Object value) {
        return new UpdateColumn(field, value);
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
