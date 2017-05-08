package br.com.augustoccesar.querybuilder.query.trackers;

import br.com.augustoccesar.querybuilder.builders.Buildable;
import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import br.com.augustoccesar.querybuilder.constants.CommonStrings;
import br.com.augustoccesar.querybuilder.exceptions.InvalidSelectBuilder;
import br.com.augustoccesar.querybuilder.query.Column;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class SelectTracker implements Buildable {
    private ArrayList<Column> columns = new ArrayList<>();
    private ArrayList<SelectBuilder> selectBuilders = new ArrayList<>();

    public SelectTracker addSelect(Object... selectObjects) {
        Arrays.asList(selectObjects).forEach((item) -> {
            if (item instanceof String)
                this.columns.add(Column.fromMarkdown(item.toString()));
            if (item instanceof Column)
                this.columns.add((Column) item);
            if (item instanceof SelectBuilder)
                this.selectBuilders.add((SelectBuilder) item);
        });

        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" ");
        for (int i = 0; i < this.columns.size(); i++) {
            stringBuilder.append(this.columns.get(i).build());
            if (i < this.columns.size() - 1 && this.selectBuilders.size() == 0) {
                stringBuilder.append(CommonStrings.COMMA);
            }
        }

        for (int i = 0; i < this.selectBuilders.size(); i++) {
            SelectBuilder sb = this.selectBuilders.get(i);

            if(sb.getAlias() != null) {
                stringBuilder.append(this.selectBuilders.get(i).build());
            }else{
                throw new InvalidSelectBuilder("Nested SelectBuilder must have an alias.");
            }

            if (i < this.selectBuilders.size() - 1) {
                stringBuilder.append(CommonStrings.COMMA);
            }
        }

        stringBuilder.append(" ");

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    @Override
    public boolean shouldBuild() {
        return this.columns.size() > 0 || this.selectBuilders.size() > 0;
    }
}
