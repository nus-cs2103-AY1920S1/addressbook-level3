package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;

/**
 * Represents a Cashier tab's model stub.
 */
public class CashierModelStubAcceptingItemAdded extends CashierModelStub {

    private final ArrayList<Item> itemsAdded;
    private InventoryModelStubAcceptingItemAdded inventoryModelStub = new InventoryModelStubAcceptingItemAdded();

    public CashierModelStubAcceptingItemAdded() {
        itemsAdded = new ArrayList<>();
    }

    public CashierModelStubAcceptingItemAdded(ArrayList<Item> arr) {
        itemsAdded = arr;
    }

    public void setInventoryModelStub(InventoryModelStubAcceptingItemAdded inventoryModelStub) {
        this.inventoryModelStub = inventoryModelStub;
        //inventoryModelStub.addItem(TypicalItem.FISH_BURGER);
    }

    @Override
    public void deleteItem(int index) {
        itemsAdded.remove(index - 1);
    }

    public ArrayList<Item> getItemsAdded() {
        return itemsAdded;
    }

    @Override
    public void addItem(Item i) {
        itemsAdded.add(i);
    }

    @Override
    public Item addItem(String description, int qty) throws NoSuchItemException {
        for (Item item : itemsAdded) {
            if (item.getDescription().equalsIgnoreCase(description)) {
                int originalQty = item.getQuantity();
                item.setQuantity(originalQty + qty);
                return item;
            }
        }
        Item i = inventoryModelStub.getOriginalItem(description);
        Item copy = new Item(i.getDescription(), i.getCategory(), qty, i.getCost(), i.getPrice(),
                Integer.valueOf(i.getId()));
        copy.setQuantity(qty);
        itemsAdded.add(copy);
        return i;
    }

    @Override
    public boolean hasSufficientQuantityToAdd(String description, int quantity) throws NoSuchItemException {
        Item originalItem = inventoryModelStub.getOriginalItem(description);
        for (Item i : itemsAdded) {
            if (originalItem.isSameItem(i)) {
                int initialSalesQty = i.getQuantity();
                return (originalItem.getQuantity() >= (initialSalesQty + quantity));
            }
        }
        if (originalItem.getQuantity() >= quantity) {
            return true;
        }
        return false;
    }

    @Override
    public int getStockLeft(String description) throws NoSuchItemException {
        return inventoryModelStub.getOriginalItem(description).getQuantity();
    }

    @Override
    public ArrayList<Item> getSalesList() {
        return this.itemsAdded;
    }

    @Override
    public InventoryList getInventoryList() {
        return new InventoryList(this.inventoryModelStub.getItemsAdded());
    }

    @Override
    public boolean isSellable(String description) throws NoSuchItemException {
        Item i = getInventoryList().getOriginalItem(description);
        return i.isSellable();
    }
}


