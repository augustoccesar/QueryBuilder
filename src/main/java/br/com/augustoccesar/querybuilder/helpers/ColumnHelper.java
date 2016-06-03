package br.com.augustoccesar.querybuilder.helpers;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class ColumnHelper {
    public static String columnAlias(String columnSelection) {
        return columnSelection.replace(".", "_");
    }

    public static String checkValueForInsert(Object value) {
        if (value instanceof String) {
            return "\'" + value + "\'";
        } else {
            return value.toString();
        }
    }
}
