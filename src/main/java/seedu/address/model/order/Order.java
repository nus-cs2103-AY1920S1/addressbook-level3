package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.customer.Customer;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Represents an Order in the SML.
 * Guarantees: details are present, field values are validated.
 */
public class Order implements Cloneable {

    // Identity fields
    private final UUID id;

    // Data fields
    private final Customer customer;
    private final Phone phone;
    private final Price price;
    private final Status status;
    private final Schedule schedule;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Order(Customer customer, Phone phone, Price price, Set<Tag> tags) {
        requireAllNonNull(customer, phone, price, tags);
        this.id = UUID.randomUUID();
        this.customer = (Customer) customer.clone();
        this.phone = (Phone) phone.clone();
        this.price = price;
        this.status = Status.UNSCHEDULED;
        this.schedule = null;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for OrderBuilder to create an object with corresponding data fields with random UUID.
     */
    public Order(Customer customer, Phone phone, Price price, Status status, Schedule schedule, Set<Tag> tags) {
        requireAllNonNull(customer, phone, price, tags);
        this.id = UUID.randomUUID();
        this.customer = (Customer) customer.clone();
        this.phone = (Phone) phone.clone();
        this.price = price;
        this.status = status;
        this.schedule = schedule;
        this.tags.addAll(tags);
    }

    /**
     * Private constructor to make a cloned Order.
     */
    private Order(UUID id, Customer customer, Phone phone, Price price, Status status, Schedule schedule,
                  Set<Tag> tags) {
        requireAllNonNull(id, customer, phone, price, status, tags);
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

    public Schedule getSchedule() {
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
     * Returns true if both orders have the same identity fields.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getId().equals(getId());
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
        return otherOrder.getCustomer().equals(getCustomer())
                && otherOrder.getPhone().equals(getPhone())
                && otherOrder.getPrice().equals(getPrice())
                && otherOrder.getStatus().equals(getStatus())
                && otherOrder.getTags().equals((getTags()));
    }

    @Override
    public Object clone() {
        Order clone;
        if (schedule == null) {
            clone = new Order(this.id, (Customer) this.customer.clone(), (Phone) this.phone.clone(),
                    (Price) this.price.clone(), this.status, null, this.getTags());
        } else {
            clone = new Order(this.id, (Customer) this.customer.clone(), (Phone) this.phone.clone(),
                    (Price) this.price.clone(), this.status, (Schedule) this.schedule.clone(), this.getTags());
        }
        return clone;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, customer, phone, price, status, tags);
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
}
