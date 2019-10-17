package seedu.mark.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.mark.logic.commands.EditCommand.EditBookmarkDescriptor;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Name;
import seedu.mark.model.bookmark.Remark;
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
        descriptor.setUrl(bookmark.getUrl());
        descriptor.setRemark(bookmark.getRemark());
        descriptor.setFolder(bookmark.getFolder());
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
     * Sets the {@code Url} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withUrl(String url) {
        descriptor.setUrl(new Url(url));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Folder} of the {@code EditBookmarkDescriptor} that we are building.
     */
    public EditBookmarkDescriptorBuilder withFolder(String folder) {
        descriptor.setFolder(new Folder(folder));
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
