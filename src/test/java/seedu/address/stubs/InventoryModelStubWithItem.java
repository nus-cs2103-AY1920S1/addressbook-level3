package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.inventory.model.Item;

/**
 * Represents a Inventory tab's Model stub.
 */
public class InventoryModelStubWithItem extends InventoryModelStub {
    private Item item;

    public InventoryModelStubWithItem(Item item) {
        requireNonNull(item);
        this.item = item;
    }

}

