package io.xpire.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import io.xpire.commons.util.StringUtil;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.item.XpireItem;
import io.xpire.model.tag.Tag;


/**
 * Contains utility methods for populating {@code Xpire} and {@Code ReplenishList} with sample data.
 */
public class SampleDataUtil {

    public static XpireItem[] getSampleItems() {
        return new XpireItem[] {
            new XpireItem(new Name("Ice Cream"), new ExpiryDate("12/10/2019"),
                new Quantity("1"), getTagSet("Food")),
            new XpireItem(new Name("Jeremy Vaseline"), new ExpiryDate("30/06/2022"),
                new Quantity("10"), getTagSet("Moisturiser", "Lube"))
        };
    }

    public static Item[] getSampleReplenishItems() {
        return new Item[] {
            new Item(new Name("Cherry"), getTagSet("Food")),
            new Item(new Name("Coco Crunch"), getTagSet("Chocolate", "Breakfast"))
        };
    }

    public static ReadOnlyListView<XpireItem> getSampleXpire() {
        Xpire sampleXpire = new Xpire();
        for (XpireItem sampleXpireItem : getSampleItems()) {
            sampleXpire.addItem(sampleXpireItem);
        }
        return sampleXpire;
    }

    public static ReadOnlyListView<Item> getSampleReplenishList() {
        ReplenishList sampleReplenishList = new ReplenishList();
        for (Item sampleReplenishItem : getSampleReplenishItems()) {
            sampleReplenishList.addItem(sampleReplenishItem);
        }
        return sampleReplenishList;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(StringUtil::convertToSentenceCase)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
