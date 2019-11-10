package seedu.deliverymans.model.deliveryman;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.StatusTag;

/**
 * Represents a deliveryman
 */
public class Deliveryman {

    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Set<Tag> tags;
    private StatusTag status;

    /**
     * Every field must be present and not null.
     */
    public Deliveryman(Name name, Phone phone, Set<Tag> tags) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
        this.tags = Set.copyOf(tags);
        status = new StatusTag("UNAVAILABLE"); // editing field will affect status
    }

    public Deliveryman(Name name, Phone phone, Set<Tag> tags, StatusTag status) {
        requireAllNonNull(name, phone);
        this.name = name;
        this.phone = phone;
        this.tags = Set.copyOf(tags);
        this.status = status; // editing other fields will affect status
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
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameDeliveryman(Deliveryman otherDeliveryman) {
        if (otherDeliveryman == this) {
            return true;
        }

        return otherDeliveryman != null
                && otherDeliveryman.getName().equals(getName())
                && otherDeliveryman.getPhone().equals(getPhone())
                && otherDeliveryman.getStatus().getDescription().equals(getStatus().getDescription());
    }

    /**
     * Returns true if both persons have the same identity. Data fields need not be same.
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
                && otherPerson.getTags().equals(getTags());

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, tags);
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
