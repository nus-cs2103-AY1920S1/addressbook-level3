package seedu.planner.testutil.accommodation;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.planner.logic.commands.editcommand.EditAccommodationCommand.EditAccommodationDescriptor;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;

/**
 * A utility class to help with building EditContactDescriptor objects.
 */
public class EditAccommodationDescriptorBuilder {

    private EditAccommodationDescriptor descriptor;

    public EditAccommodationDescriptorBuilder() {
        descriptor = new EditAccommodationDescriptor();
    }

    public EditAccommodationDescriptorBuilder(EditAccommodationDescriptor descriptor) {
        this.descriptor = new EditAccommodationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditContactDescriptor} with fields containing {@code contacts}'s details
     */
    public EditAccommodationDescriptorBuilder(Accommodation accommodation) {
        descriptor = new EditAccommodationDescriptor();
        descriptor.setName(accommodation.getName());
        accommodation.getContact().ifPresent(contact -> descriptor.setPhone(contact.getPhone()));
        descriptor.setAddress(accommodation.getAddress());
        descriptor.setTags(accommodation.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditContactDescriptor} that we are building.
     */
    public EditAccommodationDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditContactDescriptor} that we are building.
     */
    public EditAccommodationDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditContactDescriptor} that we are building.
     */
    public EditAccommodationDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditContactDescriptor}
     * that we are building.
     */
    public EditAccommodationDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditAccommodationDescriptor build() {
        return descriptor;
    }
}
