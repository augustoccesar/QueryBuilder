package se.augustoccesar.querybuilder.query.conditions;

import se.augustoccesar.querybuilder.query.Comparison;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 05/05/17
 */
public class ConditionTest {
    @Test
    public void shouldCreateEqualsCondition() {
        Condition condition = Condition.eq("{u}name", "Augusto");

        assertEquals("Augusto", condition.getValue());
        Assert.assertEquals(Comparison.EQUALS, condition.getComparison());
        assertEquals(" u.name = 'Augusto' ", condition.build());
    }

    @Test
    public void shouldCreateNotEqualsCondition() {
        Condition condition = Condition.neq("{u}name", "Augusto");

        assertEquals("Augusto", condition.getValue());
        Assert.assertEquals(Comparison.DIFFERENT, condition.getComparison());
        assertEquals(" u.name <> 'Augusto' ", condition.build());
    }

    @Test
    public void shouldCreateInCondition() {
        ArrayList<String> values = new ArrayList<>();
        values.add("Augusto");
        Condition condition = Condition.in("{u}name", values);

        assertEquals("Augusto", ((ArrayList) condition.getValue()).get(0));
        Assert.assertEquals(Comparison.IN, condition.getComparison());
        assertEquals(" u.name IN ( 'Augusto' ) ", condition.build());
    }

    @Test
    public void shouldCreateIsNullCondition() {
        Condition condition = Condition.isNull("{u}name");

        assertNull(condition.getValue());
        Assert.assertEquals(Comparison.IS_NULL, condition.getComparison());
        assertEquals(" u.name IS NULL ", condition.build());
    }

    @Test
    public void shouldCreateIsNotNullCondition() {
        Condition condition = Condition.isNotNull("{u}name");

        assertNull(condition.getValue());
        Assert.assertEquals(Comparison.IS_NOT_NULL, condition.getComparison());
        assertEquals(" u.name IS NOT NULL ", condition.build());
    }

    @Test
    public void shouldCreateLikeCondition() {
        Condition condition = Condition.like("{u}name", "Augusto");

        assertEquals("Augusto", condition.getValue());
        Assert.assertEquals(Comparison.LIKE, condition.getComparison());
        assertEquals(" u.name LIKE 'Augusto' ", condition.build());
    }

    @Test
    public void shouldCreateNotLikeCondition() {
        Condition condition = Condition.nlike("{u}name", "Augusto");

        assertEquals("Augusto", condition.getValue());
        Assert.assertEquals(Comparison.NOT_LIKE, condition.getComparison());
        assertEquals(" u.name NOT LIKE 'Augusto' ", condition.build());
    }

    @Test
    public void shouldCreateGreaterThanCondition() {
        Condition condition = Condition.gt("{u}name", 21);

        assertEquals(21, condition.getValue());
        Assert.assertEquals(Comparison.GREATER_THAN, condition.getComparison());
        assertEquals(" u.name > 21 ", condition.build());
    }

    @Test
    public void shouldCreateGreaterThanOrEqualCondition() {
        Condition condition = Condition.gte("{u}name", 21);

        assertEquals(21, condition.getValue());
        Assert.assertEquals(Comparison.GREATER_THAN_OR_EQUAL, condition.getComparison());
        assertEquals(" u.name >= 21 ", condition.build());
    }

    @Test
    public void shouldCreateLessThanCondition() {
        Condition condition = Condition.lt("{u}name", 21);

        assertEquals(21, condition.getValue());
        Assert.assertEquals(Comparison.LESS_THAN, condition.getComparison());
        assertEquals(" u.name < 21 ", condition.build());
    }

    @Test
    public void shouldCreateLessThanOrEqualCondition() {
        Condition condition = Condition.lte("{u}name", 21);

        assertEquals(21, condition.getValue());
        Assert.assertEquals(Comparison.LESS_THAN_OR_EQUAL, condition.getComparison());
        assertEquals(" u.name <= 21 ", condition.build());
    }
}