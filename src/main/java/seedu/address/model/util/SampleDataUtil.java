package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.spending.Cost;
import seedu.address.model.spending.Date;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Remark;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Spending[] getSampleSpendings() {
        return new Spending[]{
            new Spending(new Name("Alex Yeoh"), new Date("1/1/2019"), new Remark("Likes to play basketball."),
                new Cost("1.00"),
                getTagSet("friends")),
            new Spending(new Name("Bernice Yu"), new Date("2/1/2019"), new Remark("Likes to cook."),
                new Cost("2.50"),
                getTagSet("colleagues", "friends")),
            new Spending(new Name("Charlotte Oliveiro"), new Date("3/1/2019"), new Remark("Likes to watch movies."),
                new Cost("12.00"),
                getTagSet("neighbours")),
            new Spending(new Name("David Li"), new Date("4/1/2019"), new Remark("Likes to play games."),
                new Cost("16"),
                getTagSet("family")),
            new Spending(new Name("Irfan Ibrahim"), new Date("5/1/2019"), new Remark("Likes to play soccer"),
                new Cost("35"),
                getTagSet("classmates")),
            new Spending(new Name("Roy Balakrishnan"), new Date("6/1/2019"), new Remark("Likes to eat"),
                new Cost("60"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Spending sampleSpending : getSampleSpendings()) {
            sampleAb.addSpending(sampleSpending);
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
