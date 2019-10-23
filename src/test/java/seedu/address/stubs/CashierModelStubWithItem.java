package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.inventory.model.Item;
import seedu.address.person.model.person.Person;

/**
 * Represents a Cashier tab's Model stub.
 */
public class CashierModelStubWithItem extends CashierModelStub {
    private Person cashier;
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

    @Override
    public void resetCashier() {
        this.cashier = null;
    }

}


