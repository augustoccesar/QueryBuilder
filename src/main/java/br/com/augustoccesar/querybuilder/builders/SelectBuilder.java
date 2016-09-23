package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.helpers.ColumnHelper;
import br.com.augustoccesar.querybuilder.helpers.ListHelpers;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;
import br.com.augustoccesar.querybuilder.query.Condition;
import br.com.augustoccesar.querybuilder.query.Function;
import br.com.augustoccesar.querybuilder.query.Join;
import br.com.augustoccesar.querybuilder.query.Order;
import br.com.augustoccesar.querybuilder.query.aggregation.Aggregation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SelectBuilder implements QueryBuilder {

    private List<String> fields;
    private List<Aggregation> aggregations;
    private List<Function> functions;
    private List<String> tablesAndPrefixes;
    private List<String> distinctList;
    private List<Join> joins;
    private Long limit;
    private Long offset;
    private List<String> counts;
    private Condition conditionBase;
    private List<Order> orders;
    private String groupBy;
    private boolean withAlias = true;

    public SelectBuilder select(String... fields) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.addAll(Arrays.asList(fields));

        return this;
    }

    public SelectBuilder selectDistinct(String... fields) {
        if (this.distinctList == null) {
            this.distinctList = new ArrayList<>();
        }
        this.distinctList.addAll(Arrays.asList(fields));

        return this;
    }

    public SelectBuilder selectAggregations(Aggregation... aggregations) {
        if (this.aggregations == null) {
            this.aggregations = new ArrayList<>();
        }
        this.aggregations.addAll(Arrays.asList(aggregations));

        return this;
    }

    public SelectBuilder selectFunctions(Function... functions) {
        if (this.functions == null) {
            this.functions = new ArrayList<>();
        }
        this.functions.addAll(Arrays.asList(functions));

        return this;
    }

    @Deprecated
    public SelectBuilder count(String field) {
        if (this.counts == null) {
            this.counts = new ArrayList<>();
        }
        this.counts.add(field);

        for (int i = 0; i < this.counts.size(); i++) {
            String newField = "COUNT(" + this.counts.get(i) + ") AS " + "count_" + ColumnHelper.columnAlias(this.counts.get(i));
            this.counts.set(i, newField);
        }

        return this;
    }

    public SelectBuilder from(String... tableNameAndPrefix) {
        if (this.tablesAndPrefixes == null) {
            this.tablesAndPrefixes = new ArrayList<>();
        }

        this.tablesAndPrefixes.addAll(Arrays.asList(tableNameAndPrefix));
        return this;
    }

    public SelectBuilder join(Join join) {
        if (this.joins == null)
            this.joins = new ArrayList<>();

        this.joins.add(join);
        return this;
    }

    public SelectBuilder joins(Join... joins) {
        if (this.joins == null)
            this.joins = new ArrayList<>();

        Collections.addAll(this.joins, joins);
        return this;
    }

    public SelectBuilder where(Condition conditionBase) {
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

    public SelectBuilder groupBy(String groupBy) {
        this.groupBy = groupBy;
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

    public SelectBuilder withAlias(boolean flag) {
        this.withAlias = flag;
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasFields = fields != null && fields.size() > 0;
        // TODO remove counts outside aggregation on next versions
        boolean hasCounts = counts != null && counts.size() > 0;
        boolean hasAggregations = aggregations != null && aggregations.size() > 0;
        boolean hasDistinct = distinctList != null && distinctList.size() > 0;
        boolean hasFunctions = functions != null && functions.size() > 0;

        if (hasFields) {
            for (int i = 0; i < this.fields.size(); i++) {
                if (ColumnHelper.hasTableName(this.fields.get(i)) &&
                        !ColumnHelper.isAll(this.fields.get(i)) &&
                        this.withAlias) {
                    String newField = this.fields.get(i) + " AS " + ColumnHelper.columnAlias(this.fields.get(i));
                    this.fields.set(i, newField);
                }
            }
        }

        stringBuilder.append(" SELECT ");

        if (hasFunctions) {
            for (int i = 0; i < functions.size(); i++) {
                Function function = functions.get(i);

                stringBuilder.append(function.getName());
                if (function.getParameters() != null && function.getParameters().getParametersList().size() > 0) {
                    stringBuilder.append(" ( ");
                    for (int j = 0; j < function.getParameters().getParametersList().size(); j++) {
                        String parameter = function.getParameters().getParametersList().get(j);
                        stringBuilder.append(parameter);
                        if (j != function.getParameters().getParametersList().size() - 1)
                            stringBuilder.append(", ");
                    }
                    stringBuilder.append(" ) ");
                }

                if (function.getAlias() != null) {
                    stringBuilder.append(" AS ").append(function.getAlias());
                }

                if (i != functions.size() - 1) {
                    stringBuilder.append(", ");
                }
            }

            if (hasCounts || hasFields || hasAggregations || hasDistinct) {
                stringBuilder.append(", ");
            }
        }

        if (hasDistinct) {
            for (int i = 0; i < distinctList.size(); i++) {
                stringBuilder.append(" DISTINCT ");
                stringBuilder.append(distinctList.get(i));
                stringBuilder.append(" AS ").append(ColumnHelper.columnAlias(distinctList.get(i)));
                if (i != distinctList.size() - 1) {
                    stringBuilder.append(", ");
                }
            }

            if (hasCounts || hasAggregations || hasFields) {
                stringBuilder.append(", ");
            }
        }

        if (hasFields) {
            ListHelpers.runListIterator(stringBuilder, fields.listIterator(), ",");
            if (hasCounts || hasAggregations)
                stringBuilder.append(", ");
        }

        if (hasAggregations) {
            for (int i = 0; i < aggregations.size(); i++) {
                Aggregation aggregation = aggregations.get(i);

                stringBuilder.append(aggregation.getAggregationType().getCommand()).append("(");
                stringBuilder.append(aggregation.getColumnName());
                stringBuilder.append(") AS ");
                stringBuilder.append(aggregation.getAggregationType().getCommand().toLowerCase()).append("_").append(ColumnHelper.columnAlias(aggregation.getColumnName()));

                if (i != aggregations.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        }

        // TODO remove counts outside aggregation on next versions
        if (hasCounts)
            ListHelpers.runListIterator(stringBuilder, counts.listIterator(), "");

        stringBuilder.append(" FROM ");
        ListHelpers.runListIterator(stringBuilder, tablesAndPrefixes.listIterator(), ",");

        if (joins != null && joins.size() > 0) {
            List<String> joinStrings = new ArrayList<>();
            for (Join join : joins) {
                joinStrings.add(join.type + " " + join.tableAndPrefix + " ON " + join.joinOn);
            }
            ListHelpers.runListIterator(stringBuilder, joinStrings.listIterator(), null);
        }

        if (this.conditionBase != null) {
            stringBuilder.append(" WHERE ");
            ColumnHelper.runNestedConditions(stringBuilder, conditionBase);
        }

        if (groupBy != null) {
            stringBuilder.append(" GROUP BY (").append(ColumnHelper.columnAlias(groupBy)).append(")");
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

        if (limit != null) {
            stringBuilder.append(" LIMIT ").append(limit).append(" ");
        }

        if (offset != null) {
            stringBuilder.append(" OFFSET ").append(offset).append(" ");
        }

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
