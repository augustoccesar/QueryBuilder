package se.augustoccesar.querybuilder.exceptions;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class InvalidPattern extends RuntimeException {
    public InvalidPattern(String entity) {
        super("Invalid " + entity + " pattern.");
    }
}
