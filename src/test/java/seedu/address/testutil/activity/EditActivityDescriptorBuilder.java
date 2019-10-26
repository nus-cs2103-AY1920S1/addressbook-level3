package seedu.address.testutil.activity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditActivityCommand.EditActivityDescriptor;
import seedu.address.model.contact.Phone;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditActivityDescriptor objects.
 */
public class EditActivityDescriptorBuilder {

    private EditActivityDescriptor descriptor;

    public EditActivityDescriptorBuilder() {
        descriptor = new EditActivityDescriptor();
    }

    public EditActivityDescriptorBuilder(EditActivityDescriptor descriptor) {
        this.descriptor = new EditActivityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditActivityDescriptor} with fields containing {@code activities}'s details
     */
    public EditActivityDescriptorBuilder(Activity activity) {
        descriptor = new EditActivityDescriptor();
        descriptor.setName(activity.getName());
        descriptor.setAddress(activity.getAddress());
        activity.getContact().ifPresent(contact -> descriptor.setPhone(contact.getPhone()));
        descriptor.setTags(activity.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditActivityDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditContactDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditContactDescriptor} that we are building.
     */
    public EditActivityDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditContactDescriptor}
     * that we are building.
     */
    public EditActivityDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditActivityDescriptor build() {
        return descriptor;
    }
}
