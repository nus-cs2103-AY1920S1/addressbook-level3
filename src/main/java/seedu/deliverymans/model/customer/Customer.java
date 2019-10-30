package seedu.deliverymans.model.customer;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.order.Order;

/**
 * Represents a Customer in the system.
 */
public class Customer {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final ObservableList<Order> orders = FXCollections.observableArrayList();

    /**
     * Every field must be present and not null.
     */
    public Customer(Name name, Phone phone, Set<Tag> tags) {
        requireAllNonNull(name, phone, tags);
        this.name = name;
        this.phone = phone;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for adding order
     */
    public Customer(Name name) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = new Phone("123");
    }

    /**
     * Constructor for saving to storage
     */
    public Customer(Name name, Phone phone, Set<Tag> tags, ObservableList<Order> orders) {
        requireAllNonNull(name, phone, tags, orders);
        this.name = name;
        this.phone = phone;
        this.tags.addAll(tags);
        this.orders.addAll(orders);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void addOrder(Order order) {
        orders.add(order);
        // reviewTags();
    }

    public void reviewTags() {
        /*
        to be implemented
        depending on the list of orders, tags change to the tag that showed up the most in the list of orders
         */
    }

    /**
     * Returns an immutable order list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public ObservableList<Order> getOrders() {
        return orders;
    }

    public int getOrderSize() {
        return orders.size();
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        return otherCustomer != null
                && otherCustomer.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return otherCustomer.getName().equals(getName())
                && otherCustomer.getPhone().equals(getPhone())
                && otherCustomer.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, tags, orders);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone().toString())
                .append(" Tags: ")
                .append(getTags().toString());
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
