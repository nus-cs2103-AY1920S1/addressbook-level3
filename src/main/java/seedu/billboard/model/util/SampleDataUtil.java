package seedu.billboard.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Billboard} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSamplePersons() {
        return new Expense[] {
            new Expense(new Name("buy tea"), new Description("tea from foodclique"),
                    new Amount("1.23"), getTagSet("com1")),
            new Expense(new Name("buy kopiO"), new Description(""),
                    new Amount("1.50"), getTagSet("com2")),
            new Expense(new Name("buy lunch"), new Description("chicken rice from deck"),
                    new Amount("3.70"), getTagSet("thedeck")),
            new Expense(new Name("buy book"), new Description("so expensive wtf"),
                    new Amount("77.3"), getTagSet("coop")),
            new Expense(new Name("bride prof"), new Description(""),
                    new Amount("500.00"), getTagSet("LT13")),
            new Expense(new Name("buy weed"), new Description("jk haha"),
                    new Amount("150.00"), getTagSet("PGP"))
        };
    }

    public static ReadOnlyBillboard getSampleAddressBook() {
        Billboard sampleAb = new Billboard();
        for (Expense sampleExpense : getSamplePersons()) {
            sampleAb.addExpense(sampleExpense);
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
