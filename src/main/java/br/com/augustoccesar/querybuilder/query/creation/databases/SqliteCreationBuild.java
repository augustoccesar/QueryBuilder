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
            if (i == createColumns.size() - 1)
                stringBuilder.append(String.format("%s %s", createColumns.get(i).getName(), createColumns.get(0).getType().getValue()));
            else
                stringBuilder
                        .append(String.format("%s %s", createColumns.get(i).getName(), createColumns.get(0).getType().getValue()))
                        .append(", ");
        }
        stringBuilder.append(" ) ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
