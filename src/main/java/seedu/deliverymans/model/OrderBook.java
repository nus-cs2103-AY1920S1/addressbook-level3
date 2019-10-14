package seedu.deliverymans.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.order.Order;
import seedu.deliverymans.model.order.UniqueOrderList;

/**
 * Wraps all data at the order database level
 * Duplicates are not allowed (by .isSameOrder comparison)
 */
public class OrderBook implements ReadOnlyOrderBook {
    private final UniqueOrderList orders;

    {
        orders = new UniqueOrderList();
    }

    public OrderBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public OrderBook(ReadOnlyOrderBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order list with {@code orders}.
     * {@code orders} must not contain duplicate orderss.
     */

    public void setOrders(List<Order> orders) {
        this.orders.setOrders(orders);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyOrderBook newData) {
        requireNonNull(newData);

        setOrders(newData.getOrderList());
    }

    /**
     * Returns true if an order with the same identity as {@code order} exists in the address book.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds an order to the address book.
     * The order must not already exist in the address book.
     */
    public void addOrder(Order o) {
        orders.add(o);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the address book.
     * The order identity of {@code editedorder} must not be the same as another existing order in the address book.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);

        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    @Override
    public ObservableList<Order> getOrderList() {
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
