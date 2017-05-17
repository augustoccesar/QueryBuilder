package br.com.augustoccesar.querybuilder.exceptions

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
class InvalidPattern(entity: String) : RuntimeException("Invalid $entity pattern.")
