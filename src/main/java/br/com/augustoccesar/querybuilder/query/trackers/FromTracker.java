package br.com.augustoccesar.querybuilder.query.trackers;

import br.com.augustoccesar.querybuilder.builders.Buildable;
import br.com.augustoccesar.querybuilder.constants.CommonStrings;
import br.com.augustoccesar.querybuilder.query.Table;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Author: augustoccesar
 * Date: 08/05/17
 */
public class FromTracker implements Buildable {
    private ArrayList<Table> tables = new ArrayList<>();

    public FromTracker addMarkedTables(String... tables){
        this.tables.addAll(Table.multipleFromMarkdown(tables));
        return this;
    }

    public FromTracker addTables(Table... tables){
        Collections.addAll(this.tables, tables);
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < this.tables.size(); i++) {
            stringBuilder.append(this.tables.get(i).build());
            if(i < this.tables.size() - 1) {
                stringBuilder.append(CommonStrings.COMMA);
            }
        }

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    @Override
    public boolean shouldBuild() {
        return this.tables.size() > 0;
    }
}
