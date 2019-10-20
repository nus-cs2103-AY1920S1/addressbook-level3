package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.inventory.model.Item;

public class InventoryModelStubAcceptingItemAdded extends InventoryModelStub {

    final ArrayList<Item> itemsAdded;

    public InventoryModelStubAcceptingItemAdded() {
        itemsAdded = new ArrayList<>();
    }

    public InventoryModelStubAcceptingItemAdded(ArrayList<Item> arr) {
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

}
