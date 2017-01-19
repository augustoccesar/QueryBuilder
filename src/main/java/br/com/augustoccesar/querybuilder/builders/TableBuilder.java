package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.configurations.Configuration;
import br.com.augustoccesar.querybuilder.configurations.Database;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;
import br.com.augustoccesar.querybuilder.query.creation.CreateColumn;
import br.com.augustoccesar.querybuilder.query.creation.ForeignKey;
import br.com.augustoccesar.querybuilder.query.creation.databases.SqliteCreationBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class TableBuilder implements QueryBuilder {
    private String tableName;
    private List<CreateColumn> createColumns;
    private List<ForeignKey> foreignKeys;

    public TableBuilder withName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public TableBuilder columns(CreateColumn... createColumns) {
        if (this.createColumns == null)
            this.createColumns = new ArrayList<>();
        Collections.addAll(this.createColumns, createColumns);
        return this;
    }

    public TableBuilder foreignKeys(ForeignKey... foreignKeys) {
        if (this.foreignKeys == null)
            this.foreignKeys = new ArrayList<>();
        Collections.addAll(this.foreignKeys, foreignKeys);
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public List<CreateColumn> getCreateColumns() {
        return createColumns;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    @Override
    public String build() {
        if (Configuration.getDatabase().equals(Database.SQLITE)) {
            return SqliteCreationBuild.build(this);
        } else {
            return null;
        }
    }
}
