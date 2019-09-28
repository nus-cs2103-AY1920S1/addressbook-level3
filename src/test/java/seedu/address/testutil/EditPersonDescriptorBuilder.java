package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.address.model.meme.Address;
import seedu.address.model.meme.Email;
import seedu.address.model.meme.Name;
import seedu.address.model.meme.Meme;
import seedu.address.model.meme.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditMemeDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditMemeDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditMemeDescriptor();
    }

    public EditPersonDescriptorBuilder(EditCommand.EditMemeDescriptor descriptor) {
        this.descriptor = new EditMemeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMemeDescriptor} with fields containing {@code meme}'s details
     */
    public EditPersonDescriptorBuilder(Meme meme) {
        descriptor = new EditMemeDescriptor();
        descriptor.setName(meme.getName());
        descriptor.setPhone(meme.getPhone());
        descriptor.setEmail(meme.getEmail());
        descriptor.setAddress(meme.getAddress());
        descriptor.setTags(meme.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditMemeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditMemeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditMemeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditMemeDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMemeDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditMemeDescriptor build() {
        return descriptor;
    }
}
