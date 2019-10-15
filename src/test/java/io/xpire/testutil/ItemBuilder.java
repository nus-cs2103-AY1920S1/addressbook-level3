package io.xpire.testutil;

import java.util.Set;
import java.util.TreeSet;

import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */
public class ItemBuilder {

    public static final String DEFAULT_NAME = "Kiwi";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_EXPIRY_DATE = "1/2/2020";
    public static final String DEFAULT_THRESHOLD = "0";

    private Name name;
    private ExpiryDate expiryDate;
    private Quantity quantity;
    private Set<Tag> tags;
    private ReminderThreshold reminderThreshold;

    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        tags = new TreeSet<>(new TagComparator());
        reminderThreshold = new ReminderThreshold(DEFAULT_THRESHOLD);
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        expiryDate = itemToCopy.getExpiryDate();
        quantity = itemToCopy.getQuantity();
        TreeSet<Tag> set = new TreeSet<>(new TagComparator());
        set.addAll(itemToCopy.getTags());
        tags = set;
        this.reminderThreshold = itemToCopy.getReminderThreshold();
    }

    /**
     * Sets the {@code Name} of the {@code Item} that we are building.
     */
    public ItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Item} that we are building.
     */
    public ItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code Item} that we are building.
     */
    public ItemBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Sets the {@code ReminderThreshold} of the {@code Item} that we are building.
     */
    public ItemBuilder withReminderThreshold(String threshold) {
        this.reminderThreshold = new ReminderThreshold(threshold);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Item} that we are building.
     */
    public ItemBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code ReminderThreshold} of the {@code Item} that we are building.
     */
    public ItemBuilder withThreshold(String reminderThreshold) {
        this.reminderThreshold = new ReminderThreshold(reminderThreshold);
        return this;
    }

    /**
     * Constructs a new {@code Item} with the modified fields.
     */
    public Item build() {
        Item newItem = new Item(name, expiryDate, quantity, tags);
        newItem.setReminderThreshold(reminderThreshold);
        return newItem;
    }

}
