package br.com.augustoccesar.querybuilder.query.creation;

import br.com.augustoccesar.querybuilder.configurations.Configuration;
import br.com.augustoccesar.querybuilder.configurations.Database;

/**
 * Created by augustoccesar on 6/13/16.
 */
public enum ColumnType {
    VARCHAR(1),
    INTEGER(2);

    private int id;
    private String value;

    ColumnType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        if (Configuration.getDatabase() == Database.SQLITE) {
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
