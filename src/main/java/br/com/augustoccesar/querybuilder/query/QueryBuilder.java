package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.exceptions.ColumnWithoutValue;
import br.com.augustoccesar.querybuilder.helpers.ColumnHelper;
import br.com.augustoccesar.querybuilder.helpers.ListHelpers;

import java.util.*;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class QueryBuilder {
    private boolean isInsert;

    private List<String> fields;
    private List<String> tablesAndPrefixes;
    private List<Join> joins;
    private Long limit;
    private Long offset;
    private List<String> counts;
    private Condition conditionBase;
    private List<Order> orders;

    private List<InsertColumn> insertColumns;
    private String insertTableName;
    private boolean withValues = false;

    // Methods

    public QueryBuilder insert(InsertColumn... insertColumns) {
        this.isInsert = true;
        if (this.insertColumns == null)
            this.insertColumns = new ArrayList<>();
        Collections.addAll(this.insertColumns, insertColumns);
        return this;
    }

    public QueryBuilder into(String tableName) {
        this.insertTableName = tableName;
        return this;
    }

    public QueryBuilder withValues() {
        this.withValues = true;
        return this;
    }

    public QueryBuilder select(String... fields){
        this.isInsert = false;
        if(this.fields == null){
            this.fields = new ArrayList<>();
        }
        this.fields.addAll(Arrays.asList(fields));

        for(int i = 0; i < this.fields.size(); i++){
            String newField = this.fields.get(i) + " AS " + ColumnHelper.columnAlias(this.fields.get(i));
            this.fields.set(i, newField);
        }

        return this;
    }

    public QueryBuilder count(String field) {
        if(this.counts == null){
            this.counts = new ArrayList<>();
        }
//        this.counts.addAll(Arrays.asList(fields));
        this.counts.add(field);

        for(int i = 0; i < this.counts.size(); i++){
            String newField = "COUNT(" + this.counts.get(i) + ") AS " + "count_" + ColumnHelper.columnAlias(this.counts.get(i));
            this.counts.set(i, newField);
        }

        return this;
    }

    public QueryBuilder from(String... tableNameAndPrefix){
        if(this.tablesAndPrefixes == null) {
            this.tablesAndPrefixes = new ArrayList<>();
        }

        this.tablesAndPrefixes.addAll(Arrays.asList(tableNameAndPrefix));
        return this;
    }

    public QueryBuilder join(Join.Type type, String tableNameAndPrefix, String joinOn) {
        if(this.joins == null)
            this.joins = new ArrayList<>();

        this.joins.add(new Join(type, tableNameAndPrefix, joinOn));
        return this;
    }

    public QueryBuilder conditions(Condition conditionBase){
        this.conditionBase = conditionBase;
        return this;
    }

    public QueryBuilder order(Order... orders) {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }

        this.orders.addAll(Arrays.asList(orders));
        return this;
    }

    public QueryBuilder limit(Long value){
        this.limit = value;
        return this;
    }

    public QueryBuilder offset(Long value ){
        this.offset = value;
        return this;
    }

    public String build() {
        if (!this.isInsert) {
            return buildSelect();
        } else {
            try {
                return buildInsert();
            } catch (ColumnWithoutValue columnWithoutValue) {
                columnWithoutValue.printStackTrace();
                return null;
            }
        }
    }

    private String buildSelect() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" SELECT ");
        if(fields != null && fields.size() > 0) {
            ListHelpers.runListIterator(stringBuilder, fields.listIterator(), ",");
            if(counts != null && counts.size() > 0)
                stringBuilder.append(", ");
        }

        if(counts != null && counts.size() > 0)
            ListHelpers.runListIterator(stringBuilder, counts.listIterator(), "");

        stringBuilder.append(" FROM ");
        ListHelpers.runListIterator(stringBuilder, tablesAndPrefixes.listIterator(), ",");

        if(joins != null && joins.size() > 0){
            List<String> joinStrings = new ArrayList<>();
            for(Join join : joins){
                joinStrings.add(join.type + " " + join.tableAndPrefix + " ON " + join.joinOn);
            }
            ListHelpers.runListIterator(stringBuilder, joinStrings.listIterator(), null);
        }

        if(this.conditionBase != null){
            stringBuilder.append(" WHERE ");

            stringBuilder.append(conditionBase.getField())
                    .append(conditionBase.getComparison().getValue())
                    .append(
                            conditionBase.getValue() instanceof QueryBuilder ? " ( " + ((QueryBuilder) conditionBase.getValue()).buildSelect() + " ) " : (
                                    conditionBase.getValue() == null ? "" : (
                                            conditionBase.getValue() instanceof String && !conditionBase.getValue().equals("?") ? "\"" + conditionBase.getValue() + "\"" : conditionBase.getValue().toString()
                                    )
                            )
                    );

            if(this.conditionBase.getNestedConditions() != null && this.conditionBase.getNestedConditions().size() > 0){
                for(Condition condition : this.conditionBase.getNestedConditions()){
                    stringBuilder.append(condition.getNestedLink())
                            .append(condition.getField())
                            .append(condition.getComparison().getValue())
                            .append(
                                    condition.getValue() instanceof QueryBuilder ? " ( " + ((QueryBuilder) condition.getValue()).buildSelect() + " ) " : (
                                            condition.getValue() == null ? "" : (
                                                    condition.getValue().equals("?") ? condition.getValue().toString() : ColumnHelper.checkValueForInsert(condition.getValue())
                                            )
                                    )
                            );
                }
            }
        }

        if (this.orders != null && this.orders.size() > 0) {
            stringBuilder.append(" ORDER BY ");
            for (int i = 0; i < this.orders.size(); i++) {
                stringBuilder
                        .append(this.orders.get(i).getField())
                        .append(this.orders.get(i).getType().getValue());
                if (!(i == this.orders.size() - 1)) {
                    stringBuilder.append(", ");
                }
            }
        }

        if(limit != null){
            stringBuilder.append(" LIMIT ").append(limit).append(" ");
        }

        if(offset != null){
            stringBuilder.append(" OFFSET ").append(offset).append(" ");
        }

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }

    private String buildInsert() throws ColumnWithoutValue {
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
                    throw new ColumnWithoutValue(String.format("Column '%s' doesn't have a value.", insertColumn.getField()));
                }
            }
        }

        stringBuilder.append(" ) ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
