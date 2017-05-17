package br.com.augustoccesar.querybuilder.builders

/**
 * Created by augustoccesar on 4/29/16.
 */
interface Buildable {
    fun build(): String

    fun shouldBuild(): Boolean {
        return true
    }
}
