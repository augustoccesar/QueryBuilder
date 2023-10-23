package se.augustoccesar.querybuilder.query.trackers;

import se.augustoccesar.querybuilder.builders.Buildable;
import se.augustoccesar.querybuilder.query.Column;
import se.augustoccesar.querybuilder.query.Comparison;
import se.augustoccesar.querybuilder.query.conditions.Condition;
import se.augustoccesar.querybuilder.query.conditions.ConditionGroup;
import se.augustoccesar.querybuilder.query.conditions.ConditionSignature;

import java.util.Arrays;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class ConditionsTracker implements Buildable{
    private ConditionGroup baseConditionGroup = new ConditionGroup();

    public void addCondition(String columnMarkdown, Comparison comparison, Object value) {
        this.baseConditionGroup.add(new Condition(Column.fromMarkdown(columnMarkdown), comparison, value));
    }

    public void addConditions(ConditionSignature... conditions) {
        Arrays.asList(conditions).forEach((cond) -> this.baseConditionGroup.add(cond));
        this.baseConditionGroup.generateMetadata();
    }

    @Override
    public String build() {
        return this.baseConditionGroup.build();
    }

    @Override
    public boolean shouldBuild() {
        return this.baseConditionGroup.getConditionsAndLinks().size() > 0;
    }
}
