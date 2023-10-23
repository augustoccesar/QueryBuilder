package se.augustoccesar.querybuilder.query.trackers;

import se.augustoccesar.querybuilder.builders.Buildable;
import se.augustoccesar.querybuilder.query.Join;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class JoinTracker implements Buildable {
    private ArrayList<Join> joins = new ArrayList<>();

    public JoinTracker addJoin(Join join) {
        this.joins.add(join);
        return this;
    }

    public JoinTracker addJoins(Join... joins) {
        Collections.addAll(this.joins, joins);
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        this.joins.forEach((join) -> {
            stringBuilder.append(join.build());
        });

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    @Override
    public boolean shouldBuild() {
        return this.joins.size() > 0;
    }
}
