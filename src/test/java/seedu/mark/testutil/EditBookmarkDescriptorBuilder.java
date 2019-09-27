package seedu.mark.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.mark.logic.commands.EditCommand.EditBookmarkDescriptor;
import seedu.mark.model.bookmark.Address;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Phone;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.tag.Tag;

/**
 * A utility class to help with building EditBookmarkDescriptor objects.
 */
public class EditBookmarkDescriptorBuilder {

    private EditBookmarkDescriptor descriptor;

    public EditBookmarkDescriptorBuilder() {
        descriptor = new EditBookmarkDescriptor();
    }

    public EditBookmarkDescriptorBuilder(EditBookmarkDescriptor descriptor) {
        this.descriptor = new EditBookmarkDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookmarkDescriptor} with fields containing {@code bookmark}'s details
     */
    public EditBookmarkDescriptorBuilder(Bookmark bookmark) {
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
    public EditBookmarkDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withUrl(String url) {
        descriptor.setUrl(new Url(url));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookmarkDescriptor}
     * that we are building.
     */
    public EditBookmarkDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookmarkDescriptor build() {
        return descriptor;
    }
}
