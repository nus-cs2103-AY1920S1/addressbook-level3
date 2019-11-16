package seedu.ifridge.testutil;

import java.util.Set;

import seedu.ifridge.logic.commands.grocerylist.EditGroceryCommand;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.ExpiryDate;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.tag.Tag;
import seedu.ifridge.model.util.SampleDataUtil;

/**
 * A utility class to help with building EditGroceryItemDescriptor objects.
 */
public class EditGroceryItemDescriptorBuilder {

    private EditGroceryCommand.EditGroceryItemDescriptor descriptor;

    public EditGroceryItemDescriptorBuilder() {
        descriptor = new EditGroceryCommand.EditGroceryItemDescriptor();
    }

    public EditGroceryItemDescriptorBuilder(EditGroceryCommand.EditGroceryItemDescriptor descriptor) {
        this.descriptor = new EditGroceryCommand.EditGroceryItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGroceryItemDescriptor} with fields containing {@code GroceryItem}'s details
     */
    public EditGroceryItemDescriptorBuilder(GroceryItem groceryItem) {
        descriptor = new EditGroceryCommand.EditGroceryItemDescriptor();
        descriptor.setName(groceryItem.getName());
        descriptor.setAmount(groceryItem.getAmount());
        descriptor.setExpiryDate(groceryItem.getExpiryDate());
        descriptor.setTags(groceryItem.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditGroceryItemDescriptor} that we are building.
     */
    public EditGroceryItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditGroceryItemDescriptor} that we are building.
     */
    public EditGroceryItemDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditGroceryItemDescriptor} that we are building.
     */
    public EditGroceryItemDescriptorBuilder withExpiryDate(String expiryDate) {
        descriptor.setExpiryDate(new ExpiryDate(expiryDate));
        return this;
    }

    /**
     * Sets the {@code Set<Tag>} of the {@code EditGroceryItemDescriptor} that we are building.
     */
    public EditGroceryItemDescriptorBuilder withTags(String ... tags) {
        Set<Tag> tagSet = SampleDataUtil.getTagSet(tags);
        descriptor.setTags(tagSet);
        return this;
    }

    public EditGroceryCommand.EditGroceryItemDescriptor build() {
        return descriptor;
    }
}
