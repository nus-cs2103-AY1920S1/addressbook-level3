package seedu.moneygowhere.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Contains utility methods for populating {@code SpendingBook} with sample data.
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

    public static ReadOnlySpendingBook getSampleSpendingBook() {
        SpendingBook sampleAb = new SpendingBook();
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
