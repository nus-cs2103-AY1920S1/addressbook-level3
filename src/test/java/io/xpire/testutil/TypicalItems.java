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
    public static final Item APPLE = new ItemBuilder().withName("Apple")
                                                      .withExpiryDate("01/02/2020")
                                                      .build();

    //no tags; no reminder threshold
    public static final Item BANANA = new ItemBuilder().withName("Banana")
                                                       .withExpiryDate("01/02/2020")
                                                       .build();
    //with quantity
    public static final Item CORN = new ItemBuilder().withName("Corn")
                                                     .withExpiryDate("01/02/2020")
                                                     .withQuantity("2")
                                                     .build();
    //with tags
    public static final Item DUCK = new ItemBuilder().withName("Duck")
                                                     .withExpiryDate("01/02/2020")
                                                     .withTags("Fridge", "Protein")
                                                     .build();
    //with reminder threshold
    public static final Item EGGS = new ItemBuilder().withName("Eggs")
                                                     .withExpiryDate("01/02/2020")
                                                     .withReminderThreshold("7")
                                                     .build();

    //with a recent expiry date and name with multiple words
    public static final Item FAKECRABSTICK = new ItemBuilder().withName("Fake Crab Stick")
                                                              .withExpiryDate("15/12/2019")
                                                              .build();

    //with all fields (tags, reminder threshold, quantity)
    public static final Item JELLY = new ItemBuilder().withName("Jelly")
                                                        .withExpiryDate("01/02/2020")
                                                        .withQuantity("4")
                                                        .withTags("Fridge")
                                                        .withReminderThreshold("3")
                                                        .build();
    //To-add item
    public static final Item KIWI = new ItemBuilder().withName("Kiwi")
                                                     .withExpiryDate("01/02/2020")
                                                     .build();

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical items.
     */
    public static Xpire getTypicalExpiryDateTracker() {
        Xpire edt = new Xpire();
        for (Item item : getTypicalItems()) {
            Item copyItem = new Item(item);
            edt.addItem(copyItem);
        }
        return edt;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CORN, DUCK, EGGS, FAKECRABSTICK, JELLY));
    }
}
