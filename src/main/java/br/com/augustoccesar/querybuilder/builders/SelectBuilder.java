package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.helpers.ColumnHelper;
import br.com.augustoccesar.querybuilder.helpers.ListHelpers;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;
import br.com.augustoccesar.querybuilder.query.Condition;
import br.com.augustoccesar.querybuilder.query.Join;
import br.com.augustoccesar.querybuilder.query.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SelectBuilder implements QueryBuilder {

    private List<String> fields;
    private List<String> tablesAndPrefixes;
    private List<Join> joins;
    private Long limit;
    private Long offset;
    private List<String> counts;
    private Condition conditionBase;
    private List<Order> orders;

    public SelectBuilder select(String... fields) {
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

    public SelectBuilder count(String field) {
        if(this.counts == null){
            this.counts = new ArrayList<>();
        }
        this.counts.add(field);

        for(int i = 0; i < this.counts.size(); i++){
            String newField = "COUNT(" + this.counts.get(i) + ") AS " + "count_" + ColumnHelper.columnAlias(this.counts.get(i));
            this.counts.set(i, newField);
        }

        return this;
    }

    public SelectBuilder from(String... tableNameAndPrefix) {
        if(this.tablesAndPrefixes == null) {
            this.tablesAndPrefixes = new ArrayList<>();
        }

        this.tablesAndPrefixes.addAll(Arrays.asList(tableNameAndPrefix));
        return this;
    }

    public SelectBuilder join(Join.Type type, String tableNameAndPrefix, String joinOn) {
        if(this.joins == null)
            this.joins = new ArrayList<>();

        this.joins.add(new Join(type, tableNameAndPrefix, joinOn));
        return this;
    }

    public SelectBuilder conditions(Condition conditionBase) {
        this.conditionBase = conditionBase;
        return this;
    }

    public SelectBuilder order(Order... orders) {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }

        this.orders.addAll(Arrays.asList(orders));
        return this;
    }

    public SelectBuilder limit(Long value) {
        this.limit = value;
        return this;
    }

    public SelectBuilder offset(Long value) {
        this.offset = value;
        return this;
    }

    @Override
    public String build() {
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
                            conditionBase.getValue() instanceof QueryBuilder ? " ( " + ((QueryBuilder) conditionBase.getValue()).build() + " ) " : (
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
                                    condition.getValue() instanceof QueryBuilder ? " ( " + ((QueryBuilder) condition.getValue()).build() + " ) " : (
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
}
