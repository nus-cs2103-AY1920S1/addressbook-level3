package seedu.billboard.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.person.*;
import seedu.billboard.model.person.Expense;
import seedu.billboard.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Billboard} with sample data.
 */
public class SampleDataUtil {
    public static Expense[] getSamplePersons() {
        return new Expense[] {
//            new Expense(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
//                new Address("Blk 30 Geylang Street 29, #06-40"),
//                getTagSet("friends")),
//            new Expense(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
//                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
//                getTagSet("colleagues", "friends")),
//            new Expense(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
//                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
//                getTagSet("neighbours")),
//            new Expense(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
//                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
//                getTagSet("family")),
//            new Expense(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
//                new Address("Blk 47 Tampines Street 20, #17-35"),
//                getTagSet("classmates")),
//            new Expense(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
//                new Address("Blk 45 Aljunied Street 85, #11-31"),
//                getTagSet("colleagues"))
                new Expense(new Description("buy tea"), new Amount("1.23"), getTagSet("com1")),
                new Expense(new Description("buy copiO"), new Amount("1.50"), getTagSet("com2")),
                new Expense(new Description("buy lunch"), new Amount("3.70"), getTagSet("thedeck")),
                new Expense(new Description("buy book"), new Amount("77.3"), getTagSet("coop")),
                new Expense(new Description("bride prof"), new Amount("500.00"), getTagSet("LT13")),
                new Expense(new Description("buy weed"), new Amount("150.00"), getTagSet("PGP"))
        };
    }

    public static ReadOnlyBillboard getSampleAddressBook() {
        Billboard sampleAb = new Billboard();
        for (Expense sampleExpense : getSamplePersons()) {
            sampleAb.addPerson(sampleExpense);
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
