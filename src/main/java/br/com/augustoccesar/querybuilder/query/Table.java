package br.com.augustoccesar.querybuilder.query;

import br.com.augustoccesar.querybuilder.exceptions.InvalidPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: augustoccesar
 * Date: 04/05/17
 */
public class Table {
    private String name;
    private String alias;

    public Table(String creationString) {
        Pattern patternMarkdown = Pattern.compile("(\\w+)\\{(\\w+)\\}");
        Pattern patternPlain = Pattern.compile("(\\w+)\\s(\\w+)");

        Matcher matcherMarkdown = patternMarkdown.matcher(creationString);
        if (matcherMarkdown.matches()) {
            this.name = matcherMarkdown.group(1);
            this.alias = matcherMarkdown.group(2);
        } else {
            Matcher matcherPlain = patternPlain.matcher(creationString);
            if (matcherPlain.matches()){
                this.name = matcherPlain.group(1);
                this.alias = matcherPlain.group(2);
            }else{
                throw new InvalidPattern("Table");
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
}
