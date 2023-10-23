package se.augustoccesar.querybuilder.query.trackers;

import se.augustoccesar.querybuilder.builders.Buildable;
import se.augustoccesar.querybuilder.constants.CommonStrings;
import se.augustoccesar.querybuilder.query.Order;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: augustoccesar
 * Date: 10/05/17
 */
public class OrderTracker implements Buildable {
    private ArrayList<Order> orders = new ArrayList<>();

    public OrderTracker addOrder(Order order) {
        this.orders.add(order);
        return this;
    }

    public OrderTracker addOrders(Order... orders) {
        this.orders.addAll(Arrays.asList(orders));
        return this;
    }

    @Override
    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.orders.size(); i++) {
            stringBuilder.append(this.orders.get(i).build());
            if (i < this.orders.size() - 1) {
                stringBuilder.append(CommonStrings.COMMA);
            }
        }

        return stringBuilder.toString().replaceAll("\\s+", " ");
    }

    @Override
    public boolean shouldBuild() {
        return orders.size() > 0;
    }
}
