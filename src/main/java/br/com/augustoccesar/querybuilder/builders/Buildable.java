package br.com.augustoccesar.querybuilder.builders;

/**
 * Created by augustoccesar on 4/29/16.
 */
public interface Buildable {
    String build();
    default String build(boolean withDistinct, boolean withAlias) {
        return null;
    }
}
