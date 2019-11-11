package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Name;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Inventory()} without optional fields.
 */
public class InventoryBuilder {
    private Name name;
    private boolean isDone;
    private int eventInstances;

    private InventoryBuilder() {}

    public static InventoryBuilder newInstance() {
        return new InventoryBuilder();
    }

    /**
     * Constructs a InventoryBuilder instance from the specified Inventory().
     *
     * @param inventory to use.
     * @return new InventoryBuilder instance.
     */
    public static InventoryBuilder of(Inventory inventory) {
        requireAllNonNull(inventory.getName());

        return InventoryBuilder.newInstance()
                .setName(inventory.getName())
                .setIsDone(inventory.getIsDone())
                .setEventInstances(inventory.getEventInstances());
    }

    public InventoryBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public InventoryBuilder setIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    public InventoryBuilder setEventInstances(int eventInstances) {
        this.eventInstances = eventInstances;
        return this;
    }

    /**
     * Terminal method to construct new {@link Inventory()}.
     */
    public Inventory build() {
        return new Inventory(name, isDone, eventInstances);
    }

}
