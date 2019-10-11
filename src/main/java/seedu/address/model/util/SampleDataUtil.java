package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ExpenseList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.expense.Amount;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ExpenseList} with sample data.
 */
public class SampleDataUtil {

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Name("Coffee"), new Amount("$1.8"), new Date("1245"),
                getTagSet("food")),
            new Expense(new Name("Textbook"), new Amount("$23.50"), new Date("930"),
                getTagSet("education", "school")),
            new Expense(new Name("Earphone"), new Amount("$45"), new Date("10/12/2019 1800"),
                getTagSet("utility")),
            new Expense(new Name("Hang out"), new Amount("$50"), new Date("15/12/2019 2100"),
                getTagSet("entertainment")),
            new Expense(new Name("Travel to Paris"), new Amount("€850"), new Date("25/12/2019 800"),
                getTagSet("travel")),
            new Expense(new Name("Gift for duke"), new Amount("$30"), new Date("1/11/2019"),
                getTagSet("relationship"))
        };
    }

    public static ReadOnlyExpenseList getSampleExpenseList() {
        ExpenseList sampleEl = new ExpenseList();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleEl.addExpense(sampleExpense);
        }
        return sampleEl;
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
