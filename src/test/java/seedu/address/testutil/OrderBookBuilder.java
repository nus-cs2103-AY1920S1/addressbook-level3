package seedu.address.testutil;

import seedu.address.model.DataBook;
import seedu.address.model.order.Order;

/**
 * A utility class to help with building {@code Order} {@code DataBook}.
 * Example usage: <br>
 *     {@code DataBook<Order> ab = new OrderBookBuilder().withOrder("John", "Doe").build();}
 */
public class OrderBookBuilder {

    private DataBook<Order> orderBook;

    public OrderBookBuilder() {
        orderBook = new DataBook<>();
    }

    public OrderBookBuilder(DataBook<Order> orderBook) {
        this.orderBook = orderBook;
    }

    /**
     * Adds a new {@code Order} to the {@code DataBook} that we are building.
     */
    public OrderBookBuilder withOrder(Order order) {
        orderBook.add(order);
        return this;
    }

    public DataBook<Order> build() {
        return orderBook;
    }
}
