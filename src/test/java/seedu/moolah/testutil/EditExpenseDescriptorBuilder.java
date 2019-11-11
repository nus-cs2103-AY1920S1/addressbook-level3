package seedu.moolah.testutil;

import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.general.Category;
import seedu.moolah.model.general.Description;
import seedu.moolah.model.general.Price;
import seedu.moolah.model.general.Timestamp;


/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditExpenseDescriptorBuilder {

    private EditExpenseCommand.EditExpenseDescriptor descriptor;

    public EditExpenseDescriptorBuilder() {
        descriptor = new EditExpenseCommand.EditExpenseDescriptor();
    }

    public EditExpenseDescriptorBuilder(EditExpenseCommand.EditExpenseDescriptor descriptor) {
        this.descriptor = new EditExpenseCommand.EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditExpenseDescriptorBuilder(Expense expense) {
        descriptor = new EditExpenseCommand.EditExpenseDescriptor();
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

    public EditExpenseCommand.EditExpenseDescriptor build() {
        return descriptor;
    }
}
