package br.com.augustoccesar.querybuilder.builders;

import br.com.augustoccesar.querybuilder.configurations.Configuration;
import br.com.augustoccesar.querybuilder.configurations.Databases;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;
import br.com.augustoccesar.querybuilder.query.trigger.Action;
import br.com.augustoccesar.querybuilder.query.trigger.Time;
import br.com.augustoccesar.querybuilder.query.trigger.databases.SqliteTriggerBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by augustoccesar on 6/17/16.
 */
public class TriggerBuilder implements QueryBuilder {
    private String triggerName;
    private String tableName;
    private Time time;
    private Action action;
    private List<QueryBuilder> queries;

    public TriggerBuilder name(String name) {
        this.triggerName = name;
        return this;
    }

    public TriggerBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public TriggerBuilder when(Time time) {
        this.time = time;
        return this;
    }

    public TriggerBuilder action(Action action) {
        this.action = action;
        return this;
    }

    public TriggerBuilder queriesToExecute(QueryBuilder queries) {
        if (this.queries == null)
            this.queries = new ArrayList<>();
        Collections.addAll(this.queries, queries);
        return this;
    }

    @Override
    public String build() {
        if (Configuration.getDatabase().equals(Databases.SQLITE)) {
            return SqliteTriggerBuild.build(this);
        } else {
            return null;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public Time getTime() {
        return time;
    }

    public Action getAction() {
        return action;
    }

    public List<QueryBuilder> getQueries() {
        return queries;
    }

    public String getTriggerName() {
        return triggerName;
    }
}
