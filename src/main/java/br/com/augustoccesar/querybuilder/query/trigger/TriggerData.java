package br.com.augustoccesar.querybuilder.query.trigger;

/**
 * Created by augustoccesar on 6/17/16.
 */
public class TriggerData {
    private Object object;
    private String field;

    public TriggerData(Object object, String field) {
        this.object = object;
        this.field = field;
    }

    public Object getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public String insertableData() {
        if (this.object == Object.NEW) {
            return "NEW." + field;
        } else if (this.object == Object.OLD) {
            return "OLD." + field;
        } else {
            return null;
        }
    }

    public enum Object {
        NEW(1),
        OLD(2);

        private int id;

        Object(int id) {
            this.id = id;
        }
    }
}
