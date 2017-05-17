package br.com.augustoccesar.querybuilder.query

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
class Limit(value: Int) {
    var value: Int = 0
        private set

    init {
        if (value < 0) {
            throw IllegalArgumentException("Limit value must be positive.")
        } else {
            this.value = value
        }
    }
}
