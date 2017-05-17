package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern
import java.util.*
import java.util.regex.Pattern

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
class Table(var name: String) : Buildable {
    var alias: String? = null

    constructor(name: String, alias: String?): this(name) {
        this.alias = alias
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(" ").append(this.name).append(" ")

        if (this.alias != null) {
            stringBuilder.append(this.alias).append(" ")
        }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    fun build(withAlias: Boolean): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(" ").append(this.name).append(" ")

        if (withAlias) {
            if (this.alias != null) {
                stringBuilder.append(this.alias)
            }
        }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmStatic
        fun fromMarkdown(creationString: String): Table {
            val patternMarkdown = Pattern.compile("(\\w+)\\{(\\w+)\\}")
            val patternPlain = Pattern.compile("(\\w+)(\\s(\\w+))*")

            val matcherMarkdown = patternMarkdown.matcher(creationString)
            val matcherPlain = patternPlain.matcher(creationString)

            if (matcherMarkdown.matches()) {
                val name = matcherMarkdown.group(1)
                var alias: String? = matcherMarkdown.group(2)

                alias = if ("_" == alias) null else alias

                return Table(name, alias)
            } else if (matcherPlain.matches()) {
                val name = matcherPlain.group(1)
                val alias = matcherPlain.group(3)

                return Table(name, alias)
            } else {
                throw InvalidPattern("Table")
            }
        }

        @JvmStatic
        fun multipleFromMarkdown(vararg markedStrings: String): ArrayList<Table> {
            val response = ArrayList<Table>()
            Arrays.asList(*markedStrings).forEach { markedString -> response.add(fromMarkdown(markedString)) }
            return response
        }
    }
}
