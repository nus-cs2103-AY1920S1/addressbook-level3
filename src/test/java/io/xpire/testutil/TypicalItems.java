package io.xpire.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    //no tags; no reminder threshold
    public static final Item BANANA = new ItemBuilder().withName("Banana")
                                                     .withExpiryDate("01/02/2020")
                                                      .build();
    //no tags; no reminder threshold
    public static final Item APPLE = new ItemBuilder().withName("Apple")
                                                      .withExpiryDate("01/02/2020")
                                                      .build();
    //no tags; no reminder threshold
    public static final Item KIWI = new ItemBuilder().withName("Kiwi")
                                                      .withExpiryDate("01/02/2020")
                                                      .build();
    //no tags; no reminder threshold
    public static final Item MILK = new ItemBuilder().withName("Milk")
                                                     .withExpiryDate("01/02/2020")
                                                     .build();
    //with tags
    public static final Item TOFU = new ItemBuilder().withName("Tofu")
                                                     .withExpiryDate("01/02/2020")
                                                     .withTags("Fridge", "Protein")
                                                     .build();
    //with reminder threshold
    public static final Item RICE = new ItemBuilder().withName("Rice")
                                                     .withExpiryDate("01/02/2020")
                                                     .withReminderThreshold("7")
                                                     .build();
    //with all fields (tags, reminder threshold)
    public static final Item AVOCADO = new ItemBuilder().withName("Avocado")
                                                        .withExpiryDate("01/02/2020")
                                                        .withTags("Fat")
                                                        .withReminderThreshold("3")
                                                        .build();

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical items.
     */
    public static Xpire getTypicalExpiryDateTracker() {
        Xpire edt = new Xpire();
        for (Item item : getTypicalItems()) {
            edt.addItem(item);
        }
        return edt;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(MILK, BANANA, APPLE, TOFU, RICE, AVOCADO));
    }
}
