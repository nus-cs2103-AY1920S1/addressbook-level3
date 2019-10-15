package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditEateryDescriptor objects.
 */
public class EditEateryDescriptorBuilder {

    private EditEateryDescriptor descriptor;

    public EditEateryDescriptorBuilder() {
        descriptor = new EditEateryDescriptor();
    }

    public EditEateryDescriptorBuilder(EditEateryDescriptor descriptor) {
        this.descriptor = new EditEateryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEateryDescriptor} with fields containing {@code eatery}'s details
     */
    public EditEateryDescriptorBuilder(Eatery eatery) {
        descriptor = new EditEateryDescriptor();
        descriptor.setName(eatery.getName());
        descriptor.setAddress(eatery.getAddress());
        descriptor.setTags(eatery.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditEateryDescriptor} that we are building.
     */
    public EditEateryDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditEateryDescriptor} that we are building.
     */
    public EditEateryDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEateryDescriptor}
     * that we are building.
     */
    public EditEateryDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditEateryDescriptor build() {
        return descriptor;
    }
}
