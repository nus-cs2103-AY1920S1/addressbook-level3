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
            new Spending(new Name("Chicken rice"), new Date("1/1/2019"), new Remark("Tasty."),
                new Cost("4.00"),
                getTagSet("food")),
            new Spending(new Name("Taxi"), new Date("2/1/2019"), new Remark("I was late for school."),
                new Cost("25.50"),
                getTagSet("transport", "regret")),
            new Spending(new Name("Math textbook"), new Date("3/1/2019"), new Remark("I hate this."),
                new Cost("32.00"),
                getTagSet("education", "regret")),
            new Spending(new Name("Movie"), new Date("4/1/2019"), new Remark("Excited!"),
                new Cost("10"),
                getTagSet("entertainment")),
            new Spending(new Name("Gold Watch"), new Date("5/1/2019"), new Remark("It's about time."),
                new Cost("250"),
                getTagSet("entertainment", "aesthetics")),
            new Spending(new Name("Concert ticket"), new Date("6/1/2019"), new Remark("I love music festivals."),
                new Cost("180"),
                getTagSet("entertainment"))
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
