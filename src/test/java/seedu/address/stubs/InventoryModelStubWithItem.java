package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.inventory.model.Item;

public class InventoryModelStubWithItem extends InventoryModelStub {
    private Item item;

    public InventoryModelStubWithItem(Item item) {
        requireNonNull(item);
        this.item = item;
    }

    @Override
    public boolean hasItemInInventory(Item i) {
        requireNonNull(i);
        return this.item.equals(i);
    }


}