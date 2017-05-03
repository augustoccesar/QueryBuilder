package br.com.augustoccesar.querybuilder;

import br.com.augustoccesar.querybuilder.builders.SelectBuilder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 03/05/17
 */
public class IntegrationTest {
    @Test
    public void shouldBeAbleToBasicSelect(){
        SelectBuilder selectBuilder = new SelectBuilder();

        selectBuilder
                .select("u.name")
                .from("users u");

        String expected = "SELECT u.name AS u_name FROM users u";

        assertEquals(expected, selectBuilder.build());
    }

    @Test
    public void shouldBeAbleToBasicSelectUsingMarkdown(){
        SelectBuilder selectBuilder = new SelectBuilder();

        selectBuilder
                .select("{u}name", "{u}username{custom_username}")
                .from("users{u}");

        String expected = "SELECT u.name AS u_name, u.username AS custom_username FROM users u";

        assertEquals(expected, selectBuilder.build());
    }
}
