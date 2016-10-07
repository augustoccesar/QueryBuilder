package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.builders.SelectBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by augustoccesar on 10/7/16.
 */
public class UnionAll {
    private String alias;
    private List<SelectBuilder> selectBuilderList;

    public UnionAll(String alias, SelectBuilder... selectBuilders) {
        this.alias = alias;
        this.selectBuilderList = new ArrayList<>();
        this.selectBuilderList.addAll(Arrays.asList(selectBuilders));
    }

    public String getAlias() {
        return alias;
    }

    public List<SelectBuilder> getSelectBuilderList() {
        return selectBuilderList;
    }
}
