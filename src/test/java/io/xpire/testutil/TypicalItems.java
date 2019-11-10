package io.xpire.testutil;

import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_CORN;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_EGG;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_GRAPE;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_HONEY;
import static io.xpire.testutil.TypicalItemsFields.VALID_EXPIRY_DATE_ICE_CREAM;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_BANANA;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_CORN;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_DUCK;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_EGG;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_GRAPE;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_HONEY;
import static io.xpire.testutil.TypicalItemsFields.VALID_NAME_ICE_CREAM;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_EGG;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_CORIANDER;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_CORN;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_FISH;
import static io.xpire.testutil.TypicalItemsFields.VALID_REMINDER_THRESHOLD_GRAPE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_FRIDGE;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_HERB;
import static io.xpire.testutil.TypicalItemsFields.VALID_TAG_PROTEIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;

/**
 * A utility class containing a list of {@code XpireItem} objects to be used in tests.
 */
public class TypicalItems {

    // ====================== Xpire Items ==========================================================================

    // a basic item
    public static final XpireItem APPLE = new XpireItemBuilder()
            .withName(VALID_NAME_APPLE)
            .withExpiryDate(VALID_EXPIRY_DATE_APPLE)
            .build();

    // a basic item
    public static final XpireItem BANANA = new XpireItemBuilder()
            .withName(VALID_NAME_BANANA)
            .withExpiryDate(VALID_EXPIRY_DATE_BANANA)
            .build();

    // a basic item with reminder
    public static final XpireItem CORN = new XpireItemBuilder()
            .withName(VALID_NAME_CORN)
            .withExpiryDate(VALID_EXPIRY_DATE_CORN)
            .withReminderThreshold(VALID_REMINDER_THRESHOLD_CORN)
            .build();

    // a basic item with tags
    public static final XpireItem DUCK = new XpireItemBuilder()
            .withName(VALID_NAME_DUCK)
            .withExpiryDate(VALID_EXPIRY_DATE_DUCK)
            .withTags(VALID_TAG_FRIDGE, VALID_TAG_PROTEIN)
            .build();

    // a basic item with more than one quantity
    public static final XpireItem EGG = new XpireItemBuilder()
            .withName(VALID_NAME_EGG)
            .withExpiryDate(VALID_EXPIRY_DATE_EGG)
            .withQuantity(VALID_QUANTITY_EGG)
            .build();

    // with all fields (tags, reminder threshold, quantity)
    public static final XpireItem FISH = new XpireItemBuilder()
            .withName(VALID_NAME_FISH)
            .withExpiryDate(VALID_EXPIRY_DATE_FISH)
            .withQuantity(VALID_QUANTITY_FISH)
            .withTags(VALID_TAG_FRIDGE)
            .withReminderThreshold(VALID_REMINDER_THRESHOLD_FISH)
            .build();

    // expiring soon
    public static final XpireItem GRAPE = new XpireItemBuilder()
            .withName(VALID_NAME_GRAPE)
            .withExpiryDate(VALID_EXPIRY_DATE_GRAPE)
            .withReminderThreshold(VALID_REMINDER_THRESHOLD_GRAPE)
            .build();

    // expires today
    public static final XpireItem HONEY = new XpireItemBuilder()
            .withName(VALID_NAME_HONEY)
            .withExpiryDate(VALID_EXPIRY_DATE_HONEY)
            .build();

    // expired for a longer time
    public static final XpireItem ICE_CREAM = new XpireItemBuilder()
            .withName(VALID_NAME_ICE_CREAM)
            .withExpiryDate(VALID_EXPIRY_DATE_ICE_CREAM)
            .build();

    // sample item to test JsonSerializableXpire
    public static final XpireItem CORIANDER = new XpireItemBuilder()
            .withName(VALID_NAME_CORIANDER)
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
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CORN, DUCK, EGG, FISH, GRAPE, HONEY, ICE_CREAM));
    }

    public static List<Item> getTypicalReplenishListItems() {
        return new ArrayList<>(Arrays.asList(BAGEL, CHOCOLATE, COOKIE, BISCUIT));
    }

}
