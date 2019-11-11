package seedu.deliverymans.model.deliveryman;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.UNAVAILABLE_STATUS;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.StatusTag;

/**
 * Represents a deliveryman
 *
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deliveryman {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Set<Tag> tags;
    private final StatusTag status;

    /**
     * Every field must be present and not null.
     */
    public Deliveryman(Name name, Phone phone, Set<Tag> tags) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
        this.tags = Set.copyOf(tags);
        status = new StatusTag(UNAVAILABLE_STATUS);
    }

    // Second constructor primarily for setting status as "DELIVERING" or "UNAVAILABLE"
    public Deliveryman(Name name, Phone phone, Set<Tag> tags, StatusTag status) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
        this.tags = Set.copyOf(tags);
        this.status = status;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public StatusTag getStatus() {
        return status;
    }

    public Deliveryman setStatusTo(StatusTag status) {
        return new Deliveryman(name, phone, tags, status);
    }

    /**
     * Returns true if both persons have the same name and phone number.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameDeliveryman(Deliveryman otherDeliveryman) {
        if (otherDeliveryman == this) {
            return true;
        }

        return otherDeliveryman != null
                && otherDeliveryman.getName().equals(getName())
                && otherDeliveryman.getPhone().equals(getPhone());
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

        if (!(other instanceof Deliveryman)) {
            return false;
        }

        Deliveryman otherPerson = (Deliveryman) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, tags, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone().toString())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
