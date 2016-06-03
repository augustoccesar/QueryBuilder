package br.com.augustoccesar.querybuilder.helpers;

import java.util.ListIterator;

/**
 * Created by augustoccesar on 6/2/16.
 */
public class ListHelpers {
    public static void runListIterator(StringBuilder builder, ListIterator listIterator, String divisor) {
        divisor = divisor != null ? divisor : "";

        while (listIterator.hasNext()) {
            builder.append(" ").append(listIterator.next());
            if (listIterator.hasNext())
                builder.append(divisor).append(" ");
            else
                builder.append(" ");
        }
    }
}
