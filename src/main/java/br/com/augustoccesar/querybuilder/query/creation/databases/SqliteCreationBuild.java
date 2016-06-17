package br.com.augustoccesar.querybuilder.query.creation.databases;

import br.com.augustoccesar.querybuilder.query.creation.CreateColumn;

import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SqliteCreationBuild {
    public static String build(String tableName, List<CreateColumn> createColumns) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ").append(tableName);
        stringBuilder.append(" ( ");
        for (int i = 0; i < createColumns.size(); i++) {
            CreateColumn column = createColumns.get(i);
            if (i == createColumns.size() - 1) {
                stringBuilder.append(String.format("%s %s", column.getName(), column.getType().getValue()));
                if (column.isPrimaryKey())
                    stringBuilder.append(" PRIMARY KEY ");
                else if (column.isUnique())
                    stringBuilder.append(" UNIQUE ");
                if (!column.isNullable())
                    stringBuilder.append(" NOT NULL ");
            } else {
                stringBuilder.append(String.format("%s %s", column.getName(), column.getType().getValue()));
                if (column.isPrimaryKey())
                    stringBuilder.append(" PRIMARY KEY ");
                else if (column.isUnique())
                    stringBuilder.append(" UNIQUE ");
                if (!column.isNullable())
                    stringBuilder.append(" NOT NULL ");
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(" ) ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
