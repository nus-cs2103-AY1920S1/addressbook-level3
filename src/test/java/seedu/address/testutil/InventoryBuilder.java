package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.model.util.SampleTaskDataUtil;

public class InventoryBuilder {
    public static final String DEFAULT_NAME = "Sample Inventory Name";

    private InvName name;
    private Price price;

    public InventoryBuilder() {
        name = new InvName(DEFAULT_NAME);
        price = new Price(0);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public InventoryBuilder(Inventory inventoryToCopy) {
        name = inventoryToCopy.getName();
        price = new Price(inventoryToCopy.getPrice().getPrice());
    }

    /**
     * Sets the {@code Name} of the {@code Inventory} that we are building.
     */
    public InventoryBuilder withName(String name) {
        this.name = new InvName(name);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Inventory} that we are building.
     */
    public InventoryBuilder withPrice(Price price) {
        this.price = new Price(price.getPrice());
        return this;
    }


    public Inventory build() {
        return new Inventory(name, price);
    }

}
