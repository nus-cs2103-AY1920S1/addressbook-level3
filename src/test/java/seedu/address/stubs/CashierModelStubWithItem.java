package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.inventory.model.Item;

public class CashierModelStubWithItem extends CashierModelStub {
    private Item item;

    public CashierModelStubWithItem(Item item) {
        requireNonNull(item);
        this.item = item;
    }

    @Override
    public boolean hasItemInInventory(Item i) {
        requireNonNull(i);
        return this.item.equals(i);
    }

    @Override
    public void clearSalesList() {
        item = null;
    }

}


