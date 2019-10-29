package seedu.address.stubs;

import seedu.address.inventory.util.InventoryList;
import seedu.address.inventory.model.Item;

import java.util.ArrayList;

public class InventoryModelStubAcceptingItemAddedForInventoryUse extends InventoryModelStub {

    final ArrayList<Item> itemsAdded;

    public InventoryModelStubAcceptingItemAddedForInventoryUse() {
        itemsAdded = new ArrayList<>();
    }

    public InventoryModelStubAcceptingItemAddedForInventoryUse(ArrayList<Item> arr) {
        itemsAdded = arr;
    }

    @Override
    public void addItem(Item i) {
        itemsAdded.add(i);
    }

    @Override
    public void deleteItem(int index) {
        itemsAdded.remove(index - 1);
    }

    public ArrayList<Item> getItemsAdded() {
        return itemsAdded;
    }

    public InventoryList getInventoryList() {
        return new InventoryList(itemsAdded);
    }
}
