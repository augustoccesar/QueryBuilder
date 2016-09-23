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
    /**
     * Commom Strings
     */
    private static final String STRING_UNDERLINE = "_";
    private static final String STRING_SPACE = " ";
    private static final String STRING_COMMA = " , ";
    private static final String STRING_OPEN_PARENTHESES = " ( ";
    private static final String STRING_CLOSE_PARENTHESES = " ) ";
    private static final String STRING_AS = " AS ";
    private static final String STRING_ON = " ON ";
    private static final String STRING_FROM = " FROM ";
    private static final String STRING_LIMIT = " LIMIT ";
    private static final String STRING_WHERE = " WHERE ";
    private static final String STRING_OFFSET = " OFFSET ";
    private static final String STRING_SELECT = " SELECT ";
    private static final String STRING_GROUP_BY = " GROUP BY ";
    private static final String STRING_ORDER_BY = " ORDER BY ";
    private static final String STRING_DISTINCT = " DISCTINCT ";

    /**
     * Attributes
     */
    private List<String> fields;
    private List<Aggregation> aggregations;
    private List<Function> functions;
    private List<String> tablesAndPrefixes;
    private List<String> distinctList;
    private List<Join> joins;
    private Long limit;
    private Long offset;
    private Condition conditionBase;
    private List<Order> orders;
    private String groupBy;
    private boolean withAlias = true;

    /**
     * Method for building the list of fields that are going to be queried.
     *
     * @param fields List of the fields, with or without the table alias.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder select(String... fields) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.addAll(Arrays.asList(fields));

        return this;
    }

    /**
     * Method for building the list of distinct fields that are going to be queried.
     * @param fields List of the fields that are going to be DISTINCT
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder selectDistinct(String... fields) {
        if (this.distinctList == null) {
            this.distinctList = new ArrayList<>();
        }
        this.distinctList.addAll(Arrays.asList(fields));

        return this;
    }

    /**
     * Method for building the list of aggregations that are going to be queried. Examples: SUM, COUNT, etc
     * @param aggregations List of the aggregations.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder selectAggregations(Aggregation... aggregations) {
        if (this.aggregations == null) {
            this.aggregations = new ArrayList<>();
        }
        this.aggregations.addAll(Arrays.asList(aggregations));

        return this;
    }

    /**
     * Method for building the list of functions that are going to be queried on the SELECT clause.
     * @param functions List of the functions.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder selectFunctions(Function... functions) {
        if (this.functions == null) {
            this.functions = new ArrayList<>();
        }
        this.functions.addAll(Arrays.asList(functions));

        return this;
    }

    /**
     * Method for building the FROM clause.
     * @param tableNameAndPrefix List of the tables (with or without alias) that are going to be used on the query.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder from(String... tableNameAndPrefix) {
        if (this.tablesAndPrefixes == null) {
            this.tablesAndPrefixes = new ArrayList<>();
        }

        this.tablesAndPrefixes.addAll(Arrays.asList(tableNameAndPrefix));
        return this;
    }

    /**
     * Method for adding a join to the query.
     * @param join Join object instance.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder join(Join join) {
        if (this.joins == null)
            this.joins = new ArrayList<>();

        this.joins.add(join);
        return this;
    }

    /**
     * Method for adding multiple joins to the query. Similar to #{@link #join(Join)} but with a list of Join objects.
     * @param joins Array of Join objects.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder joins(Join... joins) {
        if (this.joins == null)
            this.joins = new ArrayList<>();

        Collections.addAll(this.joins, joins);
        return this;
    }

    /**
     * Method for building the WHERE clause.
     * @param conditionBase Instance of the base Condition Object.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder where(Condition conditionBase) {
        this.conditionBase = conditionBase;
        return this;
    }

    /**
     * Method for building the ORDER clause.
     * @param orders Array of Order objects.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder order(Order... orders) {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }

        this.orders.addAll(Arrays.asList(orders));
        return this;
    }

    /**
     * Method for building the GROUP BY clause.
     * @param groupBy String value of the column to be grouped by.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder groupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    /**
     * Method for building the LIMIT clause.
     * @param value Value of the limit.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder limit(Long value) {
        this.limit = value;
        return this;
    }

    /**
     * Method for building OFFSET clause.
     * @param value Value of the offset.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder offset(Long value) {
        this.offset = value;
        return this;
    }

    /**
     * Method for defining if want the SelectBuilder to build the query with alias on the columns.
     * @param flag Value of the flag.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder withAlias(boolean flag) {
        this.withAlias = flag;
        return this;
    }

    /**
     * Method for generating the SQL statement.
     * @return SQL Statement String.
     */
    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasFields = fields != null && fields.size() > 0;
        boolean hasAggregations = aggregations != null && aggregations.size() > 0;
        boolean hasDistinct = distinctList != null && distinctList.size() > 0;
        boolean hasFunctions = functions != null && functions.size() > 0;

        if (hasFields) {
            for (int i = 0; i < this.fields.size(); i++) {
                if (ColumnHelper.hasTableName(this.fields.get(i)) &&
                        !ColumnHelper.isAll(this.fields.get(i)) &&
                        this.withAlias) {
                    String newField = this.fields.get(i) + STRING_AS + ColumnHelper.columnAlias(this.fields.get(i));
                    this.fields.set(i, newField);
                }
            }
        }

        stringBuilder.append(STRING_SELECT);

        if (hasFunctions) {
            for (int i = 0; i < functions.size(); i++) {
                Function function = functions.get(i);

                stringBuilder.append(function.getName());
                if (function.getParameters() != null && function.getParameters().getParametersList().size() > 0) {
                    stringBuilder.append(STRING_OPEN_PARENTHESES);
                    for (int j = 0; j < function.getParameters().getParametersList().size(); j++) {
                        String parameter = function.getParameters().getParametersList().get(j);
                        stringBuilder.append(parameter);
                        if (j != function.getParameters().getParametersList().size() - 1)
                            stringBuilder.append(STRING_COMMA);
                    }
                    stringBuilder.append(STRING_CLOSE_PARENTHESES);
                }

                if (function.getAlias() != null) {
                    stringBuilder.append(STRING_AS).append(function.getAlias());
                }

                if (i != functions.size() - 1) {
                    stringBuilder.append(STRING_COMMA);
                }
            }

            if (hasFields || hasAggregations || hasDistinct) {
                stringBuilder.append(STRING_COMMA);
            }
        }

        if (hasDistinct) {
            for (int i = 0; i < distinctList.size(); i++) {
                stringBuilder.append(STRING_DISTINCT);
                stringBuilder.append(distinctList.get(i));
                stringBuilder.append(STRING_AS).append(ColumnHelper.columnAlias(distinctList.get(i)));
                if (i != distinctList.size() - 1) {
                    stringBuilder.append(STRING_COMMA);
                }
            }

            if (hasAggregations || hasFields) {
                stringBuilder.append(STRING_COMMA);
            }
        }

        if (hasFields) {
            ListHelpers.runListIterator(stringBuilder, fields.listIterator(), STRING_COMMA);
            if (hasAggregations)
                stringBuilder.append(STRING_COMMA);
        }

        if (hasAggregations) {
            for (int i = 0; i < aggregations.size(); i++) {
                Aggregation aggregation = aggregations.get(i);

                stringBuilder.append(aggregation.getAggregationType().getCommand()).append(STRING_OPEN_PARENTHESES);
                stringBuilder.append(aggregation.getColumnName());
                stringBuilder.append(STRING_CLOSE_PARENTHESES).append(STRING_AS);
                stringBuilder.append(aggregation.getAggregationType().getCommand().toLowerCase()).append(STRING_UNDERLINE).append(ColumnHelper.columnAlias(aggregation.getColumnName()));

                if (i != aggregations.size() - 1) {
                    stringBuilder.append(STRING_COMMA);
                }
            }
        }

        stringBuilder.append(STRING_FROM);
        ListHelpers.runListIterator(stringBuilder, tablesAndPrefixes.listIterator(), STRING_COMMA);

        if (joins != null && joins.size() > 0) {
            List<String> joinStrings = new ArrayList<>();
            for (Join join : joins) {
                joinStrings.add(join.type + " " + join.tableAndPrefix + STRING_ON + join.joinOn);
            }
            ListHelpers.runListIterator(stringBuilder, joinStrings.listIterator(), null);
        }

        if (this.conditionBase != null) {
            stringBuilder.append(STRING_WHERE);
            ColumnHelper.runNestedConditions(stringBuilder, conditionBase);
        }

        if (groupBy != null) {
            stringBuilder.append(STRING_GROUP_BY).append(STRING_OPEN_PARENTHESES).append(ColumnHelper.columnAlias(groupBy)).append(STRING_CLOSE_PARENTHESES);
        }

        if (this.orders != null && this.orders.size() > 0) {
            stringBuilder.append(STRING_ORDER_BY);
            for (int i = 0; i < this.orders.size(); i++) {
                stringBuilder
                        .append(this.orders.get(i).getField())
                        .append(this.orders.get(i).getType().getValue());
                if (!(i == this.orders.size() - 1)) {
                    stringBuilder.append(STRING_COMMA);
                }
            }
        }

        if (limit != null) {
            stringBuilder.append(STRING_LIMIT).append(limit).append(STRING_SPACE);
        }

        if (offset != null) {
            stringBuilder.append(STRING_OFFSET).append(offset).append(STRING_SPACE);
        }

        return stringBuilder.toString().trim().replaceAll(" +", STRING_SPACE);
    }
}
