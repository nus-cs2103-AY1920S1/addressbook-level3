package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.inventory.model.Item;

public class CashierModelStubAcceptingItemAdded extends CashierModelStub {

    final ArrayList<Item> itemsAdded;

    public CashierModelStubAcceptingItemAdded() {
        itemsAdded = new ArrayList<>();
    }

    public CashierModelStubAcceptingItemAdded(ArrayList<Item> arr) {
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


