package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItem {
    public static final Item FISH_BURGER = new ItemBuilder()
            .withDescription("Burger")
            .withId("1")
            .withPrice(3.25)
            .withCost(5.23)
            .withQuantity(900)
            .build();

    public static final Item STORYBOOK = new ItemBuilder()
            .withCategory("Book")
            .withDescription("The tale")
            .withId("2")
            .withPrice(2.25)
            .withCost(4.23)
            .withQuantity(900)
            .build();

    // not meant to be added to list
    public static final Item BLACK_SHIRT = new ItemBuilder()
            .withCategory("Shirt")
            .withDescription("CCA shirt")
            .withCost(31.93)
            .withPrice(6.32)
            .withQuantity(85)
            .build();

    // not available for sale
    public static final Item PHONE_CASE = new ItemBuilder()
            .withCategory("accessory")
            .withDescription("black case")
            .withCost(4.23)
            .withPrice(0.00)
            .withQuantity(85)
            .build();

    // not meant to be added to list & not available for sale
    public static final Item WATER = new ItemBuilder()
            .withDescription("water")
            .withCategory("food")
            .withCost(4.23)
            .withPrice(0.00)
            .withQuantity(85)
            .build();

    // not meant to be added to list
    public static final Item CHIPS = new ItemBuilder()
            .withDescription("chips")
            .withCategory("food")
            .withCost(4.23)
            .withPrice(5.11)
            .withQuantity(85)
            .build();

    // not meant to be added to list
    public static final Item BURGER_AND_CHIPS = new ItemBuilder()
            .withDescription("burger chips")
            .withCategory("food")
            .withCost(4.23)
            .withPrice(5.11)
            .withQuantity(85)
            .build();

    /**
     * Returns an {@code InventoryList} with all the typical items.
     */
    public static InventoryList getTypicalInventoryList() {
        return new InventoryList(getTypicalItems());
    }

    /**
     * Returns an {@code InventoryList} with all the typical items.
     */
    public static seedu.address.inventory.util.InventoryList getTypicalInventoryListForInventoryUse() {
        /*TransactionList tl = new TransactionList();
        for (Transaction transaction : getTypicalTransactions()) {
            tl.add(transaction);
        }
        return tl;*/
        return new seedu.address.inventory.util.InventoryList(getTypicalItems());
    }

    public static ArrayList<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(FISH_BURGER, STORYBOOK, PHONE_CASE));
    }
}
