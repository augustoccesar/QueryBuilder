package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.configurations.Configuration;
import br.com.augustoccesar.querybuilder.configurations.Databases;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;
import br.com.augustoccesar.querybuilder.query.creation.CreateColumn;
import br.com.augustoccesar.querybuilder.query.creation.databases.SqliteCreationBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class CreateTableBuilder implements QueryBuilder {
    private String tableName;
    private List<CreateColumn> createColumns;

    public CreateTableBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public CreateTableBuilder columns(CreateColumn... createColumns) {
        if (this.createColumns == null)
            this.createColumns = new ArrayList<>();
        Collections.addAll(this.createColumns, createColumns);
        return this;
    }

    @Override
    public String build() {
        if (Configuration.getDatabase() == Databases.SQLITE) {
            return SqliteCreationBuild.build(this.tableName, this.createColumns);
        } else {
            return null;
        }
    }
}
