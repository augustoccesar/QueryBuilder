package br.com.augustoccesar.querybuilder.exceptions;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class ColumnWithoutValue extends Exception {
    public ColumnWithoutValue(String message) {
        super(message);
    }
}
