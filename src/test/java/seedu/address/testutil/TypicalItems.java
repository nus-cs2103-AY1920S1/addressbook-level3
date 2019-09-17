package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ExpiryDateTracker;
import seedu.address.model.item.Item;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item BANANA = new ItemBuilder().withName("Banana")
                                                     .withExpiryDate("1/2/2019")
                                                      .withTags("fruit").build();

    public static final Item APPLE = new ItemBuilder().withName("Apple")
                                                      .withExpiryDate("1/2/2019")
                                                      .withTags("fruit").build();

    public static final Item KIWI = new ItemBuilder().withName("Kiwi")
                                                      .withExpiryDate("1/2/2019")
                                                      .withTags("fruit").build();

    public static final Item MILK = new ItemBuilder().withName("Milk")
                                                     .withExpiryDate("1/2/2019")
                                                     .withTags("drinks").build();

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static ExpiryDateTracker getTypicalExpiryDateTracker() {
        ExpiryDateTracker edt = new ExpiryDateTracker();
        for (Item item : getTypicalItems()) {
            edt.addItem(item);
        }
        return edt;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(MILK, BANANA, APPLE));
    }
}
