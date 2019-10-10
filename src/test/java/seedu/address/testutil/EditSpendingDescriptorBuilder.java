package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.address.model.spending.Address;
import seedu.address.model.spending.Email;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Phone;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditSpendingDescriptorBuilder {

    private EditSpendingDescriptor descriptor;

    public EditSpendingDescriptorBuilder() {
        descriptor = new EditSpendingDescriptor();
    }

    public EditSpendingDescriptorBuilder(EditSpendingDescriptor descriptor) {
        this.descriptor = new EditSpendingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code Spending}'s details
     */
    public EditSpendingDescriptorBuilder(Spending spending) {
        descriptor = new EditSpendingDescriptor();
        descriptor.setName(spending.getName());
        descriptor.setPhone(spending.getPhone());
        descriptor.setEmail(spending.getEmail());
        descriptor.setAddress(spending.getAddress());
        descriptor.setTags(spending.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditSpendingDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditSpendingDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditSpendingDescriptor build() {
        return descriptor;
    }
}
