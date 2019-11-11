package seedu.address.testutil;

import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.commons.Amount;
import seedu.address.model.commons.Currency;
import seedu.address.model.commons.Date;
import seedu.address.model.commons.Name;
import seedu.address.model.commons.Tag;
import seedu.address.model.expense.Expense;

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
        descriptor.setName(expense.getName());
        descriptor.setAmount(expense.getAmount());
        descriptor.setCurrency(expense.getCurrency());
        descriptor.setDate(expense.getDate());
        descriptor.setTag(expense.getTag());
    }

    /**
     * Sets the {@code Name} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Currency} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withCurrency(String currency) {
        descriptor.setCurrency(new Currency(currency));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditExpenseDescriptorBuilder withTag(String tag) {
        descriptor.setTag(new Tag(tag));
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}
