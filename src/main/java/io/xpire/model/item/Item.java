package io.xpire.model.item;

import static io.xpire.model.item.Quantity.DEFAULT_QUANTITY;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import io.xpire.commons.util.CollectionUtil;
import io.xpire.commons.util.DateUtil;
import io.xpire.model.tag.Tag;
/**
 * Represents a Item in xpire.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {
    // Identity fields
    private final Name name;
    private final ExpiryDate expiryDate;

    // Data fields
    private final Quantity quantity;
    private final Set<Tag> tags = new HashSet<>();
    private ReminderThreshold reminderThreshold = new ReminderThreshold(("0"));

    /**
     * Every field must be present and not null.
     * Only called in Tag and Edit commands.
     */
    public Item(Name name, ExpiryDate expiryDate, Quantity quantity, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, expiryDate, tags);
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     * Tags are optional.
     */
    public Item(Name name, ExpiryDate expiryDate, Quantity quantity) {
        CollectionUtil.requireAllNonNull(name, expiryDate);
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    /**
     * Every field must be present and not null.
     * Quantity is optional.
     */
    public Item(Name name, ExpiryDate expiryDate) {
        CollectionUtil.requireAllNonNull(name, expiryDate);
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = new Quantity(DEFAULT_QUANTITY);
    }


    public Name getName() {
        return this.name;
    }

    public ExpiryDate getExpiryDate() {
        return this.expiryDate;
    }

    public Quantity getQuantity() {
        return this.quantity;
    };

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(this.tags);
    }

    /**
     * Returns the reminder threshold.
     *
     * @return {@Code ReminderThreshold} object.
     */
    public ReminderThreshold getReminderThreshold() {
        return this.reminderThreshold;
    }

    /**
     * Sets and overrides the reminder threshold.
     *
     * @param reminderThreshold reminder threshold.
     */
    public void setReminderThreshold(ReminderThreshold reminderThreshold) {
        this.reminderThreshold = reminderThreshold;
    }

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameItem(Item other) {
        if (other == this) {
            return true;
        } else {
            return other != null
                    && this.name.equals(other.name)
                    && this.expiryDate.equals(other.expiryDate);
        }
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Item)) {
            return false;
        } else {
            Item other = (Item) obj;
            return this.name.equals(other.name)
                    && this.expiryDate.equals(other.expiryDate)
                    && this.tags.equals(other.tags)
                    && this.quantity.equals(other.quantity)
                    && this.reminderThreshold.equals(other.reminderThreshold);
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.name, this.expiryDate, this.tags, this.quantity, this.reminderThreshold);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.name + "\n")
                .append(String.format("Expiry date: %s (%s)\n",
                        this.expiryDate, this.expiryDate.getStatus(DateUtil.getCurrentDate())))
                .append("Tags: ");
        this.getTags().forEach(builder::append);
        return builder.toString();
    }

    /* TODO: Remove Tag for added items */

    /*   public String getAddedItem() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.name + "\n")
                .append(String.format("Expiry date: %s (%s)\n",
                        this.expiryDate, this.expiryDate.getStatus(DateUtil.getCurrentDate())));
        return builder.toString();
    } */
}
