package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.Identifiable;
import seedu.address.model.customer.Customer;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Represents an Order in the SML.
 * Guarantees: details are present, field values are validated.
 */
public class Order implements Identifiable<Order> {

    // Identity fields
    private final UUID id;

    // Data fields
    private final Customer customer;
    private final Phone phone;
    private final Price price;
    private final Status status;
    private final Optional<Schedule> schedule;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Order(UUID id, Customer customer, Phone phone, Price price, Status status,
                 Optional<Schedule> schedule, Set<Tag> tags) {
        requireAllNonNull(id, customer, phone, price, status, schedule, tags);
        this.id = id;
        this.customer = customer;
        this.phone = phone;
        this.price = price;
        this.status = status;
        this.schedule = schedule;
        this.tags.addAll(tags);
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Phone getPhone() {
        return phone;
    }

    public Price getPrice() {
        return price;
    }

    public Status getStatus() {
        return status;
    }

    public Optional<Schedule> getSchedule() {
        return schedule;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both orders have the same data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return otherOrder.getId().equals(getId())
                && otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getPrice().equals(getPrice())
                && otherOrder.getStatus().equals(getStatus())
                && otherOrder.getSchedule().equals(getSchedule())
                && otherOrder.getTags().equals((getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, customer, phone, price, status, schedule, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" # ")
                .append(getId())
                .append(" Customer: ")
                .append(getCustomer())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Price: ")
                .append(getPrice())
                .append(" Order Status: ")
                .append(getStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public boolean isSameAs(Order other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getPhone().equals(getPhone());
    }

}
