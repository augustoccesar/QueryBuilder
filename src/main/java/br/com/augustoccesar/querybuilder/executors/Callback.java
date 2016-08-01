package br.com.augustoccesar.querybuilder.executors;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by augustoccesar on 7/12/16.
 */
public interface Callback {
    void execute(ResultSet rs) throws SQLException;
}
