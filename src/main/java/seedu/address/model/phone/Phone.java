package seedu.address.model.phone;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Phone in the SML.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Phone {

    // Identity fields
    private final Name name;
    private final Brand brand;
    private final Capacity capacity;
    private final Color color;

    // Data fields
    private final Cost cost;
    private final Price price;
    private final Quantity quantity;
    private final Set<Tag> tags = new HashSet<>();

    public Phone(Name name, Brand brand, Capacity capacity, Color color, Cost cost, Price price, Quantity quantity,
                 Set<Tag> tags) {
        requireAllNonNull(name, brand, capacity, color, cost, price, quantity, tags);
        this.name = name;
        this.brand = brand;
        this.capacity = capacity;
        this.color = color;
        this.cost = cost;
        this.price = price;
        this.quantity = quantity;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Color getColor() {
        return color;
    }

    public Cost getCost() {
        return cost;
    }

    public Price getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both phones have the same identity fields.
     * This defines a weaker notion of equality between two phones.
     */
    public boolean isSamePhone(Phone otherPhone) {
        if (otherPhone == this) {
            return true;
        }

        return otherPhone != null
                && otherPhone.getName().equals(getName())
                && otherPhone.getBrand().equals((getBrand()))
                && otherPhone.getCapacity().equals((getCapacity()))
                && otherPhone.getColor().equals((getColor()));
    }

    /**
     * Returns true if both phones have the same identity and data fields.
     * This defines a stronger notion of equality between two phones.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return otherPhone.getName().equals(getName())
                && otherPhone.getName().equals(getName())
                && otherPhone.getBrand().equals((getBrand()))
                && otherPhone.getCapacity().equals((getCapacity()))
                && otherPhone.getColor().equals((getColor()))
                && otherPhone.getCost().equals(getCost())
                && otherPhone.getPrice().equals(getPrice())
                && otherPhone.getQuantity().equals(getQuantity())
                && otherPhone.getTags().equals((getTags()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, brand, capacity, color, cost, price, quantity, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Brand: ")
                .append(getBrand())
                .append(" Capacity: ")
                .append(getCapacity())
                .append(" Color: ")
                .append(getColor())
                .append(" Cost: ")
                .append(getCost())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
