package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.query.Column;
import br.com.augustoccesar.querybuilder.query.Comparison;
import br.com.augustoccesar.querybuilder.query.Join;
import br.com.augustoccesar.querybuilder.query.conditions.ConditionSignature;
import br.com.augustoccesar.querybuilder.query.conditions.ConditionsTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by augustoccesar on 6/13/16.
 */
public class SelectBuilder implements Buildable {
    /**
     * Attributes
     */
    private String alias;
    private List<Column> columns;
    private List<Join> joins;

    private ConditionsTracker conditionsTracker;

    /**
     * Constructors
     */
    public SelectBuilder() {
    }

    public SelectBuilder(String alias) {
        this.alias(alias);
    }

    public SelectBuilder alias(String alias) {
        this.alias = alias;
        return this;
    }

    public SelectBuilder select(Object... fields) {
        Arrays.asList(fields).forEach((field) -> {
            if (field instanceof String) {
                if (this.columns == null) {
                    this.columns = new ArrayList<>();
                }

                this.columns.add(Column.fromMarkdown(field.toString()));
            }
        });

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

    public SelectBuilder where(String columnMarkdown, Comparison comparison, Object value) {
        conditionsTracker.addCondition(columnMarkdown, comparison, value);
        return this;
    }

    public SelectBuilder where(ConditionSignature... conditions) {
        conditionsTracker.addConditions(conditions);
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();
        return null;
    }
}
