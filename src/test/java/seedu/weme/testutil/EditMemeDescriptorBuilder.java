package seedu.weme.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.weme.logic.commands.MemeEditCommand;
import seedu.weme.logic.commands.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * A utility class to help with building EditMemeDescriptor objects.
 */
public class EditMemeDescriptorBuilder {

    private MemeEditCommand.EditMemeDescriptor descriptor;

    public EditMemeDescriptorBuilder() {
        descriptor = new EditMemeDescriptor();
    }

    public EditMemeDescriptorBuilder(MemeEditCommand.EditMemeDescriptor descriptor) {
        this.descriptor = new EditMemeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMemeDescriptor} with fields containing {@code meme}'s details
     */
    public EditMemeDescriptorBuilder(Meme meme) {
        descriptor = new EditMemeDescriptor();
        descriptor.setFilePath(meme.getFilePath());
        descriptor.setDescription(meme.getDescription());
        descriptor.setTags(meme.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code EditMemeDescriptor} that we are building.
     * @param description
     */
    public EditMemeDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code ImageUrl} of the {@code EditMemeDescriptor} that we are building.
     * @param filePath
     */
    public EditMemeDescriptorBuilder withFilePath(String filePath) {
        descriptor.setFilePath(new ImagePath(filePath));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMemeDescriptor}
     * that we are building.
     */
    public EditMemeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public MemeEditCommand.EditMemeDescriptor build() {
        return descriptor;
    }
}
