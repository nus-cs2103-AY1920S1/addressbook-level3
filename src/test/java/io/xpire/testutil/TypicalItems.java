package io.xpire.testutil;

import static io.xpire.testutil.TypicalItemsFields.IN_A_MONTH;
import static io.xpire.testutil.TypicalItemsFields.IN_A_WEEK;
import static io.xpire.testutil.TypicalItemsFields.IN_TWO_WEEKS;
import static io.xpire.testutil.TypicalItemsFields.PASSED_A_DAY;
import static io.xpire.testutil.TypicalItemsFields.PASSED_A_WEEK;
import static io.xpire.testutil.TypicalItemsFields.TODAY;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_EXPIRED_MILK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_EXPIRED_ORANGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_EXPIRING_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_EXPIRED_MILK;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_EXPIRED_ORANGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_EXPIRING_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_EXPIRING_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_JELLY;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_KIWI;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_HERB;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_PROTEIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.model.Xpire;
import io.xpire.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item BANANA = new ItemBuilder().withName(VALID_NAME_BANANA)
                                            .withExpiryDate(IN_TWO_WEEKS)
                                            .withQuantity(VALID_QUANTITY_BANANA).build();

    public static final Item KIWI = new ItemBuilder().withName(VALID_NAME_KIWI)
                                                      .withExpiryDate(IN_A_MONTH)
                                                      .withQuantity(VALID_QUANTITY_KIWI)
                                                      .withReminderThreshold(VALID_REMINDER_THRESHOLD_KIWI).build();

    //with tags
    public static final Item DUCK = new ItemBuilder().withName(VALID_NAME_DUCK)
                                                     .withExpiryDate(IN_A_MONTH)
                                                     .withTags(VALID_TAG_FRIDGE, VALID_TAG_PROTEIN)
                                                     .build();

    //with all fields (tags, reminder threshold, quantity)
    public static final Item JELLY = new ItemBuilder().withName(VALID_NAME_JELLY)
                                                        .withExpiryDate(IN_A_MONTH)
                                                        .withQuantity(VALID_QUANTITY_JELLY)
                                                        .withTags(VALID_TAG_FRIDGE)
                                                        .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                                                        .build();

    // expiring soon
    public static final Item EXPIRING_FISH = new ItemBuilder().withName(VALID_NAME_EXPIRING_FISH)
                                                               .withExpiryDate(IN_A_WEEK)
                                                               .withQuantity(VALID_QUANTITY_EXPIRING_FISH)
                                                               .withReminderThreshold(
                                                                       VALID_REMINDER_THRESHOLD_EXPIRING_FISH)
                                                               .build();
    // already expired
    public static final Item EXPIRED_APPLE = new ItemBuilder().withName(VALID_NAME_APPLE)
                                                              .withExpiryDate(TODAY)
                                                              .withQuantity(VALID_QUANTITY_APPLE).build();

    public static final Item EXPIRED_ORANGE = new ItemBuilder().withName(VALID_NAME_EXPIRED_ORANGE)
                                                             .withExpiryDate(PASSED_A_DAY)
                                                             .withQuantity(VALID_QUANTITY_EXPIRED_ORANGE).build();

    // expired for a longer time
    public static final Item EXPIRED_MILK = new ItemBuilder().withName(VALID_NAME_EXPIRED_MILK)
                                                             .withExpiryDate(PASSED_A_WEEK)
                                                             .withQuantity(VALID_QUANTITY_EXPIRED_MILK).build();

    // sample item to test JsonSerializableXpire
    public static final Item CORIANDER = new ItemBuilder().withName(VALID_NAME_CORIANDER)
                                                          .withExpiryDate(VALID_EXPIRY_DATE_CORIANDER)
                                                          .withQuantity(VALID_QUANTITY_CORIANDER)
                                                          .withTags(VALID_TAG_HERB, VALID_TAG_FRIDGE)
                                                          .withReminderThreshold(VALID_REMINDER_THRESHOLD_CORIANDER)
                                                          .build();


    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code Xpire} with all the typical items.
     */
    public static Xpire getTypicalExpiryDateTracker() {
        Xpire edt = new Xpire();
        for (Item item : getTypicalItems()) {
            Item copyItem = new Item(item);
            edt.addItem(copyItem);
        }
        return edt;
    }

    public static Xpire getSampleTracker() {
        Xpire edt = new Xpire();
        edt.addItem(new Item(CORIANDER));
        return edt;
    }

    public static List<Item> getTypicalItems() {

        return new ArrayList<>(Arrays.asList(EXPIRED_MILK, BANANA, EXPIRED_APPLE, EXPIRED_ORANGE, EXPIRING_FISH, DUCK,
                JELLY));

    }
}
