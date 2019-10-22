package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditExpenseDescriptor;

import seedu.address.model.category.Category;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;


/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditExpenseDescriptorBuilder {

    private EditExpenseDescriptor descriptor;

    public EditExpenseDescriptorBuilder() {
        descriptor = new EditExpenseDescriptor();
    }

    public EditExpenseDescriptorBuilder(EditExpenseDescriptor descriptor) {
        this.descriptor = new EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditExpenseDescriptorBuilder(Expense expense) {
        descriptor = new EditExpenseDescriptor();
        descriptor.setDescription(expense.getDescription());
        descriptor.setPrice(expense.getPrice());
        descriptor.setCategory(expense.getCategory());
        descriptor.setTimestamp(expense.getTimestamp());
    }

    /**
     * Sets the {@code Description} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withTimestamp(String rawTimestamp) {
        descriptor.setTimestamp(Timestamp.createTimestampIfValid(rawTimestamp).get());
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}
