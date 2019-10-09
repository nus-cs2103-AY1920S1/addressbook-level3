package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
// import java.util.Collections;
// import java.util.HashSet;

// import java.util.Set;

// import seedu.address.model.tag.Tag;

/**
 * Represents a Expense in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private final Description description;
    private final Price price;
    private final Timestamp timestamp;
    // private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(Description description, Price price, Timestamp timestamp) { // Set<Tag> tags) {
        requireAllNonNull(description, price, timestamp);
        this.description = description;
        this.price = price;
        this.timestamp = timestamp;
        // this.tags.addAll(tags);
    }

    public Description getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    //    /**
    //     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
    //     * if modification is attempted.
    //     */
    //    public Set<Tag> getTags() {
    //        return Collections.unmodifiableSet(tags);
    //    }

    /**
     * Returns true if both expenses of the same description have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two expenses.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getDescription().equals(getDescription())
                && (otherEvent.getPrice().equals(getPrice()))
                && (otherEvent.getTimestamp().equals(getTimestamp()));
    }

    /**
     * Returns true if both expenses have the same identity and data fields.
     * This defines a stronger notion of equality between two expenses.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getDescription().equals(getDescription())
                && otherEvent.getPrice().equals(getPrice())
                // && otherEvent.getTags().equals(getTags())
                && (otherEvent.getTimestamp().equals(getTimestamp()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, price, timestamp); // , tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("|| Description: ")
                .append(getDescription())
                .append(" Price: ")
                .append(getPrice())
                .append(" Timestamp: ")
                .append(getTimestamp());
        // .append(" Tags: ");
        // getTags().forEach(builder::append);
        builder.append("||");
        return builder.toString();
    }

}
