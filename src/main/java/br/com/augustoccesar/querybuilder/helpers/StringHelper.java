package br.com.augustoccesar.querybuilder.helpers;

/**
 * Created by augustoccesar on 8/9/16.
 */
public class StringHelper {
    public static String camelToSnakeCase(String string) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return string.replaceAll(regex, replacement).toLowerCase();
    }
}
