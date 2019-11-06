package seedu.address.testutil;

import seedu.address.inventory.logic.commands.EditCommand;
import seedu.address.inventory.model.Item;

/**
 * Builds an edit item descriptor.
 */
public class EditItemDescriptorBuilder {
    private EditCommand.EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new EditCommand.EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(EditCommand.EditItemDescriptor descriptor) {
        this.descriptor = new EditCommand.EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditItemDescriptorBuilder} with fields containing {@code Item}'s details
     */
    public EditItemDescriptorBuilder(Item toCopy) {
        descriptor = new EditCommand.EditItemDescriptor();
        descriptor.setDescription(toCopy.getDescription());
        descriptor.setCategory(toCopy.getCategory());
        descriptor.setQuantity(toCopy.getQuantity());
        descriptor.setCost(toCopy.getCost());
        descriptor.setPrice(toCopy.getPrice());
    }

    /**
     * Sets the description of the {@code EditItemDescriptorBuilder} that we are building.
     */
    public EditItemDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the category of the {@code EditItemDescriptorBuilder} that we are building.
     */
    public EditItemDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(category);
        return this;
    }

    /**
     * Sets the quantity of the {@code EditItemDescriptorBuilder} that we are building.
     */
    public EditItemDescriptorBuilder withQuantity(Integer quantity) {
        descriptor.setQuantity(quantity);
        return this;
    }

    /**
     * Set the cost to the {@code EditItemDescriptorBuilder} that we are building.
     */
    public EditItemDescriptorBuilder withCost(Double cost) {
        descriptor.setCost(cost);
        return this;
    }

    /**
     * Set the price to the {@code EditItemDescriptorBuilder} that we are building.
     */
    public EditItemDescriptorBuilder withPrice(Double price) {
        descriptor.setPrice(price);
        return this;
    }

    public EditCommand.EditItemDescriptor build() {
        return descriptor;
    }
}

