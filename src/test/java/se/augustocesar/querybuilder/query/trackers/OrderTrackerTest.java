package se.augustocesar.querybuilder.query.trackers;

import se.augustocesar.querybuilder.query.Order;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class OrderTrackerTest {
    @Test
    public void shouldCreateWithOneOrder(){
        OrderTracker orderTracker = new OrderTracker();
        orderTracker.addOrder(Order.asc("{u}name"));

        String expected = " u.name ASC ";
        assertEquals(expected, orderTracker.build());
    }

    @Test
    public void shouldCreateWithMultipleOrderOneByOne(){
        OrderTracker orderTracker = new OrderTracker();
        orderTracker.addOrder(Order.asc("{u}name"));
        orderTracker.addOrder(Order.desc("{u}id"));

        String expected = " u.name ASC , u.id DESC ";
        assertEquals(expected, orderTracker.build());
    }

    @Test
    public void shouldCreateWithMultipleDirectly(){
        OrderTracker orderTracker = new OrderTracker();
        orderTracker.addOrders(
                Order.asc("{u}name"),
                Order.desc("{u}id")
        );

        String expected = " u.name ASC , u.id DESC ";
        assertEquals(expected, orderTracker.build());
    }
}