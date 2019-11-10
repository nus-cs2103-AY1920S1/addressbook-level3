package seedu.pluswork.testutil;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.pluswork.logic.commands.EditInventoryCommand;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

public class EditInventoryDescriptorBuilder {
    private EditInventoryCommand.EditInventoryDescriptor descriptor;

    public EditInventoryDescriptorBuilder() {
        descriptor = new EditInventoryCommand.EditInventoryDescriptor();
    }

    public EditInventoryDescriptorBuilder(EditInventoryCommand.EditInventoryDescriptor descriptor) {
        this.descriptor = new EditInventoryCommand.EditInventoryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditInventoryDescriptor} with fields containing {@code inv}'s details
     */
    public EditInventoryDescriptorBuilder(Inventory inv) {
        requireNonNull(inv);
        descriptor = new EditInventoryCommand.EditInventoryDescriptor();
        descriptor.setName(inv.getName());
        descriptor.setPrice(inv.getPrice());
    }

    /**
     * Sets the {@code Name} of the {@code EditInventoryDescriptor} that we are building.
     */
    public EditInventoryDescriptorBuilder withName(String name) {
        descriptor.setName(new InvName(name));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditInventoryDescriptor} that we are building.
     */
    public EditInventoryDescriptorBuilder withPrice(Price price) {
        descriptor.setPrice(new Price(price.getPrice()));
        return this;
    }


    public EditInventoryCommand.EditInventoryDescriptor build() {
        return descriptor;
    }
}
