package seedu.address.stubs;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.Model;
import seedu.address.inventory.model.exception.NoSuchIndexException;

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
    public void deleteItem(int index) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void writeInInventoryFile() throws Exception {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setItem(int i, Item editedItem) throws Exception {
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
}

