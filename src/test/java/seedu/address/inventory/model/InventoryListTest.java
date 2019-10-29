package seedu.address.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.inventory.util.InventoryList;
import seedu.address.testutil.TypicalItem;

public class InventoryListTest {
    @Test
    public void getIndexTest_successful() {
        //Instantiate an InventoryList with a TypicalItem.FISH_BURGER
        InventoryList inventoryList = new InventoryList();
        inventoryList.add(TypicalItem.FISH_BURGER);

        int index = -1;
        try {
            index = inventoryList.getIndex("Burger");
        } catch (NoSuchItemException e) {
            fail();
        }
        assertEquals(0, index);
    }

    @Test
    public void getIndexTest_unsuccessful() {
        //Instantiate an InventoryList with a TypicalItem.FISH_BURGER
        InventoryList inventoryList = new InventoryList();
        inventoryList.add(TypicalItem.FISH_BURGER);

        int index = -1;
        try {
            index = inventoryList.getIndex("CCA shirt");
        } catch (NoSuchItemException e) {
            assertEquals(InventoryMessages.NO_SUCH_ITEM_INVENTORY, e.toString());
        }
    }

    @Test
    public void getItemByIndexTest_unsuccessful() {
        InventoryList inventoryList = new InventoryList();

        try {
            inventoryList.getItemByIndex(8);
        } catch (Exception e) {
            assertEquals(InventoryMessages.NO_SUCH_INDEX_INVENTORY, e.toString());
        }
    }

    @Test
    public void getOriginalItemTest_successful() {
        //Instantiate an InventoryList with a TypicalItem.FISH_BURGER
        InventoryList inventoryList = new InventoryList();
        inventoryList.add(TypicalItem.FISH_BURGER);
        inventoryList.add(TypicalItem.BLACK_SHIRT);
        inventoryList.add(TypicalItem.PHONE_CASE);

        Item retrieved = null;

        try {
            retrieved = inventoryList.getOriginalItem("burger");
        } catch (NoSuchItemException e) {
            fail();
        }

        assertEquals(TypicalItem.FISH_BURGER, retrieved);
    }
}
