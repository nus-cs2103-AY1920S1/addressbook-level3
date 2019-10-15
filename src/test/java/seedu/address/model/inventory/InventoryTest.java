package seedu.address.model.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_NAME_SPORTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INVENTORY_PRICE_MUSIC;
import static seedu.address.testutil.TypicalInventories.POSTERS;
import static seedu.address.testutil.TypicalInventories.SHIRTS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.InventoryBuilder;

public class InventoryTest {

    @Test
    public void isSameInventory() {
        // same object -> returns true
        assertTrue(SHIRTS.isSameInventory(SHIRTS));

        // null -> returns false
        assertFalse(SHIRTS.isSameInventory(null));

        Inventory editedShirtInventory = new InventoryBuilder(SHIRTS).build();

        // different name -> returns false
        editedShirtInventory = new InventoryBuilder(SHIRTS)
                .withName(VALID_INVENTORY_NAME_SPORTS).build();
        assertFalse(SHIRTS.isSameInventory(editedShirtInventory));

        // same name, different attributes -> returns true
        editedShirtInventory = new InventoryBuilder(SHIRTS)
                .withPrice(new Price(VALID_INVENTORY_PRICE_MUSIC))
                .build();
        assertTrue(SHIRTS.isSameInventory(editedShirtInventory));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Inventory orderShirtsCopy = new InventoryBuilder(SHIRTS).build();
        assertTrue(SHIRTS.equals(orderShirtsCopy));

        // same object -> returns true
        assertTrue(SHIRTS.equals(SHIRTS));

        // null -> returns false
        assertFalse(SHIRTS == null);

        // different type -> returns false
        assertFalse(SHIRTS.equals(5));

        // different inventory -> returns false
        assertFalse(SHIRTS.equals(POSTERS));

        // different name -> returns false
        Inventory editedShirtInventory = new InventoryBuilder(SHIRTS).withName(VALID_INVENTORY_NAME_SPORTS).build();
        assertFalse(SHIRTS.equals(editedShirtInventory));

        // different price -> returns false
        editedShirtInventory = new InventoryBuilder(SHIRTS).withPrice(new Price(VALID_INVENTORY_PRICE_MUSIC)).build();
        assertFalse(SHIRTS.equals(editedShirtInventory));

    }
}
