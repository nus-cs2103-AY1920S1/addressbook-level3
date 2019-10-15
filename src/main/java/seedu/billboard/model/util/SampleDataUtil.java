package seedu.billboard.model.util;

import java.util.Arrays;
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
            new Expense(new Name("buy tea"), new Description("tea from foodclique"),
                    new Amount("1.23"), new CreatedDateTime("12/12/2018"), getTagSet("com1")),
            new Expense(new Name("buy kopiO"), new Description(""),
                    new Amount("1.50"), new CreatedDateTime("13/04/2019 2359"), getTagSet("com2")),
            new Expense(new Name("buy lunch"), new Description("chicken rice from deck"),
                    new Amount("3.70"), new CreatedDateTime("1/1/2018"), getTagSet("thedeck")),
            new Expense(new Name("buy book"), new Description("so expensive wtf"),
                    new Amount("77.3"), new CreatedDateTime("3/05/2019"), getTagSet("coop")),
            new Expense(new Name("bride prof"), new Description(""),
                    new Amount("500.00"), new CreatedDateTime("31/12/2018 1200"), getTagSet("LT13")),
            new Expense(new Name("buy weed"), new Description("jk haha"),
                    new Amount("150.00"), new CreatedDateTime("10/10/2019"), getTagSet("PGP"))
        };
    }

    public static ReadOnlyBillboard getSampleBillboard() {
        Billboard sampleBb = new Billboard();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleBb.addExpense(sampleExpense);
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
