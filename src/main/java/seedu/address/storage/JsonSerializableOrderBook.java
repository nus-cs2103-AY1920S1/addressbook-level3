package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.order.Order;

/**
 * An Immutable OrderBook that is serializable to JSON format.
 */
@JsonRootName(value = "orderbook")
class JsonSerializableOrderBook {

    public static final String MESSAGE_DUPLICATE_ORDER = "orders list contains duplicate order(s).";

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableOrderBook} with the given orders.
     */
    @JsonCreator
    public JsonSerializableOrderBook(@JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.orders.addAll(orders);
    }

    /**
     * Converts a given {@code ReadOnlyOrderBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableOrderBook}.
     */
    public JsonSerializableOrderBook(ReadOnlyDataBook<Order> source) {
        orders.addAll(source.getList().stream().map(JsonAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this order book into the model's {@code OrderBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public OrderBook toModelType() throws IllegalValueException {
        OrderBook orderBook = new OrderBook();
        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            Order order = jsonAdaptedOrder.toModelType();
            if (orderBook.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            orderBook.addOrder(order);
        }
        return orderBook;
    }

}
