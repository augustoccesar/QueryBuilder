package br.com.augustoccesar.querybuilder.query.creation;

import br.com.augustoccesar.querybuilder.configurations.Configuration;
import br.com.augustoccesar.querybuilder.configurations.Databases;

/**
 * Created by augustoccesar on 6/13/16.
 */
public enum ColumnTypes {
    VARCHAR(1),
    INTEGER(2);

    private int id;
    private String value;

    ColumnTypes(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        if (Configuration.getDatabase() == Databases.SQLITE) {
            switch (id) {
                case 1:
                    return " TEXT ";
                case 2:
                    return " INTEGER ";
                default:
                    return null;
            }
        } else {
            // TODO create other databases constants
            return null;
        }
    }
}
