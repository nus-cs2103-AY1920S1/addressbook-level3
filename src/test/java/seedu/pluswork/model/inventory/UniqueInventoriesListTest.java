package seedu.pluswork.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_INVENTORY_PRICE_MUSIC;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalInventories.BALLS;
import static seedu.pluswork.testutil.TypicalInventories.SHIRTS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pluswork.model.inventory.exceptions.DuplicateInventoryException;
import seedu.pluswork.model.inventory.exceptions.InventoryNotFoundException;
import seedu.pluswork.testutil.InventoryBuilder;

public class UniqueInventoriesListTest {
    private final UniqueInventoryList uniqueInventoryList = new UniqueInventoryList();

    @Test
    public void contains_nullInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.contains(null));
    }

    @Test
    public void contains_inventoryNotInList_returnsFalse() {
        assertFalse(uniqueInventoryList.contains(SHIRTS));
    }

    @Test
    public void contains_inventoryIList_returnsTrue() {
        uniqueInventoryList.add(SHIRTS);
        assertTrue(uniqueInventoryList.contains(SHIRTS));
    }

    @Test
    public void add_nullInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.add(null));
    }

    @Test
    public void add_duplicateInventory_throwsDuplicateInventoryException() {
        uniqueInventoryList.add(SHIRTS);
        assertThrows(DuplicateInventoryException.class, () -> uniqueInventoryList.add(SHIRTS));
    }

    @Test
    public void setInventory_nullTargetInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.setInventory(null, SHIRTS));
    }

    @Test
    public void setInventory_nullEditedInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.setInventory(SHIRTS, null));
    }

    @Test
    public void setInventory_targetInventoryNotInList_throwsInventoryNotFoundException() {
        assertThrows(InventoryNotFoundException.class, () -> uniqueInventoryList.setInventory(SHIRTS, SHIRTS));
    }

    @Test
    public void setInventory_editedInventoryIsSameInventory_success() {
        uniqueInventoryList.add(SHIRTS);
        uniqueInventoryList.setInventory(SHIRTS, SHIRTS);
        UniqueInventoryList expectedUniqueInventoryList = new UniqueInventoryList();
        expectedUniqueInventoryList.add(SHIRTS);
        assertEquals(expectedUniqueInventoryList, uniqueInventoryList);
    }

    @Test
    public void setInventory_editedInventoryHasSameIdentity_success() {
        uniqueInventoryList.add(SHIRTS);
        Inventory editedAlice = new InventoryBuilder(SHIRTS).withPrice(new Price(VALID_INVENTORY_PRICE_MUSIC))
                .build();
        uniqueInventoryList.setInventory(SHIRTS, editedAlice);
        UniqueInventoryList expectedUniqueInventoryList = new UniqueInventoryList();
        expectedUniqueInventoryList.add(editedAlice);
        assertEquals(expectedUniqueInventoryList, uniqueInventoryList);
    }

    @Test
    public void setInventory_editedInventoryHasDifferentIdentity_success() {
        uniqueInventoryList.add(SHIRTS);
        uniqueInventoryList.setInventory(SHIRTS, BALLS);
        UniqueInventoryList expectedUniqueInventoryList = new UniqueInventoryList();
        expectedUniqueInventoryList.add(BALLS);
        assertEquals(expectedUniqueInventoryList, uniqueInventoryList);
    }

    @Test
    public void setInventory_editedInventoryHasNonUniqueIdentity_throwsDuplicateInventoryException() {
        uniqueInventoryList.add(SHIRTS);
        uniqueInventoryList.add(BALLS);
        assertThrows(DuplicateInventoryException.class, ()
            -> uniqueInventoryList.setInventory(SHIRTS, BALLS));
    }

    @Test
    public void remove_nullInventory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.remove(null));
    }

    @Test
    public void remove_inventoryDoesNotExist_throwsInventoryNotFoundException() {
        assertThrows(InventoryNotFoundException.class, () -> uniqueInventoryList.remove(SHIRTS));
    }

    @Test
    public void remove_existingInventory_removesInventory() {
        uniqueInventoryList.add(SHIRTS);
        uniqueInventoryList.remove(SHIRTS);
        UniqueInventoryList expectedUniqueTaskList = new UniqueInventoryList();
        assertEquals(expectedUniqueTaskList, uniqueInventoryList);
    }

    @Test
    public void setInventories_nullUniqueInventoriesList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.setInventories((UniqueInventoryList) null));
    }

    @Test
    public void setInventories_uniqueInventoryList_replacesOwnListWithProvidedUniqueInventoriesList() {
        uniqueInventoryList.add(SHIRTS);
        UniqueInventoryList expectedUniqueInventoryList = new UniqueInventoryList();
        expectedUniqueInventoryList.add(BALLS);
        uniqueInventoryList.setInventories(expectedUniqueInventoryList);
        assertEquals(expectedUniqueInventoryList, uniqueInventoryList);
    }

    @Test
    public void setInventories_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueInventoryList.setInventories((List<Inventory>) null));
    }

    @Test
    public void setInventories_list_replacesOwnListWithProvidedList() {
        uniqueInventoryList.add(SHIRTS);
        List<Inventory> inventoryList = Collections.singletonList(BALLS);
        uniqueInventoryList.setInventories(inventoryList);
        UniqueInventoryList expectedUniqueInventoryList = new UniqueInventoryList();
        expectedUniqueInventoryList.add(BALLS);
        assertEquals(expectedUniqueInventoryList, uniqueInventoryList);
    }

    @Test
    public void setInventories_listWithDuplicateInventories_throwsDuplicateInventoriesException() {
        List<Inventory> listWithDuplicateTasks = Arrays.asList(SHIRTS, SHIRTS);
        assertThrows(DuplicateInventoryException.class, () -> uniqueInventoryList
                .setInventories(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueInventoryList.asUnmodifiableList().remove(0));
    }
}
