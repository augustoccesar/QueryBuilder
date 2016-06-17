package br.com.augustoccesar.querybuilder.query.trigger.databases;

import br.com.augustoccesar.querybuilder.builders.TriggerBuilder;
import br.com.augustoccesar.querybuilder.interfaces.QueryBuilder;

/**
 * Created by augustoccesar on 6/17/16.
 */
public class SqliteTriggerBuild {
    public static String build(TriggerBuilder triggerBuilder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" CREATE TRIGGER ").append(triggerBuilder.getTriggerName());
        stringBuilder.append(triggerBuilder.getTime().getValue()).append(triggerBuilder.getAction().getValue()).append(" ON ").append(triggerBuilder.getTableName()).append(" ");
        stringBuilder.append(" BEGIN ");
        for (QueryBuilder queryBuilder : triggerBuilder.getQueries()) {
            stringBuilder.append(queryBuilder.build()).append(";");
        }
        stringBuilder.append(" END ");

        return stringBuilder.toString().trim().replaceAll(" +", " ");
    }
}
