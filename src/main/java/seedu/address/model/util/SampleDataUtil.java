package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.expense.*;
import seedu.address.model.expense.Description;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Description("Alex Yeoh"), new Price("87438807"), /*new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),*/
                getTagSet("friends")),
            new Expense(new Description("Bernice Yu"), new Price("99272758"), /*new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),*/
                getTagSet("colleagues", "friends")),
            new Expense(new Description("Charlotte Oliveiro"), new Price("93210283"), /*new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),*/
                getTagSet("neighbours")),
            new Expense(new Description("David Li"), new Price("91031282"), /*new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),*/
                getTagSet("family")),
            new Expense(new Description("Irfan Ibrahim"), new Price("92492021"), /*new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),*/
                getTagSet("classmates")),
            new Expense(new Description("Roy Balakrishnan"), new Price("92624417"), /*new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),*/
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
