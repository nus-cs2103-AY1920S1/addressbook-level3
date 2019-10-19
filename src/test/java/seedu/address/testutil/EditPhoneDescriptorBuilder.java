package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPhoneDescriptor objects.
 */
public class EditPhoneDescriptorBuilder {

    private EditPhoneDescriptor descriptor;

    public EditPhoneDescriptorBuilder() {
        descriptor = new EditPhoneDescriptor();
    }

    public EditPhoneDescriptorBuilder(EditPhoneDescriptor descriptor) {
        this.descriptor = new EditPhoneDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPhoneDescriptor} with fields containing {@code phone}'s details
     */
    public EditPhoneDescriptorBuilder(Phone phone) {
        descriptor = new EditPhoneDescriptor();
        descriptor.setSerialNumber(phone.getSerialNumber());
        descriptor.setIdentityNumber(phone.getIdentityNumber());
        descriptor.setPhoneName(phone.getPhoneName());
        descriptor.setBrand(phone.getBrand());
        descriptor.setCapacity(phone.getCapacity());
        descriptor.setColour(phone.getColour());
        descriptor.setCost(phone.getCost());
        descriptor.setTags(phone.getTags());
    }

    /**
     * Sets the {@code PhoneName} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withPhoneName(String name) {
        descriptor.setPhoneName(new PhoneName(name));
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withSerialNumber(String serialNumber) {
        descriptor.setSerialNumber(new SerialNumber(serialNumber));
        return this;
    }

    /**
     * Sets the {@code IdentityNumber} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withIdentityNumber(String identityNumber) {
        descriptor.setIdentityNumber(new IdentityNumber(identityNumber));
        return this;
    }

    /**
     * Sets the {@code Brand} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withBrand(String brand) {
        descriptor.setBrand(new Brand(brand));
        return this;
    }

    /**
     * Sets the {@code Capacity} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withCapacity(Capacity capacity) {
        descriptor.setCapacity(capacity);
        return this;
    }

    /**
     * Sets the {@code Colour} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withColour(String colour) {
        descriptor.setColour(new Colour(colour));
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code EditPhoneDescriptor} that we are building.
     */
    public EditPhoneDescriptorBuilder withCost(String cost) {
        descriptor.setCost(new Cost(cost));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPhoneDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPhoneDescriptor build() {
        return descriptor;
    }
}
