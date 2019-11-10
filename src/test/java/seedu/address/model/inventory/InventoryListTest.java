package seedu.address.model.inventory;

import org.junit.jupiter.api.Test;
import seedu.address.model.inventory.exceptions.DuplicateInventoryException;
import seedu.address.model.inventory.exceptions.InventoryNotFoundException;
import seedu.address.testutil.InventoryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEGER_LARGE;

import static org.junit.jupiter.api.Assertions.*;

class InventoryListTest {

    public static final Inventory INVENTORY_A = InventoryBuilder.newInstance().setName(new Name("Inventory A"))
            .setIsDone(true)
            .setEventInstances(123)
            .build();
    public static final Inventory INVENTORY_B = InventoryBuilder.newInstance().setName(new Name("Inventory B"))
            .setIsDone(false)
            .setEventInstances(987)
            .build();

    @Test
    void contains_nullInventory_throwsNullPointerException() {
        InventoryList inventoryList = new InventoryList();
        assertThrows(NullPointerException.class, () -> inventoryList.contains(null));
    }

    @Test
    void contains_inventoryNotInList_returnsFalse() {
        InventoryList inventoryList = new InventoryList();
        assertFalse(inventoryList.contains(INVENTORY_A));
    }

    @Test
    void contains_inventoryInList_returnsTrue() {
        InventoryList inventoryList = new InventoryList();
        assertDoesNotThrow(() -> {
            inventoryList.add(INVENTORY_A);
            assertTrue(inventoryList.contains(INVENTORY_A));
        });
    }

    @Test
    void contains_inventoryWithSameIdentityFieldsInList_returnsTrue() {
        InventoryList inventoryList = new InventoryList();
        assertDoesNotThrow(() -> {
            inventoryList.add(INVENTORY_A);
            Inventory editedInventoryA = InventoryBuilder.of(INVENTORY_A).setEventInstances(VALID_INTEGER_LARGE)
                    .build();
            assertTrue(inventoryList.contains(editedInventoryA));
        });
    }

    @Test
    void add_nullInventory_throwsNullPointerException() {
        InventoryList inventoryList = new InventoryList();
        assertThrows(NullPointerException.class, () -> inventoryList.add(null));
    }

    @Test
    void add_duplicateInventory_throwsDuplicateInventoryException() {
        InventoryList inventoryList = new InventoryList();
        assertDoesNotThrow(() -> {
            inventoryList.add(INVENTORY_A);
            assertThrows(DuplicateInventoryException.class, () -> inventoryList.add(INVENTORY_A));
        });
    }

    @Test
    void addAll() {
    }

    @Test
    void addEventInventoryList() {
    }

    @Test
    void removeEventInventoryList() {
    }

    @Test
    public void remove_nullInventory_throwsNullPointerException() {
        InventoryList inventoryList = new InventoryList();
        assertThrows(NullPointerException.class, () -> inventoryList.remove((Inventory) null));
    }

    @Test
    public void remove_inventoryDoesNotExist_throwsInventoryNotFoundException() {
        InventoryList inventoryList = new InventoryList();
        assertThrows(InventoryNotFoundException.class, () -> inventoryList.remove(INVENTORY_A));
    }

    @Test
    public void remove_existingInventory_removesInventory() {
        InventoryList inventoryList = new InventoryList();
        assertDoesNotThrow(() -> {
            inventoryList.add(INVENTORY_A);
            inventoryList.remove(INVENTORY_A);
            InventoryList expectedUniqueInventoryList = new InventoryList();
            assertEquals(expectedUniqueInventoryList, inventoryList);
        });
    }

    @Test
    public void setInventories_uniqueInventoryList_replacesOwnListWithProvidedUniqueInventoryList() {
        InventoryList inventoryList = new InventoryList();
        assertDoesNotThrow(() -> {
            inventoryList.add(INVENTORY_A);
            List<Inventory> expectedUniqueInventoryList = new ArrayList<Inventory>();
            expectedUniqueInventoryList.add(INVENTORY_B);
            inventoryList.set(expectedUniqueInventoryList);
            assertEquals(expectedUniqueInventoryList, inventoryList.asUnmodifiableObservableList());
        });
    }
    //-------------------------------------------------------------------

    @Test
    public void setInventories_nullList_throwsNullPointerException() {
        InventoryList inventoryList = new InventoryList();
        assertThrows(NullPointerException.class, () -> inventoryList.set((List<Inventory>) null));
    }

    @Test
    public void setInventories_list_replacesOwnListWithProvidedList() {
        InventoryList inventoryList = new InventoryList();
        assertDoesNotThrow(() -> {
            inventoryList.add(INVENTORY_A);
            List<Inventory> inventorys = Collections.singletonList(INVENTORY_B);
            inventoryList.set(inventorys);
            InventoryList expectedUniqueInventoryList = new InventoryList();
            expectedUniqueInventoryList.add(INVENTORY_B);
            assertEquals(expectedUniqueInventoryList, inventoryList);
        });
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        InventoryList inventoryList = new InventoryList();
        assertThrows(UnsupportedOperationException.class, () ->
                inventoryList.asUnmodifiableObservableList().remove(0));
    }
}