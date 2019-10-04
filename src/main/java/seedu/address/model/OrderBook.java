package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.order.UniqueOrderList;

/**
 * Wraps all data at the order-book level
 * Duplicates are not allowed (by .isSameOrder comparison)
 */
public class OrderBook implements ReadOnlyDataBook<Order> {

    private final UniqueOrderList orders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orders = new UniqueOrderList();
    }

    public OrderBook() {}

    /**
     * Creates an OrderBook using the Orders in the {@code toBeCopied}
     */
    public OrderBook(ReadOnlyDataBook<Order> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setOrders(List<Order> orders) {
        this.orders.setOrders(orders);
    }

    /**
     * Resets the existing data of this {@code OrderBook} with {@code newData}.
     */
    public void resetData(ReadOnlyDataBook<Order> newData) {
        requireNonNull(newData);

        setOrders(newData.getList());
    }

    //// order-level operations

    /**
     * Returns true if a order with the same identity as {@code order} exists in the order book.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds a order to the order book.
     * The order must not already exist in the order book.
     */
    public void addOrder(Order o) {
        orders.add(o);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the order book.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the order book.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code OrderBook}.
     * {@code key} must exist in the order book.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.asUnmodifiableObservableList().size() + " orders";
        // TODO: refine later
    }

    @Override
    public ObservableList<Order> getList() {
        return orders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderBook // instanceof handles nulls
                && orders.equals(((OrderBook) other).orders));
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
