package io.xpire.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.xpire.logic.commands.EditCommand.EditItemDescriptor;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.Quantity;
import io.xpire.model.tag.Tag;

/**
 * A utility class to help with building EditItemDescriptor objects.
 */
public class EditItemDescriptorBuilder {

    private EditItemDescriptor descriptor;

    public EditItemDescriptorBuilder() {
        descriptor = new EditItemDescriptor();
    }

    public EditItemDescriptorBuilder(EditItemDescriptor descriptor) {
        this.descriptor = new EditItemDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditItemDescriptor} with fields containing {@code item}'s details
     */
    public EditItemDescriptorBuilder(Item item) {
        descriptor = new EditItemDescriptor();
        descriptor.setName(item.getName());
        descriptor.setExpiryDate(item.getExpiryDate());
        descriptor.setTags(item.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code ExpiryDate} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withExpiryDate(String expiryDate) {
        descriptor.setExpiryDate(new ExpiryDate(expiryDate));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code EditItemDescriptor} that we are building.
     */
    public EditItemDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditItemDescriptor}
     * that we are building.
     */
    public EditItemDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditItemDescriptor build() {
        return descriptor;
    }

}
