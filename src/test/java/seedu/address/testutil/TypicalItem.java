package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

public class TypicalItem {
    public static final Item FISH_BURGER = new ItemBuilder()
            .withId("1")
            .withPrice(3.25)
            .withCost(5.23)
            .withQuantity(3)
            .build();

    public static final Item STORYBOOK = new ItemBuilder()
            .withCategory("Book")
            .withDescription("The tale")
            .withId("2")
            .withPrice(2.25)
            .withCost(4.23)
            .withQuantity(6)
            .build();

    /**
     * Returns an {@code InventoryList} with all the typical items.
     */
    public static InventoryList getTypicalInventoryList() {
        /*TransactionList tl = new TransactionList();
        for (Transaction transaction : getTypicalTransactions()) {
            tl.add(transaction);
        }
        return tl;*/
        return new InventoryList(getTypicalItems());
    }

    public static ArrayList<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(FISH_BURGER, STORYBOOK));
    }










}
