package br.com.augustoccesar.querybuilder.query

import br.com.augustoccesar.querybuilder.builders.Buildable
import br.com.augustoccesar.querybuilder.constants.CommonStrings
import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern
import java.util.regex.Pattern

/**
 * Created by augustoccesar on 8/9/16.
 */
class Column(var name: String) : Buildable {
    var prefix: String? = null
    var alias: String? = null
    var distinct: Boolean = false

    constructor(name: String, distinct: Boolean) : this(name) {
        this.distinct = distinct
    }

    constructor(prefix: String, name: String, distinct: Boolean) : this(name) {
        this.prefix = prefix
        this.alias = prefix + "_" + name
        this.distinct = distinct
    }

    constructor(prefix: String?, name: String, alias: String?, distinct: Boolean) : this(name) {
        this.prefix = prefix
        this.alias = alias
        this.distinct = distinct
    }

    fun sqlColumnRepresentation(): String {
        val stringBuilder = StringBuilder()
        if (prefix != null)
            stringBuilder.append(prefix).append(".")
        stringBuilder.append(name)
        return stringBuilder.toString()
    }

    fun isDistinct(): Boolean {
        return distinct
    }

    override fun build(): String {
        val stringBuilder = StringBuilder()

        if (this.distinct) {
            stringBuilder.append(CommonStrings.DISTINCT)
        }

        if (this.prefix != null) {
            stringBuilder.append(this.prefix).append(".")
        }

        stringBuilder.append(this.name)

        if (this.alias != null) {
            stringBuilder.append(CommonStrings.AS).append(this.alias)
        }

        return stringBuilder.toString().replace("\\s+".toRegex(), " ")
    }

    companion object {
        @JvmStatic
        fun multiColumns(tablePrefix: String, vararg columnMarkdown: String): MutableList<String> {
            val response: MutableList<String> = mutableListOf()
            val regex = "(\\**\\w+)(\\{(\\w+)\\})?".toRegex()

            for(i in 0..columnMarkdown.size - 1) {
                val groups = regex.matchEntire(columnMarkdown[i])?.groups ?: throw InvalidPattern("Column")

                val name = groups[1]?.value
                val alias = groups[3]?.value

                var fullMarked: String = "{$tablePrefix}$name"
                if(alias != null) fullMarked = "$fullMarked{$alias}"

                response.add(fullMarked)
            }

            return response
        }

        @JvmStatic
        fun fromMarkdown(columnMarkdown: String): Column {
            val markdownWithTableAlias = Pattern.compile("\\{(\\w+)\\}(\\**\\w+)")
            val fullMarkdown = Pattern.compile("\\{(\\w+)\\}(\\**\\w+)\\{(\\w+)\\}")

            val markdownWithTableAliasMatcher = markdownWithTableAlias.matcher(columnMarkdown)
            val fullMarkdownMatcher = fullMarkdown.matcher(columnMarkdown)

            if (markdownWithTableAliasMatcher.matches()) {
                val prefix = markdownWithTableAliasMatcher.group(1)
                var name = markdownWithTableAliasMatcher.group(2)
                var distinct = false

                if (name.contains("*")) {
                    name = name.replace("*", "")
                    distinct = true
                }

                if ("_" == prefix) {
                    return Column(name = name, distinct = distinct)
                } else {
                    return Column(prefix = prefix, name = name, distinct = distinct)
                }
            } else if (fullMarkdownMatcher.matches()) {
                var prefix: String? = fullMarkdownMatcher.group(1)
                var name = fullMarkdownMatcher.group(2)
                var customAlias: String? = fullMarkdownMatcher.group(3)
                var distinct = false

                prefix = if ("_" == prefix) null else prefix
                customAlias = if ("_" == customAlias) null else customAlias

                if (name.contains("*")) {
                    name = name.replace("*", "")
                    distinct = true
                }

                return Column(prefix, name, customAlias, distinct)
            } else {
                throw InvalidPattern("Column")
            }
        }
    }
}
