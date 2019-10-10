package seedu.billboard.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.billboard.logic.commands.EditCommand;
import seedu.billboard.logic.commands.EditCommand.EditExpenseDescriptor;
import seedu.billboard.model.person.*;
import seedu.billboard.model.person.Expense;
import seedu.billboard.model.tag.Tag;

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
//        descriptor.setName(expense.getName());
//        descriptor.setPhone(expense.getPhone());
//        descriptor.setEmail(expense.getEmail());
//        descriptor.setAddress(expense.getAddress());
        descriptor.setDescription(expense.getDescription());
        descriptor.setAmount(expense.getAmount());
        descriptor.setTags(expense.getTags());
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

//    /**
//     * Sets the {@code Name} of the {@code EditExpenseDescriptor} that we are building.
//     */
//    public EditExpenseDescriptorBuilder withName(String name) {
//        descriptor.setName(new Name(name));
//        return this;
//    }
//
//    /**
//     * Sets the {@code Phone} of the {@code EditExpenseDescriptor} that we are building.
//     */
//    public EditExpenseDescriptorBuilder withPhone(String phone) {
//        descriptor.setPhone(new Phone(phone));
//        return this;
//    }
//
//    /**
//     * Sets the {@code Email} of the {@code EditExpenseDescriptor} that we are building.
//     */
//    public EditExpenseDescriptorBuilder withEmail(String email) {
//        descriptor.setEmail(new Email(email));
//        return this;
//    }
//
//    /**
//     * Sets the {@code Address} of the {@code EditExpenseDescriptor} that we are building.
//     */
//    public EditExpenseDescriptorBuilder withAddress(String address) {
//        descriptor.setAddress(new Address(address));
//        return this;
//    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditExpenseDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}
