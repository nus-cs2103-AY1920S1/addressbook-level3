package seedu.address.testutil;

import seedu.address.model.OrderBook;
import seedu.address.model.order.Order;

/**
 * A utility class to help with building OrderBook objects.
 * Example usage: <br>
 *     {@code OrderBook ab = new OrderBookBuilder().withOrder("John", "Doe").build();}
 */
public class OrderBookBuilder {

    private OrderBook orderBook;

    public OrderBookBuilder() {
        orderBook = new OrderBook();
    }

    public OrderBookBuilder(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    /**
     * Adds a new {@code Order} to the {@code OrderBook} that we are building.
     */
    public OrderBookBuilder withOrder(Order order) {
        orderBook.addOrder(order);
        return this;
    }

    public OrderBook build() {
        return orderBook;
    }
}
