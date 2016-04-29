package br.com.augustoccesar.querybuilder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by augustoccesar on 29/04/2016.
 */
class MapperUtil {
    boolean checkIfColumnExists(String column, ResultSet rs) {
        try {
            rs.findColumn(column);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    String prefixedField(String prefix, String field){
        return prefix == null ? "" + field : prefix + "_" + field;
    }
}
