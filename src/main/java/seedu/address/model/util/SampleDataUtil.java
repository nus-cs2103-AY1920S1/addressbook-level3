package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.util.UniqueIdentifierGenerator;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Description("Chickens"), new Price("30"),
                    getTagSet("friends"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Chicken Rice"), new Price("2.50"),
                    getTagSet("nusDeck", "oldFriends"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Popcorn Chicken"), new Price("12"),
                    getTagSet("fairprice", "frozen"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("David Li"), new Price("15000"),
                getTagSet("family"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Invite only Chicken Event"), new Price("1000"),
                getTagSet("chickenNetworking"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Chicken Rice extra Chicken"), new Price("15"),
                getTagSet("nusDeck"), UniqueIdentifierGenerator.generateRandomUniqueIdentifier())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Expense sampleExpense : getSampleExpenses()) {
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
