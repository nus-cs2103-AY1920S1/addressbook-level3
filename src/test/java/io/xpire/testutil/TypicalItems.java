package io.xpire.testutil;

import static io.xpire.model.item.ExpiryDate.DATE_FORMAT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.commons.util.DateUtil;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final String TODAY = DateUtil.convertDateToString(LocalDate.now(), DATE_FORMAT);
    public static final String IN_A_WEEK = DateUtil.convertDateToString(LocalDate.now().plusDays(7), DATE_FORMAT);
    public static final String IN_TWO_WEEKS = DateUtil.convertDateToString(LocalDate.now().plusDays(14), DATE_FORMAT);
    public static final String IN_A_MONTH = DateUtil.convertDateToString(LocalDate.now().plusDays(30), DATE_FORMAT);
    public static final String PASSED_A_DAY = DateUtil.convertDateToString(LocalDate.now().minusDays(1), DATE_FORMAT);
    public static final String PASSED_A_WEEK = DateUtil.convertDateToString(LocalDate.now().minusDays(7), DATE_FORMAT);

    public static final Item BANANA = new ItemBuilder().withName("Banana")
                                            .withExpiryDate(IN_TWO_WEEKS)
                                            .withQuantity("5").build();

    public static final Item APPLE = new ItemBuilder().withName("Apple")
                                                      .withExpiryDate(TODAY)
                                                      .withQuantity("1").build();

    public static final Item KIWI = new ItemBuilder().withName("Kiwi")
                                                      .withExpiryDate(IN_A_MONTH)
                                                      .withQuantity("2")
                                                      .withThreshold("20").build();

    //with tags
    public static final Item DUCK = new ItemBuilder().withName("Duck")
                                                     .withExpiryDate(IN_A_MONTH)
                                                     .withTags("Fridge", "Protein")
                                                     .build();

    //with all fields (tags, reminder threshold, quantity)
    public static final Item JELLY = new ItemBuilder().withName("Jelly")
                                                        .withExpiryDate(IN_A_MONTH)
                                                        .withQuantity("4")
                                                        .withTags("Fridge")
                                                        .withReminderThreshold("3")
                                                        .build();

    // expiring soon
    public static final Item EXPIRING_FISH = new ItemBuilder().withName("Fish")
                                                               .withExpiryDate(IN_A_WEEK)
                                                               .withQuantity("1")
                                                               .withThreshold("8").build();
    // already expired
    public static final Item EXPIRED_ORANGE = new ItemBuilder().withName("Orange")
                                                             .withExpiryDate(PASSED_A_DAY)
                                                             .withQuantity("1").build();

    // expired for a longer time
    public static final Item EXPIRED_MILK = new ItemBuilder().withName("Milk")
                                                             .withExpiryDate(PASSED_A_WEEK)
                                                             .withQuantity("2").build();

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

        return new ArrayList<>(Arrays.asList(EXPIRED_MILK, BANANA, APPLE, EXPIRED_ORANGE, EXPIRING_FISH, DUCK,
                JELLY));

    }
}
