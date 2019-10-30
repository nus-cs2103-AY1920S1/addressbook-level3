package seedu.address.cashier.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_INDEX_CASHIER;
import static seedu.address.cashier.ui.CashierMessages.NO_SUCH_ITEM_CASHIER;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.inventory.model.Item;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.TypicalItem;

public class InventoryListTest {

    private InventoryList inventoryList = new InventoryList();

    public void setInventoryList() {
        ArrayList<Item> list = new ArrayList<>();
        list.add(TypicalItem.FISH_BURGER);
        list.add(TypicalItem.STORYBOOK);
        list.add(TypicalItem.WATER);
        list.add(TypicalItem.CHIPS);
        inventoryList = new InventoryList(list);
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), inventoryList.getiArrayList());
    }

    @Test
    public void setTransactionList_successful() {
        ArrayList<Item> expectedList = new ArrayList<>();
        expectedList.add(TypicalItem.BLACK_SHIRT);
        expectedList.add(TypicalItem.STORYBOOK);
        expectedList.add(TypicalItem.WATER);
        expectedList.add(TypicalItem.CHIPS);
        inventoryList = new InventoryList(expectedList);

        inventoryList.set(0, TypicalItem.BLACK_SHIRT);
        InventoryList expected = new InventoryList();
        assertEquals(inventoryList, expected);
    }

    @Test
    public void setTransactionList_outOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () ->
                inventoryList.set(2, TypicalItem.BLACK_SHIRT));
    }

    @Test
    public void setNull_throwsNullPointerException() {
        setInventoryList();
        assertThrows(NullPointerException.class, () -> inventoryList.set(0, null));
    }

    @Test
    public void getOriginalItemByDescription_successful() throws NoSuchItemException {
        setInventoryList();

        // item with same description as "Fish Burger", but with other different fields
        Item i = new ItemBuilder()
                .withCategory("Fast food")
                .withQuantity(23)
                .build();

        Item originalItem = inventoryList.getOriginalItem(i.getDescription());
        assertEquals(originalItem.getDescription(), i.getDescription());
        assertEquals(TypicalItem.FISH_BURGER, originalItem);
        inventoryList = new InventoryList();
    }

    @Test
    public void getOriginalItemByDescription_differentDescription_failure() {
        setInventoryList();

        // item with same fields as "Fish Burger", but with different description only
        Item i = new ItemBuilder()
                .withDescription("Fast food")
                .build();

        String expectedMessage = NO_SUCH_ITEM_CASHIER;
        Assert.assertThrows(Exception.class, expectedMessage, () -> inventoryList.getOriginalItem(i.getDescription()));
        inventoryList = new InventoryList();
    }

    @Test
    public void getOriginalItemByItem_successful() throws NoSuchItemException {
        setInventoryList();

        // item with same description and attributes as "Fish Burger", but with different quantity only
        Item i = new ItemBuilder()
                .withQuantity(23)
                .build();

        Item originalItem = inventoryList.getOriginalItem(i);
        assertTrue(originalItem.isSameItem(i));
        inventoryList = new InventoryList();
    }

    @Test
    public void getOriginalItemByItem_allDifferentFields_failure() {
        setInventoryList();

        // item with all different fields as "Fish Burger"
        Item i = new ItemBuilder()
                .withDescription("beef")
                .withCategory("fast food")
                .withCost(1.11)
                .withPrice(3.33)
                .withQuantity(23)
                .build();

        String expectedMessage = NO_SUCH_ITEM_CASHIER;
        Assert.assertThrows(Exception.class, expectedMessage, () -> inventoryList.getOriginalItem(i));
        inventoryList = new InventoryList();
    }

    @Test
    public void getItemByIndex_success() throws NoSuchIndexException {
        setInventoryList();

        Item item1 = inventoryList.getItemByIndex(0);
        assertEquals(item1, TypicalItem.FISH_BURGER);

        Item item2 = inventoryList.getItemByIndex(1);
        assertEquals(item2, TypicalItem.STORYBOOK);
    }

    @Test
    public void getItemByIndex_invalidZeroIndex_failure() {
        setInventoryList();

        String expectedMessage = NO_SUCH_INDEX_CASHIER;
        Assert.assertThrows(Exception.class, expectedMessage, () -> inventoryList.getItemByIndex(-3));
        Assert.assertThrows(Exception.class, expectedMessage, () -> inventoryList.getItemByIndex(800));
        inventoryList = new InventoryList();
    }

    @Test
    public void hasItem_assertTrue() {
        setInventoryList();

        // item with same description and attributes as "Fish Burger", but with different quantity only
        Item i = new ItemBuilder()
                .withQuantity(23)
                .build();

        assertTrue(inventoryList.hasItem(i.getDescription()));
        inventoryList = new InventoryList();
    }

    @Test
    public void hasItem_invalidDescription_assertFalse() {
        setInventoryList();

        // item with same fields as "Fish Burger" except description
        Item i = new ItemBuilder()
                .withDescription("beef")
                .build();

        assertFalse(inventoryList.hasItem(i.getDescription()));
        inventoryList = new InventoryList();
    }

    @Test
    public void size_assertTrue() {
        setInventoryList();
        assertEquals(4, inventoryList.size());
    }

    @Test
    public void getAllSalesDescriptionByCategory_validDescription_successful() {
        setInventoryList();

        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(TypicalItem.FISH_BURGER.getDescription());
        expectedList.add(TypicalItem.CHIPS.getDescription());

        ArrayList<String> list = inventoryList.getAllSalesDescriptionByCategory("food");
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expectedList.get(i), list.get(i));
        }
    }

    @Test
    public void getAllSalesDescriptionByCategory_invalidDescription_emptyListSuccessful() {
        setInventoryList();
        ArrayList<String> list = inventoryList.getAllSalesDescriptionByCategory("hii");
        assertEquals(0, list.size());
    }

}

