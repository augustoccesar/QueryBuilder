package br.com.augustoccesar.querybuilder.query.conditions;

import br.com.augustoccesar.querybuilder.builders.Buildable;
import br.com.augustoccesar.querybuilder.constants.CommonStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: augustoccesar
 * Date: 05/05/17
 */
class ConditionGroup extends ConditionSignature implements Buildable {
    private boolean nested = false;
    private List<Object> conditionsAndLinks = new ArrayList<>();

    public ConditionGroup() {
    }

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
            if (item instanceof ConditionGroup) {
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

                // TODO Rethink to not check for isNested twice
                if (cg.isNested())
                    builder.append(CommonStrings.OPEN_PARENTHESES);

                builder.append(cg.build());

                if (cg.isNested())
                    builder.append(CommonStrings.CLOSE_PARENTHESES);
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
