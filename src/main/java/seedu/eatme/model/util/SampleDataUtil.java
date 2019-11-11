package seedu.eatme.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.eatme.model.EateryList;
import seedu.eatme.model.FeedList;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;
import seedu.eatme.model.feed.Feed;

/**
 * Contains utility methods for populating {@code EateryList} with sample data.
 */
public class SampleDataUtil {
    public static Eatery[] getSampleEateries() {
        return new Eatery[] {
            new Eatery(new Name("Ali Nachia Briyani Dam"),
                true,
                new Address("5 Tanjong Pagar Plaza, #02-04, Singapore 081005"),
                new Category("Malay"),
                getTagSet("nice")),
            new Eatery(new Name("Fei Fei Wanton Mee"),
                true,
                new Address("62 Joo Chiat Place, Singapore 427785"),
                new Category("Chinese"),
                getTagSet("delicious")),
            new Eatery(new Name("Swee Choon Tim Sum"),
                true,
                new Address("191 Jalan Besar, Singapore 208882"),
                new Category("Chinese"),
                getTagSet("supper")),
            new Eatery(new Name("Janggut Laksa"),
                true,
                new Address("1 Queensway, Queensway Shopping Centre, #01-59, Singapore 149053"),
                new Category("Peranakan"),
                getTagSet("delicious")),
            new Eatery(new Name("Seng Hiang Food Stall"),
                true,
                new Address("Blk 85 Bedok North Street 4, Fengshan Market & FoodCentre, Singapore 460085"),
                new Category("Chinese"),
                getTagSet("hawker", "cheap")),
            new Eatery(new Name("Selegie Soya Bean"),
                true,
                new Address("990 Upper Serangoon Road, Singapore 534734"),
                new Category("Chinese"),
                getTagSet("dessert"))
        };
    }

    public static ReadOnlyEateryList getSampleEateryList() {
        EateryList sampleEl = new EateryList();
        for (Eatery sampleEatery : getSampleEateries()) {
            sampleEl.addEatery(sampleEatery);
        }
        return sampleEl;
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
