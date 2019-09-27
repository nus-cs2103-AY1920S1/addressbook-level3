package seedu.address.testutil.personutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class PersonDescriptorBuilder {

    //public static final String DEFAULT_NAME = "Ally";

    private PersonDescriptor descriptor;

    public PersonDescriptorBuilder() {
        descriptor = new PersonDescriptor();
        //descriptor.setName(new Name(DEFAULT_NAME));
    }

    public PersonDescriptorBuilder(Person person) {
        this.descriptor = new PersonDescriptor();
        this.descriptor.setName(person.getName());
        this.descriptor.setPhone(person.getPhone());
        this.descriptor.setEmail(person.getEmail());
        this.descriptor.setAddress(person.getAddress());
        this.descriptor.setTags(person.getTags());
        this.descriptor.setRemark(person.getRemark());
    }

    public PersonDescriptorBuilder(PersonDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public PersonDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public PersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public PersonDescriptor build() {
        return descriptor;
    }
}
