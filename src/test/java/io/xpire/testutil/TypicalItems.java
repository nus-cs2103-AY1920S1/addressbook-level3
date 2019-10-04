package io.xpire.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item BANANA = new ItemBuilder().withName("Banana")
                                                     .withExpiryDate("01/02/2019")
                                                      .withTags("fruit").build();

    public static final Item APPLE = new ItemBuilder().withName("Apple")
                                                      .withExpiryDate("01/02/2019")
                                                      .withTags("fruit").build();

    public static final Item KIWI = new ItemBuilder().withName("Kiwi")
                                                      .withExpiryDate("01/02/2019")
                                                      .withTags("fruit").build();

    public static final Item MILK = new ItemBuilder().withName("Milk")
                                                     .withExpiryDate("01/02/2019")
                                                     .withTags("drinks").build();

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static Xpire getTypicalExpiryDateTracker() {
        Xpire edt = new Xpire();
        for (Item item : getTypicalItems()) {
            edt.addItem(item);
        }
        return edt;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(MILK, BANANA, APPLE));
    }
}
