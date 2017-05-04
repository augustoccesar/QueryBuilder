package br.com.augustoccesar.querybuilder.helpers;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class ColumnHelper {
    public static String columnAlias(String columnSelection) {
        return columnSelection.replace(".", "_");
    }

    public static boolean isAll(String columnSelection) {
        return columnSelection.contains("*");
    }

    public static boolean hasTableName(String columnSelection) {
        return columnSelection.matches("\\w+\\.\\w+");
    }
}
