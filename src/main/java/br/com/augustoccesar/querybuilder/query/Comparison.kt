package br.com.augustoccesar.querybuilder.query

/**
 * Created by augustoccesar on 6/2/16.
 */
enum class Comparison constructor(val value: String) {
    EQUALS(" = "),
    IN(" IN "),
    IS_NULL(" IS NULL "),
    IS_NOT_NULL(" IS NOT NULL "),
    LIKE(" LIKE "),
    NOT_LIKE(" NOT LIKE "),
    DIFFERENT(" <> "),
    GREATER_THAN(" > "),
    GREATER_THAN_OR_EQUAL(" >= "),
    LESS_THAN(" < "),
    LESS_THAN_OR_EQUAL(" <= "),
    VARIABLE("?")
}
