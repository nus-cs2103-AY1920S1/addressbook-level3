package seedu.ifridge.testutil;

import seedu.ifridge.logic.commands.shoppinglist.EditShoppingCommand;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.ShoppingItem;

/**
 * A utility class to help with building EditShoppingItemDescriptor objects.
 */
public class EditShoppingItemDescriptorBuilder {

    private EditShoppingCommand.EditShoppingItemDescriptor descriptor;

    public EditShoppingItemDescriptorBuilder() {
        descriptor = new EditShoppingCommand.EditShoppingItemDescriptor();
    }

    public EditShoppingItemDescriptorBuilder(EditShoppingCommand.EditShoppingItemDescriptor descriptor) {
        this.descriptor = new EditShoppingCommand.EditShoppingItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditShoppingItemDescriptor} with fields containing {@code ShoppingItem}'s details
     */
    public EditShoppingItemDescriptorBuilder(ShoppingItem shoppingItem) {
        descriptor = new EditShoppingCommand.EditShoppingItemDescriptor();
        descriptor.setName(shoppingItem.getName());
        descriptor.setAmount(shoppingItem.getAmount());
    }

    /**
     * Sets the {@code Amount} of the {@code EditShoppingItemDescriptor} that we are building.
     */
    public EditShoppingItemDescriptorBuilder withAmount(String name) {
        descriptor.setAmount(new Amount(name));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditShoppingItemDescriptor} that we are building.
     */
    public EditShoppingItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditShoppingCommand.EditShoppingItemDescriptor build() {
        return descriptor;
    }
}
