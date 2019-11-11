package seedu.billboard.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.billboard.logic.commands.EditCommand;
import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.Name;

/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditExpenseDescriptorBuilder {

    private EditCommand.EditExpenseDescriptor descriptor;

    public EditExpenseDescriptorBuilder() {
        descriptor = new EditExpenseDescriptor();
    }

    public EditExpenseDescriptorBuilder(EditCommand.EditExpenseDescriptor descriptor) {
        this.descriptor = new EditCommand.EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditExpenseDescriptorBuilder(Expense expense) {
        descriptor = new EditCommand.EditExpenseDescriptor();
        descriptor.setName(expense.getName());
        descriptor.setDescription(expense.getDescription());
        descriptor.setAmount(expense.getAmount());
        descriptor.setCreated(expense.getCreated());
        descriptor.setTags(expense.getTags().stream().map(s -> s.tagName).collect(Collectors.toList()));
    }

    /**
     * Sets the {@code Name} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withCreatedDateTime(String dateTime) {
        descriptor.setCreated(new CreatedDateTime(dateTime));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditExpenseDescriptorBuilder withTags(String... tags) {
        List<String> tagSet = Stream.of(tags).collect(Collectors.toList());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}
