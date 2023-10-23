package se.augustocesar.querybuilder.builders;

/**
 * Created by augustoccesar on 4/29/16.
 */
public interface Buildable {
    // TODO rethink this approach
    String build();

    default boolean shouldBuild() { return true; }
}
