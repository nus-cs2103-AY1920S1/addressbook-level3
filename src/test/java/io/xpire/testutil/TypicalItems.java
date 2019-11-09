package io.xpire.testutil;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.commons.util.DateUtil;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;

/**
 * A utility class containing a list of {@code XpireItem} objects to be used in tests.
 */
public class TypicalItems {


    public static final String TODAY = DateUtil.convertDateToString(LocalDate.now());
    public static final String IN_A_WEEK = DateUtil.convertDateToString(LocalDate.now().plusDays(7));
    public static final String IN_TWO_WEEKS = DateUtil.convertDateToString(LocalDate.now().plusDays(14));
    public static final String IN_A_MONTH = DateUtil.convertDateToString(LocalDate.now().plusDays(30));
    public static final String PASSED_A_DAY = DateUtil.convertDateToString(LocalDate.now().minusDays(1));
    public static final String PASSED_A_WEEK = DateUtil.convertDateToString(LocalDate.now().minusDays(7));

    // ====================== Xpire Items ==========================================================================

    public static final XpireItem BANANA = new XpireItemBuilder().withName(VALID_NAME_BANANA)
                                            .withExpiryDate(IN_TWO_WEEKS)
                                            .withQuantity(VALID_QUANTITY_BANANA).build();


    public static final XpireItem KIWI = new XpireItemBuilder().withName(VALID_NAME_KIWI)
                                                .withExpiryDate(IN_A_MONTH)
                                                      .withQuantity(VALID_QUANTITY_KIWI)
                                                      .withReminderThreshold(VALID_REMINDER_THRESHOLD_KIWI).build();

    public static final XpireItem DUCK = new XpireItemBuilder().withName(VALID_NAME_DUCK)
                                                   .withExpiryDate(IN_A_MONTH)
                                                     .withTags(VALID_TAG_FRIDGE, VALID_TAG_PROTEIN)
                                                     .build();

    //with all fields (tags, reminder threshold, quantity)
    public static final XpireItem JELLY = new XpireItemBuilder().withName(VALID_NAME_JELLY)
                                                        .withExpiryDate(IN_A_MONTH)
                                                        .withQuantity(VALID_QUANTITY_JELLY)
                                                        .withTags(VALID_TAG_FRIDGE)
                                                        .withReminderThreshold(VALID_REMINDER_THRESHOLD_JELLY)
                                                        .build();

    // expiring soon
    public static final XpireItem EXPIRING_FISH = new XpireItemBuilder().withName(VALID_NAME_EXPIRING_FISH)
                                                                .withExpiryDate(IN_A_WEEK)
                                                               .withQuantity(VALID_QUANTITY_EXPIRING_FISH)
                                                               .withReminderThreshold(
                                                                       VALID_REMINDER_THRESHOLD_EXPIRING_FISH)
                                                               .build();
    // already expired
    public static final XpireItem EXPIRED_APPLE = new XpireItemBuilder().withName(VALID_NAME_APPLE)
                                                                .withExpiryDate(TODAY)
                                                              .withQuantity(VALID_QUANTITY_APPLE).build();


    public static final XpireItem EXPIRED_ORANGE = new XpireItemBuilder().withName(VALID_NAME_EXPIRED_ORANGE)
                                                             .withExpiryDate(PASSED_A_DAY)
                                                             .withQuantity(VALID_QUANTITY_EXPIRED_ORANGE).build();

    // expired for a longer time
    public static final XpireItem EXPIRED_MILK = new XpireItemBuilder().withName(VALID_NAME_EXPIRED_MILK)
                                                             .withExpiryDate(PASSED_A_WEEK)
                                                             .withQuantity(VALID_QUANTITY_EXPIRED_MILK).build();

    // sample item to test JsonSerializableXpire
    public static final XpireItem CORIANDER = new XpireItemBuilder().withName(VALID_NAME_CORIANDER)
                                                          .withExpiryDate(VALID_EXPIRY_DATE_CORIANDER)
                                                          .withQuantity(VALID_QUANTITY_CORIANDER)
                                                          .withTags(VALID_TAG_HERB, VALID_TAG_FRIDGE)
                                                          .withReminderThreshold(VALID_REMINDER_THRESHOLD_CORIANDER)
                                                          .build();



    //====================== Replenish List Items ====================================================

    public static final Item BAGEL = new ItemBuilder().withName("Bagel").build();

    public static final Item CHOCOLATE = new ItemBuilder().withName("Chocolate")
                                            .withTags("Cadbury", "Cocoa").build();

    public static final Item COOKIE = new ItemBuilder().withName("Cookie")
                                                          .withTags("Sweet").build();

    public static final Item BISCUIT = new ItemBuilder().withName("Biscuit")
                                                       .withTags("Sweet").build();

    public static final Item PAPAYA = new ItemBuilder().withName("Papaya").build();

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code Xpire} and {@code ReplenishList} with all the typical items.
     */
    public static ReadOnlyListView[] getTypicalLists() {
        Xpire edt = new Xpire();
        for (XpireItem xpireItem : getTypicalXpireItems()) {
            XpireItem copyXpireItem = new XpireItem(xpireItem);
            edt.addItem(copyXpireItem);
        }
        ReplenishList replenishList = new ReplenishList();
        for (Item replenishItem : getTypicalReplenishListItems()) {
            Item copyReplenishItem = new Item(replenishItem);
            replenishList.addItem(copyReplenishItem);
        }
        return new ReadOnlyListView[]{edt, replenishList};
    }

    /**
     * Returns an {@code Xpire} with all the typical items.
     */
    public static Xpire getTypicalExpiryDateTracker() {
        Xpire edt = new Xpire();
        for (XpireItem xpireItem : getTypicalXpireItems()) {
            XpireItem copyXpireItem = new XpireItem(xpireItem);
            edt.addItem(copyXpireItem);
        }
        return new Xpire(edt);
    }

    public static Xpire getSampleTracker() {
        Xpire edt = new Xpire();
        edt.addItem(new XpireItem(CORIANDER));
        return edt;
    }

    public static List<XpireItem> getTypicalXpireItems() {
        return new ArrayList<>(Arrays.asList(EXPIRED_MILK, BANANA, EXPIRED_APPLE, EXPIRED_ORANGE, EXPIRING_FISH, DUCK,
                JELLY));

    }

    public static List<Item> getTypicalReplenishListItems() {
        return new ArrayList<>(Arrays.asList(BAGEL, CHOCOLATE, COOKIE, BISCUIT));
    }

}
