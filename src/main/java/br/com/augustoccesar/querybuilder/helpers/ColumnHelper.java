package br.com.augustoccesar.querybuilder.helpers;

import br.com.augustoccesar.querybuilder.query.Condition;
import br.com.augustoccesar.querybuilder.query.trigger.TriggerData;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class ColumnHelper {
    public static String columnAlias(String columnSelection) {
        return columnSelection.replace(".", "_");
    }

    public static boolean hasTableName(String columnSelection) {
        return columnSelection.matches("\\w+\\.\\w+");
    }

    public static String checkValueForInsert(Object value) {
        if (value instanceof String) {
            return "\'" + value + "\'";
        } else if (value instanceof TriggerData) {
            return ((TriggerData) value).insertableData();
        } else if (value == null) {
            return "?";
        } else {
            return value.toString();
        }
    }

    public static void runNestedConditions(StringBuilder query, Condition initialCondition) {
        if (initialCondition.getNestedLink() == null) {
            query.append(initialCondition.getField())
                    .append(initialCondition.getComparison().getValue())
                    .append(initialCondition.getValueAsString());
        }

        if (initialCondition.getNestedConditions() != null && initialCondition.getNestedConditions().size() > 0) {
            for (Condition condition : initialCondition.getNestedConditions()) {
                boolean hasNested = condition.getNestedConditions() != null && condition.getNestedConditions().size() > 0;

                query.append(condition.getNestedLink());

                if (hasNested)
                    query.append(" ( ");

                query.append(condition.getField())
                        .append(condition.getComparison().getValue())
                        .append(condition.getValueAsString());

                if (hasNested) {
                    runNestedConditions(query, condition);
                    query.append(" ) ");
                }
            }
        }
    }
}
