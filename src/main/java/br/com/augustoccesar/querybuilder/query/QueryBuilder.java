package br.com.augustoccesar.querybuilder.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by augustoccesar on 4/29/16.
 */
public class QueryBuilder {
    public static final String AND = "AND";
    public static final String OR = "OR";

    public static final String LEFT_JOIN = "LEFT JOIN";
    public static final String INNER_JOIN = "INNER JOIN";

    private List<String> fields;
    private List<String> tablesAndPrefixes;
    private List<Join> joins;
    @Deprecated
    private List<String> conditionsAndLinks;
    @Deprecated
    private List<String> orders;
    private Long limit;
    private Long offset;
    private List<String> counts;

    // New Version

    private Condition conditionBase;
    private List<Order> newOrders;

    // Methods

    public QueryBuilder select(String... fields){
        if(this.fields == null){
            this.fields = new ArrayList<>();
        }
        this.fields.addAll(Arrays.asList(fields));

        for(int i = 0; i < this.fields.size(); i++){
            String newField = this.fields.get(i) + " AS " + columnAlias(this.fields.get(i));
            this.fields.set(i, newField);
        }

        return this;
    }

    public QueryBuilder count(String... fields){
        if(this.counts == null){
            this.counts = new ArrayList<>();
        }
        this.counts.addAll(Arrays.asList(fields));

        for(int i = 0; i < this.counts.size(); i++){
            String newField = "COUNT(" + this.counts.get(i) + ") AS " + "count_" + columnAlias(this.counts.get(i));
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

    public QueryBuilder join(String type, String tableNameAndPrefix, String onJoin){
        if(this.joins == null)
            this.joins = new ArrayList<>();

        this.joins.add(new Join(type, tableNameAndPrefix, onJoin));
        return this;
    }

    @Deprecated
    public QueryBuilder conditions(String... conditionsAndLinks){
        if(this.conditionsAndLinks == null) {
            this.conditionsAndLinks = new ArrayList<>();
        }

        this.conditionsAndLinks.addAll(Arrays.asList(conditionsAndLinks));
        return this;
    }

    public QueryBuilder conditions(Condition conditionBase){
        this.conditionBase = conditionBase;
        return this;
    }

    @Deprecated
    public QueryBuilder order(String... fieldsAndOrder){
        if(this.orders == null) {
            this.orders = new ArrayList<>();
        }

        this.orders.addAll(Arrays.asList(fieldsAndOrder));
        return this;
    }

    public QueryBuilder order(Order... orders){
        if(this.newOrders == null){
            this.newOrders = new ArrayList<>();
        }

        this.newOrders.addAll(Arrays.asList(orders));
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
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" SELECT ");
        if(fields != null && fields.size() > 0) {
            runListIterator(stringBuilder, fields.listIterator(), ",");
            if(counts != null && counts.size() > 0)
                stringBuilder.append(", ");
        }

        if(counts != null && counts.size() > 0)
            runListIterator(stringBuilder, counts.listIterator(), "");

        stringBuilder.append(" FROM ");
        runListIterator(stringBuilder, tablesAndPrefixes.listIterator(), ",");

        if(joins != null && joins.size() > 0){
            List<String> joinStrings = new ArrayList<>();
            for(Join join : joins){
                joinStrings.add(join.type + " " + join.tableAndPrefix + " ON " + join.joinOn);
            }
            runListIterator(stringBuilder, joinStrings.listIterator(), null);
        }

        // Deprecated Conditions
        if(conditionsAndLinks != null && conditionsAndLinks.size() > 0){
            stringBuilder.append("WHERE ");

            if(conditionsAndLinks.contains(QueryBuilder.AND) || conditionsAndLinks.contains(QueryBuilder.OR)){
                for(int i = 0; i < conditionsAndLinks.size(); i++){
                    if(i != conditionsAndLinks.size() - 1) {
                        if (!conditionsAndLinks.get(i).equals(AND) && !conditionsAndLinks.get(i).equals(OR)) {
                            if (!conditionsAndLinks.get(i + 1).equals(AND) && !conditionsAndLinks.get(i + 1).equals(OR)) {
                                conditionsAndLinks.add(i + 1, AND);
                            }
                        }
                    }
                }
                runListIterator(stringBuilder, conditionsAndLinks.listIterator(), null);
            }else{
                runListIterator(stringBuilder, conditionsAndLinks.listIterator(), " AND ");
            }
        }

        // New Conditions
        if(this.conditionBase != null){
            stringBuilder.append(" WHERE ");

            stringBuilder.append(conditionBase.getField())
                    .append(conditionBase.getComparison().getValue())
                    .append(
                            conditionBase.getValue() == null ? "" : (
                                    conditionBase.getValue() instanceof String && !conditionBase.getValue().equals("?") ? "\"" + conditionBase.getValue() + "\"" : conditionBase.getValue()
                            )
                    );

            if(this.conditionBase.getNestedConditions() != null && this.conditionBase.getNestedConditions().size() > 0){
                for(Condition condition : this.conditionBase.getNestedConditions()){
                    stringBuilder.append(condition.getNestedLink())
                            .append(condition.getField())
                            .append(condition.getComparison().getValue())
                            .append(condition.getValue() == null ? "" : (condition.getValue() instanceof String ? "\"" + condition.getValue() + "\"" : condition.getValue()));
                }
            }
        }

        // Deprecated Order
        if(orders != null && orders.size() > 0){
            stringBuilder.append(" ORDER BY ");
            runListIterator(stringBuilder, orders.listIterator(), ",");
        }

        // New Order
        if(this.newOrders != null && this.newOrders.size() > 0){
            stringBuilder.append(" ORDER BY ");
            for (int i = 0; i < this.newOrders.size(); i++) {
                stringBuilder
                        .append(this.newOrders.get(i).getField())
                        .append(this.newOrders.get(i).getType().getValue());
                if(!(i == this.newOrders.size() - 1)){
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

    // Methods

    private void runListIterator(StringBuilder builder, ListIterator listIterator, String divisor){
        divisor = divisor != null ? divisor : "";

        while(listIterator.hasNext()){
            builder.append(" ").append(listIterator.next());
            if(listIterator.hasNext())
                builder.append(divisor).append(" ");
            else
                builder.append(" ");
        }
    }

    private String columnAlias(String columnSelection){
        return columnSelection.replace(".", "_");
    }

    // Classes

    private class Join{
        public String type;
        public String tableAndPrefix;
        public String joinOn;

        public Join(String type, String tableAndPrefix, String joinOn) {
            this.type = type;
            this.tableAndPrefix = tableAndPrefix;
            this.joinOn = joinOn;
        }
    }
}
