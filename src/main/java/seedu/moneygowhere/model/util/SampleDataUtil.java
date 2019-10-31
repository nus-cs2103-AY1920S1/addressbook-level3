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
            new Spending(new Name("Chicken rice"), new Date("20/10/2019"), new Remark("Good food"),
                new Cost("4.00"),
                getTagSet("food")),
            new Spending(new Name("Taxi"), new Date("21/10/2019"), new Remark("I was late for school"),
                new Cost("25.50"),
                getTagSet("transport", "regret")),
            new Spending(new Name("Math textbook"), new Date("22/10/2019"), new Remark("Wasted my money!"),
                new Cost("32.00"),
                getTagSet("education", "regret")),
            new Spending(new Name("Gold class movie ticket"), new Date("23/10/2019"), new Remark("GV ticket"),
                new Cost("20"),
                getTagSet("entertainment")),
            new Spending(new Name("Luxury Watch"), new Date("24/10/2019"), new Remark("Once in a lifetime purchase"),
                new Cost("600"),
                getTagSet("entertainment", "aesthetics")),
            new Spending(new Name("Concert ticket"), new Date("25/10/2019"), new Remark("Favourite band"),
                new Cost("180"),
                getTagSet("entertainment")),
            new Spending(new Name("Transport card fare"), new Date("31/10/2019"), new Remark("I would rather walk"),
                new Cost("15"),
                getTagSet("transport")),
            new Spending(new Name("Other things"), new Date("31/10/2019"), new Remark(""),
                new Cost("30.75"),
                getTagSet())
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
