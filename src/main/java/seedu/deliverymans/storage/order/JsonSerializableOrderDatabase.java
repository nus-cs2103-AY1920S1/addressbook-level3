package seedu.deliverymans.storage.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;
import seedu.deliverymans.model.order.Order;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "orderdatabase")
public class JsonSerializableOrderDatabase {

    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate order(s).";

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableOrderBook} with the given orders.
     */
    @JsonCreator
    public JsonSerializableOrderDatabase(@JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.orders.addAll(orders);
    }

    /**
     * Converts a given {@code ReadOnlyOrderBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableOrderBook}.
     */
    public JsonSerializableOrderDatabase(ReadOnlyOrderDatabase source) {
        orders.addAll(source.getOrderList().stream().map(JsonAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this aorder book into the model's {@code OrderBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public OrderDatabase toModelType() throws IllegalValueException {
        OrderDatabase orderDatabase = new OrderDatabase();
        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            Order order = jsonAdaptedOrder.toModelType();
            if (orderDatabase.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            orderDatabase.addOrder(order);
        }
        return orderDatabase;
    }

}
