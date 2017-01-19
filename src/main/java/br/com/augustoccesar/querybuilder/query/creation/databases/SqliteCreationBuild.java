package br.com.augustoccesar.querybuilder.query.creation.databases;

import br.com.augustoccesar.querybuilder.builders.TableBuilder;
import br.com.augustoccesar.querybuilder.query.creation.CreateColumn;
import br.com.augustoccesar.querybuilder.query.creation.ForeignKey;

import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SqliteCreationBuild {
    public static String build(TableBuilder tableBuilder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" CREATE TABLE ").append(tableBuilder.getTableName());
        stringBuilder.append(" ( ");

        List<CreateColumn> createColumns = tableBuilder.getCreateColumns();
        List<ForeignKey> foreignKeys = tableBuilder.getForeignKeys();

        for (int i = 0; i < createColumns.size(); i++) {
            CreateColumn column = createColumns.get(i);
            stringBuilder.append(column.getName()).append(" ").append(column.getType().getValue());
            if (column.isPrimaryKey())
                stringBuilder.append(" PRIMARY KEY ");
            else if (column.isUnique())
                stringBuilder.append(" UNIQUE ");
            if (!column.isNullable())
                stringBuilder.append(" NOT NULL ");

            if (i < createColumns.size() - 1 || (foreignKeys != null && foreignKeys.size() > 0))
                stringBuilder.append(" , ");
        }

        if (foreignKeys != null) {
            for (int i = 0; i < foreignKeys.size(); i++) {
                ForeignKey foreignKey = foreignKeys.get(i);
                stringBuilder.append(" FOREIGN KEY ( ").append(foreignKey.getColumnName()).append(" ) ");
                stringBuilder.append(" REFERENCES ").append(foreignKey.getReferencesTable()).append(" ( ").append(foreignKey.getReferencesColumn()).append(" ) ");
                if (i < foreignKeys.size() - 1)
                    stringBuilder.append(" , ");
            }
        }

        stringBuilder.append(" ) ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
