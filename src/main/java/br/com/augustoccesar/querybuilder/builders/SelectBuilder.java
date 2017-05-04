package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.query.*;

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
    private static final String STRING_DISTINCT = " DISTINCT ";

    /**
     * Attributes
     */
    private String alias;
    private List<String> fields;
    private List<Join> joins;

    /**
     * Constructors
     */
    public SelectBuilder() {
    }

    public SelectBuilder(String alias) {
        this.alias(alias);
    }

    /**
     * Method for setting an alias to the SelectBuilder.
     *
     * @param alias Alias to the SelectBuilder.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    /**
     * Method for building the list of fields that are going to be queried.
     *
     * @param fields List of the fields, with or without the table alias.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder select(Object... fields) {
        Arrays.asList(fields).forEach((field) -> {
            if (field instanceof String) {
                if (this.fields == null) {
                    this.fields = new ArrayList<>();
                }

                this.fields.add(field.toString());
            }
        });

        return this;
    }

    /**
     * Method for adding a join to the query.
     *
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
     *
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
     *
     * @param condition Instance of the base Condition Object.
     * @return This instance of SelectBuilder.
     */
    public SelectBuilder where(Condition condition) {
        // TODO Implement
        return this;
    }

    /**
     * Method for generating the SQL statement.
     *
     * @return SQL Statement String.
     */
    @Override
    public String build() {
        // TODO Implement
        return null;
    }

    // Getters and Setters


    public String getAlias() {
        return alias;
    }
}
