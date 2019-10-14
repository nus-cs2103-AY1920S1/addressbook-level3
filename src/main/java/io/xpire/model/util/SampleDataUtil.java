package io.xpire.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import io.xpire.model.ReadOnlyXpire;
import io.xpire.model.Xpire;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Xpire} with sample data.
 */
public class SampleDataUtil {
    public static Item[] getSampleItems() {
        return new Item[] {
            new Item(new Name("Ice Cream"), new ExpiryDate("12/10/2019"),
                new Quantity("1"), getTagSet("food")),
            new Item(new Name("Jeremy Vaseline"), new ExpiryDate("30/06/2022"),
                new Quantity("10"), getTagSet("moisturiser", "lube")),
        };
    }

    public static ReadOnlyXpire getSampleXpire() {
        Xpire sampleXpire = new Xpire();
        for (Item sampleItem : getSampleItems()) {
            sampleXpire.addItem(sampleItem);
        }
        return sampleXpire;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
