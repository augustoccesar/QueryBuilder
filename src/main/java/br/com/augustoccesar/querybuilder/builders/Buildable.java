package br.com.augustoccesar.querybuilder.builders;

/**
 * Created by augustoccesar on 4/29/16.
 */
public interface Buildable {
    // TODO rethink this approach
    String build();
    default String build(boolean withDistinct, boolean withAlias) {
        return null;
    }
}
