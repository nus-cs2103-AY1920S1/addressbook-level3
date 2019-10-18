package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building UpdatePersonDescriptor objects.
 */
public class UpdatePersonDescriptorBuilder {

    private UpdatePersonDescriptor descriptor;

    public UpdatePersonDescriptorBuilder() {
        descriptor = new UpdatePersonDescriptor();
    }

    public UpdatePersonDescriptorBuilder(UpdatePersonDescriptor descriptor) {
        this.descriptor = new UpdatePersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdatePersonDescriptor} with fields containing {@code person}'s details
     */
    public UpdatePersonDescriptorBuilder(Person person) {
        descriptor = new UpdatePersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setUsername(person.getUsername());
        descriptor.setPassword(person.getPassword());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withUsername(String username) {
        descriptor.setUsername(new Username(username));
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withPassword(String password) {
        descriptor.setPassword(new Password(password));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code UpdatePersonDescriptor}
     * that we are building.
     */
    public UpdatePersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdatePersonDescriptor build() {
        return descriptor;
    }
}
