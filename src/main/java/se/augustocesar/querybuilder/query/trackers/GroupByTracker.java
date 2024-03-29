package se.augustocesar.querybuilder.query.trackers;

import se.augustocesar.querybuilder.builders.Buildable;
import se.augustocesar.querybuilder.query.Column;

/**
 * Author: augustoccesar
 * Date: 15/05/17
 */
public class GroupByTracker implements Buildable {
    private Column column;

    public GroupByTracker setGroupByColumn(String markedColumn) {
        this.column = Column.fromMarkdown(markedColumn);
        return this;
    }

    @Override
    public String build() {
        return this.column.sqlColumnRepresentation();
    }

    @Override
    public boolean shouldBuild() {
        return column != null;
    }
}
