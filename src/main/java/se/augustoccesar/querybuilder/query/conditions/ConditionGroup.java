package se.augustoccesar.querybuilder.query.conditions;

import se.augustoccesar.querybuilder.builders.Buildable;
import se.augustoccesar.querybuilder.constants.CommonStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: augustoccesar
 * Date: 05/05/17
 */
public class ConditionGroup extends ConditionSignature implements Buildable {
    private boolean nested = false;
    private List<Object> conditionsAndLinks = new ArrayList<>();

    public void add(ConditionSignature conditionSignature) {
        if (this.conditionsAndLinks.size() == 0) {
            this.conditionsAndLinks.add(conditionSignature);
        } else {
            this.add(ConditionLink.AND, conditionSignature);
        }
    }

    public void add(ConditionLink link, ConditionSignature condition) {
        if (this.conditionsAndLinks.size() == 0) {
            this.conditionsAndLinks.add(condition);
        } else {
            this.conditionsAndLinks.add(link);
            this.conditionsAndLinks.add(condition);
        }
    }

    public void generateMetadata() {
        this.conditionsAndLinks.forEach((item) -> {
            if (item instanceof ConditionGroup && item != conditionsAndLinks.get(0)) {
                ((ConditionGroup) item).setNested(true);
                ((ConditionGroup) item).generateMetadata();
            }
        });
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();

        this.conditionsAndLinks.forEach((item) -> {
            if (item instanceof ConditionLink) {
                builder.append(((ConditionLink) item).getDisplayName());
            } else if (item instanceof Condition) {
                builder.append(((Condition) item).build());
            } else if (item instanceof ConditionGroup) {
                ConditionGroup cg = (ConditionGroup) item;

                if (cg.isNested()) {
                    builder.append(CommonStrings.OPEN_PARENTHESES)
                            .append(cg.build())
                            .append(CommonStrings.CLOSE_PARENTHESES);

                }else{
                    builder.append(cg.build());
                }
            }
        });

        return builder.toString().replaceAll("\\s+", " ");
    }

    // Getters and Setters

    public List<Object> getConditionsAndLinks() {
        return conditionsAndLinks;
    }

    public boolean isNested() {
        return this.nested;
    }

    public void setNested(boolean nested) {
        this.nested = nested;
    }
}
