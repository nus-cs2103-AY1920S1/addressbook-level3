package seedu.billboard.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Billboard} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Welcome to Billboard"), new Description("Description goes here (optional)"),
                    new Amount("20.00"), new CreatedDateTime("08/11/2019"),
                    getTagSet("many", "tags")),
            new Expense(new Name("Paid bills"), new Description("I can include things like bills!"),
                    new Amount("40.00"), new CreatedDateTime("12/12/2018"), getTagSet("bills")),
            new Expense(new Name("Bought lunch"), new Description("And food!"),
                    new Amount("3.70"), new CreatedDateTime("1/1/2018"), getTagSet("food")),
            new Expense(new Name("Billboard is great"), new Description("Yes it is."),
                    new Amount("12.34"), new CreatedDateTime("3/05/2019"),
                    getTagSet("upgrade", "now")),
        };
    }

    public static ReadOnlyBillboard getSampleBillboard() {
        Billboard sampleBb = new Billboard();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleBb.addExpense(sampleExpense);
            List<String> tagNames = sampleExpense.getTags().stream().map(x -> x.tagName).collect(Collectors.toList());
            Set<Tag> tags = sampleBb.retrieveTags(tagNames);
            sampleBb.incrementCount(tags);
        }
        return sampleBb;
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
