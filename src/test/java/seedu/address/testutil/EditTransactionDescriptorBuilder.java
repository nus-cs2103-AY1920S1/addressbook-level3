package seedu.address.testutil;

import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.model.Transaction;

public class EditTransactionDescriptorBuilder {
    private EditCommand.EditTransactionDescriptor descriptor;

    public EditTransactionDescriptorBuilder() {
        descriptor = new EditCommand.EditTransactionDescriptor();
    }

    public EditTransactionDescriptorBuilder(EditCommand.EditTransactionDescriptor descriptor) {
        this.descriptor = new EditCommand.EditTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditTransactionDescriptorBuilder(Transaction toCopy) {
        descriptor = new EditCommand.EditTransactionDescriptor();
        descriptor.setDate(toCopy.getDate());
        descriptor.setDescription(toCopy.getDescription());
        descriptor.setCategory(toCopy.getCategory());
        descriptor.setAmount(toCopy.getAmount());
        descriptor.setName(toCopy.getName());
        descriptor.setIsReimbursed(toCopy.getIsReimbursed());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDate(String date) {
        descriptor.setDate(date);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(category);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditTransactionDescriptorBuilder withAmount(Double amount) {
        descriptor.setAmount(amount);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditTransactionDescriptorBuilder withName(String person) {
        descriptor.setName(person);
        return this;
    }

    public EditCommand.EditTransactionDescriptor build() {
        return descriptor;
    }
}
