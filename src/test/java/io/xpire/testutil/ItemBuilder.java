package io.xpire.testutil;

import java.util.Set;
import java.util.TreeSet;

import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;
import io.xpire.model.util.SampleDataUtil;

/**
 * A utility class to help with building Item objects.
 */

public class ItemBuilder {
    public static final String DEFAULT_NAME = "Earl Grey Tea";

    private Name name;
    private Set<Tag> tags;

    public ItemBuilder() {
        name = new Name(DEFAULT_NAME);
        tags = new TreeSet<>(new TagComparator());
    }

    /**
     * Initializes the ItemBuilder with the data of {@code itemToCopy}.
     */
    public ItemBuilder(Item itemToCopy) {
        name = itemToCopy.getName();
        TreeSet<Tag> set = new TreeSet<>(new TagComparator());
        set.addAll(itemToCopy.getTags());
        tags = set;
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
     * Constructs a new {@code Item} with the modified fields.
     */
    public Item build() {
        Item newItem = new Item(name, tags);
        return newItem;
    }

}
