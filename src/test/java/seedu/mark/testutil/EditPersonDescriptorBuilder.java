package seedu.mark.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.mark.logic.commands.EditCommand.EditBookmarkDescriptor;
import seedu.mark.model.bookmark.Address;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Phone;
import seedu.mark.model.tag.Tag;

/**
 * A utility class to help with building EditBookmarkDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditBookmarkDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditBookmarkDescriptor();
    }

    public EditPersonDescriptorBuilder(EditBookmarkDescriptor descriptor) {
        this.descriptor = new EditBookmarkDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookmarkDescriptor} with fields containing {@code bookmark}'s details
     */
    public EditPersonDescriptorBuilder(Bookmark bookmark) {
        descriptor = new EditBookmarkDescriptor();
        descriptor.setName(bookmark.getName());
        descriptor.setPhone(bookmark.getPhone());
        descriptor.setUrl(bookmark.getUrl());
        descriptor.setAddress(bookmark.getAddress());
        descriptor.setTags(bookmark.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setUrl(new Url(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookmarkDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookmarkDescriptor build() {
        return descriptor;
    }
}
