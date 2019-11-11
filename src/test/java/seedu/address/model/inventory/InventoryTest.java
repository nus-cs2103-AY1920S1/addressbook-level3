package seedu.address.model.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.model.ModelTestUtil.VALID_INTEGER_LARGE;
import static seedu.address.model.ModelTestUtil.VALID_NAME_AFRICA;
import static seedu.address.model.ModelTestUtil.VALID_NAME_BALI;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InventoryBuilder;

class InventoryTest {

    public static final Inventory INVENTORY_A = InventoryBuilder.newInstance().setName(new Name("Inventory A"))
            .setIsDone(true)
            .setEventInstances(123)
            .build();
    public static final Inventory INVENTORY_B = InventoryBuilder.newInstance().setName(new Name("Inventory B"))
            .setIsDone(false)
            .setEventInstances(987)
            .build();

    @Test
    void isSameInventory() {
        // same object -> returns true
        assertTrue(INVENTORY_A.isSameInventory(INVENTORY_A));

        // null -> returns false
        assertFalse(INVENTORY_A.isSameInventory(null));

        // different name -> returns false
        Inventory editedInventoryA = InventoryBuilder.of(INVENTORY_A)
                .setName(new Name(VALID_NAME_AFRICA)).build();
        assertFalse(INVENTORY_A.isSameInventory(editedInventoryA));

        // same name, same isDone, different eventInstances -> returns true
        editedInventoryA = InventoryBuilder.of(INVENTORY_A).setEventInstances(VALID_INTEGER_LARGE)
                .build();
        assertTrue(INVENTORY_A.isSameInventory(editedInventoryA));

        // same name, different isDone, same eventInstance -> returns true
        editedInventoryA = InventoryBuilder.of(INVENTORY_A).setIsDone(!INVENTORY_A.getIsDone()).build();
        assertTrue(INVENTORY_A.isSameInventory(editedInventoryA));

        // same name, different isDone, different eventInstance -> returns true
        editedInventoryA = InventoryBuilder.of(INVENTORY_A).setIsDone(!INVENTORY_A.getIsDone())
                .setEventInstances(VALID_INTEGER_LARGE).build();
        assertTrue(INVENTORY_A.isSameInventory(editedInventoryA));

        // same name, same isDone, same eventInstance -> returns true
        editedInventoryA = InventoryBuilder.of(INVENTORY_A).build();
        assertTrue(INVENTORY_A.isSameInventory(editedInventoryA));


    }

    @Test
    void testEquals() {

        // same values -> returns true
        Inventory inventoryACopy = InventoryBuilder.of(INVENTORY_A).build();
        assertTrue(INVENTORY_A.equals(inventoryACopy));

        // same object -> returns true
        assertTrue(INVENTORY_A.equals(INVENTORY_A));

        // null -> returns false
        assertFalse(INVENTORY_A.equals(null));

        // different type -> returns false
        assertFalse(INVENTORY_A.equals(5));

        // different Inventory -> returns false
        assertFalse(INVENTORY_A.equals(INVENTORY_B));

        // different name -> returns false
        Inventory editedInventoryA = InventoryBuilder.of(INVENTORY_A)
                .setName(new Name(VALID_NAME_BALI)).build();
        assertFalse(INVENTORY_A.equals(editedInventoryA));

        // different isDone -> returns true
        editedInventoryA = InventoryBuilder.of(INVENTORY_A).setIsDone(!INVENTORY_A.getIsDone()).build();
        assertTrue(INVENTORY_A.equals(editedInventoryA));

        // different eventInstances -> returns true
        editedInventoryA = InventoryBuilder.of(INVENTORY_A)
                .setEventInstances(VALID_INTEGER_LARGE).build();
        assertTrue(INVENTORY_A.equals(editedInventoryA));
    }
}
