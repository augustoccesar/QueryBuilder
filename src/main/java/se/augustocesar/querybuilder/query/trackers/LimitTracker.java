package se.augustocesar.querybuilder.query.trackers;

import se.augustocesar.querybuilder.builders.Buildable;
import se.augustocesar.querybuilder.query.Limit;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class LimitTracker implements Buildable {
    private Limit limit;

    public LimitTracker setLimit(int value) {
        this.limit = new Limit(value);
        return this;
    }


    @Override
    public String build() {
        return (" " + this.limit.getValue() + " ").replaceAll("\\s+", " ");
    }

    @Override
    public boolean shouldBuild() {
        return limit != null;
    }
}
