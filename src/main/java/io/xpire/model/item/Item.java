package io.xpire.model.item;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.tag.Tag;

/**
 * Represents a Item in the expiry date tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
    private final Name name;
    private final ExpiryDate expiryDate;
    private ReminderThreshold reminderThreshold = new ReminderThreshold("0");

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Item(Name name, ExpiryDate expiryDate, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, expiryDate, tags);
        this.name = name;
        this.expiryDate = expiryDate;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     * Tags are optional.
     */
    public Item(Name name, ExpiryDate expiryDate) {
        CollectionUtil.requireAllNonNull(name, expiryDate);
        this.name = name;
        this.expiryDate = expiryDate;
    }


    public Name getName() {
        return name;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a mutable reminder threshold.
     *
     * @return {@Code ReminderThreshold} object.
     */
    public ReminderThreshold getReminderThreshold() {
        return reminderThreshold;
    }

    /**
     * Sets a mutable reminder threshold.
     *
     * @param reminderThreshold An integer representating the reminder threshold.
     */
    public void setReminderThreshold(ReminderThreshold reminderThreshold) {
        this.reminderThreshold = reminderThreshold;
    }

    private Optional<ReminderDate> getReminderDate() {
        if (reminderThreshold.getThreshold() != 0) {
            return Optional.of(
                    new ReminderDate(this.expiryDate.getDate().plusDays(this.reminderThreshold.getThreshold())));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Tests if this item has exceeded its reminder threshold.
     *
     * @return {@Code true} if and only if the reminder threshold of this {@Code Item} is strictly earlier
     * than the current date.
     */
    boolean isReminderThresholdExceeded() {
        if (this.getReminderDate().isEmpty()) {
            return false;
        } else {
            return getReminderDate().get().getDate().isBefore(LocalDate.now());
        }
    }

    public String getReminderDateString() {
        return getReminderDate().isEmpty() ? "" : getReminderDate().get().toString();
    }

    /**
     * Tests if this item is expiring before the specified date.
     *
     * @param when a date
     * @return {@Code true} if and only if the expiry date of this {@Code Item} is strictly earlier
     * than {@Code when}; {@Code false} otherwise.
     */
    boolean isExpiring(LocalDate when) {
        return this.getExpiryDate().getDate().isBefore(when);
    }

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getName().equals(getName())
                && (otherItem.getExpiryDate().equals(getExpiryDate()));
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherPerson = (Item) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getExpiryDate().equals(getExpiryDate())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, expiryDate, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Expiry Date: ")
                .append(getExpiryDate().toString())
                .append(" Tags: ")
                .append(getReminderDate().isEmpty() ? "" : " Reminder on: " + getReminderDate().get());
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
