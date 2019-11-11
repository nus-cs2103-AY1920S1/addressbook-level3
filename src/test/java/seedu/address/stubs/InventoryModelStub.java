package seedu.address.stubs;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.inventory.model.exception.NoSuchItemException;
import seedu.address.inventory.util.InventoryList;

/**
 * Represents a Inventory tab's Model stub.
 */
public class InventoryModelStub implements Model {

    @Override
    public void addItem(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Item findItemByIndex(int index) throws NoSuchIndexException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int findIndexByDescription(String description) throws NoSuchItemException, NoSuchIndexException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteItem(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void writeInInventoryFile() throws IOException, NoSuchIndexException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setItem(int i, Item editedItem) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasItemInInventory(Item item) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void readInUpdatedList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Item> getInventoryListInArrayList() {
        throw new AssertionError("This method should not be called.");
    }

    public void sortByQuantity() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public InventoryList getInventoryList() {
        throw new AssertionError("This method should not be called.");
    }

    public void sortByDescription() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortByCategory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortReset() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateIndexes() {
        throw new AssertionError("This method should not be called.");
    }

}

