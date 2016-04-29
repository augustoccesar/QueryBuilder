package br.com.augustoccesar.querybuilder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by augustoccesar on 29/04/2016.
 */
public interface Mapper<T> {
    T readFromResultSet(String prefix, ResultSet rs) throws SQLException;
}
