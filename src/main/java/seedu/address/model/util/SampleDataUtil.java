package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ExpiryDateTracker;
import seedu.address.model.ReadOnlyExpiryDateTracker;
import seedu.address.model.item.ExpiryDate;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Item[] getSampleItems() {
        return new Item[] {
            new Item(new Name("Alex Yeoh"), new ExpiryDate("1234567"),
                getTagSet("friends")),
            new Item(new Name("Bernice Yu"), new ExpiryDate("99272758"),
                getTagSet("colleagues", "friends")),
        };
    }

    public static ReadOnlyExpiryDateTracker getSampleExpiryDateTracker() {
        ExpiryDateTracker sampleAb = new ExpiryDateTracker();
        for (Item sampleItem : getSampleItems()) {
            sampleAb.addItem(sampleItem);
        }
        return sampleAb;
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
