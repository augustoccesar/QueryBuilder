package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.exceptions.ColumnWithoutValue;
import br.com.augustoccesar.querybuilder.helpers.ColumnHelper;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;
import br.com.augustoccesar.querybuilder.query.insertion.InsertColumn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class InsertBuilder implements QueryBuilder {
    private List<InsertColumn> insertColumns;
    private String insertTableName;
    private boolean withValues = false;

    public InsertBuilder insert(InsertColumn... insertColumns) {
        if (this.insertColumns == null)
            this.insertColumns = new ArrayList<>();
        Collections.addAll(this.insertColumns, insertColumns);
        return this;
    }

    public InsertBuilder into(String tableName) {
        this.insertTableName = tableName;
        return this;
    }

    public InsertBuilder withValues() {
        this.withValues = true;
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        ListIterator insertColumnsIterator = this.insertColumns.listIterator();

        stringBuilder.append(" INSERT INTO ");
        stringBuilder.append(this.insertTableName);
        stringBuilder.append(" ( ");

        while (insertColumnsIterator.hasNext()) {
            stringBuilder.append(((InsertColumn) insertColumnsIterator.next()).getField());
            if (insertColumnsIterator.hasNext())
                stringBuilder.append(" , ");
            else
                stringBuilder.append(" ");
        }

        stringBuilder.append(" ) ");
        stringBuilder.append(" VALUES ( ");

        insertColumnsIterator = this.insertColumns.listIterator();
        if (!withValues) {
            while (insertColumnsIterator.hasNext()) {
                insertColumnsIterator.next();
                stringBuilder.append(" ? ");
                if (insertColumnsIterator.hasNext())
                    stringBuilder.append(" , ");
                else
                    stringBuilder.append(" ");
            }
        } else {
            while (insertColumnsIterator.hasNext()) {
                InsertColumn insertColumn = (InsertColumn) insertColumnsIterator.next();
                if (insertColumn.getValue() != null) {
                    stringBuilder.append(ColumnHelper.checkValueForInsert(insertColumn.getValue()));
                    if (insertColumnsIterator.hasNext())
                        stringBuilder.append(" , ");
                    else
                        stringBuilder.append(" ");
                } else {
                    try {
                        throw new ColumnWithoutValue(String.format("Column '%s' doesn't have a value.", insertColumn.getField()));
                    } catch (ColumnWithoutValue columnWithoutValue) {
                        columnWithoutValue.printStackTrace();
                    }
                }
            }
        }

        stringBuilder.append(" ) ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
