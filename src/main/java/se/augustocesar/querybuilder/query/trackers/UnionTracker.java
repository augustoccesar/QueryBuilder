package se.augustocesar.querybuilder.query.trackers;

import se.augustocesar.querybuilder.builders.Buildable;
import se.augustocesar.querybuilder.builders.SelectBuilder;
import se.augustocesar.querybuilder.query.Union;

import java.util.ArrayList;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class UnionTracker implements Buildable {
    private ArrayList<Union> unions = new ArrayList<>();

    public UnionTracker addUnion(SelectBuilder selectBuilder) {
        this.unions.add(Union.union(selectBuilder));
        return this;
    }

    public UnionTracker addUnionAll(SelectBuilder selectBuilder) {
        this.unions.add(Union.unionAll(selectBuilder));
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        this.unions.forEach((union) -> stringBuilder.append(union.build()));

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    @Override
    public boolean shouldBuild() {
        return this.unions.size() > 0;
    }
}
