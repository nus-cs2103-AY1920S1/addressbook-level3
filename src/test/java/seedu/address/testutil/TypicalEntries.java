package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Entry;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalEntries {

    public static final Entry FOOD_EXPENSE = new EntryBuilder().withDesc("pgp mala").withAmt(5.50)
            .withTags("food").build();
    public static final Entry CLOTHING_EXPENSE = new EntryBuilder().withDesc("cotton on jeans on sale").withAmt(14.90)
            .withTags("want", "clothes").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEntries() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Entry entry : getTypicalEntries()) {
            ab.addEntry(entry);
        }
        return ab;
    }

    public static List<Entry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(FOOD_EXPENSE, CLOTHING_EXPENSE));
    }
}
