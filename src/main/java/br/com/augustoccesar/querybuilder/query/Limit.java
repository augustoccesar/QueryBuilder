package br.com.augustoccesar.querybuilder.query;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class Limit {
    private int value;

    public Limit(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Limit value must be positive.");
        } else {
            this.value = value;
        }
    }

    public int getValue() {
        return value;
    }
}
