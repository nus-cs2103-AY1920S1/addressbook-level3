package io.xpire.testutil;

import java.util.Set;
import java.util.TreeSet;

import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.item.XpireItem;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.model.util.SampleDataUtil;

/**
 * A utility class to help with building XpireItem objects.
 */
public class XpireItemBuilder {

    public static final String DEFAULT_NAME = "Kiwi";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_EXPIRY_DATE = "1/2/2020";
    public static final String DEFAULT_THRESHOLD = "0";

    private Name name;
    private ExpiryDate expiryDate;
    private Quantity quantity;
    private Set<Tag> tags;
    private ReminderThreshold reminderThreshold;

    public XpireItemBuilder() {
        name = new Name(DEFAULT_NAME);
        expiryDate = new ExpiryDate(DEFAULT_EXPIRY_DATE);
        quantity = new Quantity(DEFAULT_QUANTITY);
        tags = new TreeSet<>(new TagComparator());
        reminderThreshold = new ReminderThreshold(DEFAULT_THRESHOLD);
    }

    /**
     * Initializes the XpireItemBuilder with the data of {@code xpireItemToCopy}.
     */
    public XpireItemBuilder(XpireItem xpireItemToCopy) {
        name = xpireItemToCopy.getName();
        expiryDate = xpireItemToCopy.getExpiryDate();
        quantity = xpireItemToCopy.getQuantity();
        TreeSet<Tag> set = new TreeSet<>(new TagComparator());
        set.addAll(xpireItemToCopy.getTags());
        tags = set;
        this.reminderThreshold = xpireItemToCopy.getReminderThreshold();
    }

    /**
     * Sets the {@code Name} of the {@code XpireItem} that we are building.
     */
    public XpireItemBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code XpireItem} that we are building.
     */
    public XpireItemBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code XpireItem} that we are building.
     */
    public XpireItemBuilder withExpiryDate(String expiryDate) {
        this.expiryDate = new ExpiryDate(expiryDate);
        return this;
    }

    /**
     * Sets the {@code ReminderThreshold} of the {@code XpireItem} that we are building.
     */
    public XpireItemBuilder withReminderThreshold(String threshold) {
        this.reminderThreshold = new ReminderThreshold(threshold);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code XpireItem} that we are building.
     */
    public XpireItemBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Constructs a new {@code XpireItem} with the modified fields.
     */
    public XpireItem build() {
        XpireItem newXpireItem = new XpireItem(name, expiryDate, quantity, tags);
        newXpireItem.setReminderThreshold(reminderThreshold);
        return newXpireItem;
    }

}
