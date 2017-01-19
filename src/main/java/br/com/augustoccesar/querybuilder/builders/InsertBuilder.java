package br.com.augustoccesar.querybuilder.builders;

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
    /**
     * Attributes
     */
    private List<InsertColumn> insertColumns;
    private String insertTableName;
    private boolean withValues = false;

    /**
     * Method for building the INSERT clause with the columns to be inserted.
     *
     * @param insertColumns Columns to be inserted
     * @return This instance of InsertBuilder.
     */
    public InsertBuilder insert(InsertColumn... insertColumns) {
        if (this.insertColumns == null)
            this.insertColumns = new ArrayList<>();
        Collections.addAll(this.insertColumns, insertColumns);
        return this;
    }

    /**
     * Method for defining the table that the data will be inserted.
     * @param tableName Name of the table.
     * @return This instance of InsertBuilder.
     */
    public InsertBuilder into(String tableName) {
        this.insertTableName = tableName;
        return this;
    }

    /**
     * Method for defining if the insertion will contain the values or '?'
     * @return This instance of InsertBuilder.
     */
    public InsertBuilder withValues() {
        this.withValues = true;
        return this;
    }

    /**
     * Method for generating the SQL statement.
     * @return SQL Statement String.
     */
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
                    // TODO Exception handlers
                    return null;
                }
            }
        }

        stringBuilder.append(" ) ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
