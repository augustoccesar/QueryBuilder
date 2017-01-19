package br.com.augustoccesar.querybuilder.executors.mapper;

import br.com.augustoccesar.querybuilder.helpers.StringHelper;
import br.com.augustoccesar.querybuilder.query.Column;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class ResultSetExtractor {
    private ResultSet resultSet;

    public ResultSetExtractor(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public <T> T extractClass(String prefix, Class<T> clazz) throws SQLException {
        Constructor constructor = null;
        for (Constructor ctr : clazz.getDeclaredConstructors()) {
            constructor = ctr;
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }

        try {
            assert constructor != null;
            constructor.setAccessible(true);
            Object response = constructor.newInstance();

            T responseObject = clazz.cast(response);

            for (Column column : EntityReader.getColumns(prefix, clazz)) {
                Field field = response.getClass().getDeclaredField(column.getName());
                String queryColumn = column.getPrefix() + "_" + StringHelper.camelToSnakeCase(column.getName()); // TODO generic type of alias

                if (checkIfColumnExists(queryColumn, resultSet)) {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    field.set(responseObject, getResultSetByType(column, resultSet));
                    field.setAccessible(accessible);
                }
            }

            return responseObject;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T extractClasses(Class<T> baseClass, QueryRelation queryRelation) throws SQLException {
        Object base = extractClass(queryRelation.getParentClass().getPrefix(), queryRelation.getParentClass().getClazz());

        T baseResponse = baseClass.cast(base);

        for (String key : queryRelation.getIncludes().keySet()) {
            ClassTable classTable = queryRelation.getIncludes().get(key);
            Object includeObj = extractClass(classTable.getPrefix(), classTable.getClazz());

            try {
                Field field = base.getClass().getDeclaredField(key);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(baseResponse, includeObj);
                field.setAccessible(accessible);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return baseResponse;
    }

    public Object getResultSetByType(Column column, ResultSet rs) throws SQLException {
        Class type = column.getType();
        String columnString = column.getPrefix() + "_" + StringHelper.camelToSnakeCase(column.getName()); // TODO Transform to generic alias

        if (type == Long.class) {
            return rs.getLong(columnString);
        } else if (type == Integer.class) {
            return rs.getInt(columnString);
        } else if (type == String.class) {
            return rs.getString(columnString);
        } else if (type == Boolean.class) {
            return rs.getBoolean(columnString);
        } else {
            System.err.println("Invalid type.");
        }

        return null;
    }

    boolean checkIfColumnExists(String column, ResultSet rs) {
        try {
            rs.findColumn(column);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
}
