package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.UpdateTransactionDescriptor;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Description;
import seedu.address.model.util.Date;

/**
 * A utility class to help with building UpdateTransactionDescriptor objects.
 */
public class UpdateTransactionDescriptorBuilder {

    private UpdateTransactionDescriptor descriptor;

    public UpdateTransactionDescriptorBuilder() {
        descriptor = new UpdateTransactionDescriptor();
    }

    public UpdateTransactionDescriptorBuilder(UpdateTransactionDescriptor descriptor) {
        this.descriptor = new UpdateTransactionDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateTransactionDescriptor} with fields containing
     * {@code BankAccountOperation}'s details
     */
    public UpdateTransactionDescriptorBuilder(BankAccountOperation transaction) {
        descriptor = new UpdateTransactionDescriptor();
        descriptor.setAmount(transaction.getAmount());
        descriptor.setDescription(transaction.getDescription());
        descriptor.setDate(transaction.getDate());
        descriptor.setCategories(transaction.getCategories());
    }

    /**
     * Sets the {@code Amount} of the {@code UpdateTransactionDescriptor} that we are building.
     */
    public UpdateTransactionDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(Double.parseDouble(amount)));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code UpdateTransactionDescriptor} that we are building.
     */
    public UpdateTransactionDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code UpdateTransactionDescriptor} that we are building.
     */
    public UpdateTransactionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code UpdateTransactionDescriptor}
     * that we are building.
     */
    public UpdateTransactionDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public UpdateTransactionDescriptor build() {
        return descriptor;
    }

}
