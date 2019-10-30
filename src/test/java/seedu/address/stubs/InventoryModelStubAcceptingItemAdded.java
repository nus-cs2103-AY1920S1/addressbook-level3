package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierMessages;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.util.InventoryList;

/**
 * Represents a Cashier tab's Model stub.
 */
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

    public Item getOriginalItem(String description) throws NoSuchItemException {
        for (int i = 0; i < itemsAdded.size(); i++) {
            if (itemsAdded.get(i).getDescription().equalsIgnoreCase(description)) {
                return itemsAdded.get(i);
            }
        }
        throw new NoSuchItemException(CashierMessages.NO_SUCH_ITEM_CASHIER);
    }

    public InventoryList getInventoryList() {
        return new InventoryList(itemsAdded);
    }
}


