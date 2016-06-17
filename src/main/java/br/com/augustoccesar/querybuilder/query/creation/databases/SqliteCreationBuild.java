package br.com.augustoccesar.querybuilder.query.creation.databases;

import br.com.augustoccesar.querybuilder.builders.TableBuilder;
import br.com.augustoccesar.querybuilder.query.creation.CreateColumn;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SqliteCreationBuild {
    public static String build(TableBuilder tableBuilder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ").append(tableBuilder.getTableName());
        stringBuilder.append(" ( ");
        for (int i = 0; i < tableBuilder.getCreateColumns().size(); i++) {
            CreateColumn column = tableBuilder.getCreateColumns().get(i);
            if (i == tableBuilder.getCreateColumns().size() - 1) {
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
