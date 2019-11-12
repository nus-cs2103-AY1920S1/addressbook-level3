package seedu.eatme.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.eatme.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.eatme.model.eatery.Address;
import seedu.eatme.model.eatery.Category;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Name;
import seedu.eatme.model.eatery.Tag;

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
        descriptor.setCategory(eatery.getCategory());
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
     * Sets the {@code Category} of the {@code EditEateryDescriptor} that we are building.
     */
    public EditEateryDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
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
