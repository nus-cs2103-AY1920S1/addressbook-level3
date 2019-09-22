package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Description("Alex Yeoh"), new Price("87438807"),
                    getTagSet("friends")),
            new Expense(new Description("Bernice Yu"), new Price("99272758"),
                    getTagSet("colleagues", "friends")),
            new Expense(new Description("Charlotte Oliveiro"), new Price("93210283"),
                    getTagSet("neighbours")),
            new Expense(new Description("David Li"), new Price("91031282"),
                getTagSet("family")),
            new Expense(new Description("Irfan Ibrahim"), new Price("92492021"),
                getTagSet("classmates")),
            new Expense(new Description("Roy Balakrishnan"), new Price("92624417"),
                getTagSet("colleagues"))
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
