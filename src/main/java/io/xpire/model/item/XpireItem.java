package io.xpire.model.item;

import static io.xpire.model.item.Quantity.DEFAULT_QUANTITY;
import static io.xpire.model.item.ReminderThreshold.DEFAULT_THRESHOLD;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.util.CollectionUtil;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

/**
 * Represents an item in the expiry date tracker.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class XpireItem extends Item {
    // Identity fields
    private final ExpiryDate expiryDate;

    // Data fields
    private Quantity quantity = new Quantity(DEFAULT_QUANTITY);
    private ReminderThreshold reminderThreshold = new ReminderThreshold(DEFAULT_THRESHOLD);

    /**
     * Every field must be present and not null.
     * Only called in Tag and Delete commands.
     */
    public XpireItem(Name name, ExpiryDate expiryDate, Quantity quantity, Set<Tag> tags) {
        super(name, tags);
        CollectionUtil.requireAllNonNull(expiryDate);
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    /**
     * Every field must be present and not null.
     * Tags are optional.
     */
    public XpireItem(Name name, ExpiryDate expiryDate, Quantity quantity) {
        super(name);
        CollectionUtil.requireAllNonNull(expiryDate, quantity);
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    /**
     * Every field must be present and not null.
     * Quantity is optional.
     */
    public XpireItem(Name name, ExpiryDate expiryDate) {
        super(name);
        CollectionUtil.requireAllNonNull(expiryDate);
        this.expiryDate = expiryDate;
    }

    /**
     * Constructor with all parameters for ItemBuilder class. (Used in testing)
     */
    public XpireItem(Name name, ExpiryDate expiryDate, Quantity quantity, Set<Tag> tags,
                     ReminderThreshold reminderThreshold) {
        super(name, tags);
        CollectionUtil.requireAllNonNull(expiryDate);
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.reminderThreshold = reminderThreshold;
    }

    public XpireItem(XpireItem xpireItem) {
        super(xpireItem);
        this.expiryDate = xpireItem.getExpiryDate();
        this.quantity = xpireItem.getQuantity();
        this.reminderThreshold = xpireItem.getReminderThreshold();
    }

    public ExpiryDate getExpiryDate() {
        return this.expiryDate;
    }

    public Quantity getQuantity() {
        return this.quantity;
    }

    /**
     * Sets and overrides the quantity.
     *
     * @param newQuantity Quantity to be updated.
     */
    public void setQuantity(Quantity newQuantity) {
        this.quantity = newQuantity;
    }

    /**
     * Returns the reminder threshold.
     *
     * @return {@code ReminderThreshold} object.
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

    public Set<Tag> getNewTagSet(Tag tag) {
        Set<Tag> newTagSet = new TreeSet<>(new TagComparator());
        newTagSet.addAll(this.getTags());
        newTagSet.add(tag);
        return newTagSet;
    }

    //@@author xiaoyu-nus
    /**
     * Returns {@Code true} if the item has expired.
     */
    public boolean isExpired() {
        return this.expiryDate.isPassed();
    }

    /**
     * Returns {@Code true} if the item's reminderThreshold is activated.
     */
    public boolean isReminding() {
        long remainingDays = Long.parseLong(this.getExpiryDate().getStatus());
        int reminderThreshold = this.getReminderThreshold().getValue();
        return hasReminderThreshold() && remainingDays <= reminderThreshold;
    }

    /**
     * Returns {@Code true} if the item has a {@Code ReminderThreshold}.
     */
    public boolean hasReminderThreshold() {
        return !this.reminderThreshold.equals(new ReminderThreshold(DEFAULT_THRESHOLD));
    }
    //@@author

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    @Override
    public boolean isSameItem(Item otherItem) {
        XpireItem other;
        try {
            other = (XpireItem) otherItem;
        } catch (ClassCastException e) {
            return false;
        }
        if (other == this) {
            return true;
        } else {
            return other != null
                    && this.name.equals(other.name)
                    && this.expiryDate.equals(other.expiryDate);
        }
    }

    //@@author JermyTan
    /**
     * Returns a new {@code Item} with the name and tags of the current item.
     *
     * @return New {@code Item}.
     */
    public Item remodel() {
        return new Item(this.name, this.tags);
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof XpireItem)) {
            return false;
        } else {
            XpireItem other = (XpireItem) obj;
            return super.equals(other)
                    && this.expiryDate.equals(other.expiryDate)
                    && this.quantity.equals(other.quantity)
                    && this.reminderThreshold.equals(other.reminderThreshold);
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(super.hashCode(), this.expiryDate, this.quantity, this.reminderThreshold);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (!this.getTags().isEmpty()) {
            builder.append(this.name).append("\n")
                    .append(String.format("Expiry date: %s\n", this.expiryDate.toStringWithCountdown()))
                    .append(String.format("Quantity: %s\n", this.quantity))
                    .append("Tags:");
            this.getTags().forEach(tag -> builder.append(" " + tag));
        } else {
            builder.append(this.name).append("\n")
                    .append(String.format("Expiry date: %s\n", this.expiryDate.toStringWithCountdown()))
                    .append(String.format("Quantity: %s", this.quantity));
        }
        return builder.toString();
    }
}
