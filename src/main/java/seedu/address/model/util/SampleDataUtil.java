package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.FeedList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyFeedList;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Tag;
import seedu.address.model.feed.Feed;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Eatery[] getSampleEateries() {
        return new Eatery[] {
            new Eatery(new Name("Alex Yeoh"),
                true,
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new Category("Chinese"),
                getTagSet("friends")),
            new Eatery(new Name("Bernice Yu"),
                true,
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new Category("Chinese"),
                getTagSet("colleagues", "friends")),
            new Eatery(new Name("Charlotte Oliveiro"),
                true,
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new Category("Western"),
                getTagSet("neighbours")),
            new Eatery(new Name("David Li"),
                true,
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new Category("Chinese"),
                getTagSet("family")),
            new Eatery(new Name("Irfan Ibrahim"),
                true,
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new Category("Malay"),
                getTagSet("classmates")),
            new Eatery(new Name("Roy Balakrishnan"),
                true,
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new Category("Indian"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Eatery sampleEatery : getSampleEateries()) {
            sampleAb.addEatery(sampleEatery);
        }
        return sampleAb;
    }

    public static ReadOnlyFeedList getSampleFeedList() {
        FeedList sampleFl = new FeedList();
        sampleFl.addFeed(new Feed("Seth Lui", "https://sethlui.com/feed"));
        sampleFl.addFeed(new Feed("Eatbook", "https://eatbook.sg/feed"));
        return sampleFl;
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
