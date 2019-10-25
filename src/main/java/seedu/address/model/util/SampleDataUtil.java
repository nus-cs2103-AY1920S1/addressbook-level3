package seedu.address.model.util;

import seedu.address.model.MooLah;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.category.Category;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.util.UniqueIdentifierGenerator;


/**
 * Contains utility methods for populating {@code MooLah} with sample data.
 */
public class SampleDataUtil {

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Description("Chickens"), new Price("30"),
                    getCategory("friends"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("David Li"), new Price("15000"),
                    getCategory("family"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Invite only Chicken Event"), new Price("1000"),
                    getCategory("chickenNetworking"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Chicken Rice extra Chicken"), new Price("15"),
                    getCategory("nusDeck"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        MooLah sampleAb = new MooLah();
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleAb.addExpense(sampleExpense);
        }
        return sampleAb;
    }

    public static Category getCategory(String category) {
        return new Category(category);
    }

}
