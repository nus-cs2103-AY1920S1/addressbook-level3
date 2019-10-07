package seedu.weme.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.weme.logic.commands.EditCommand;
import seedu.weme.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.tag.Tag;

/**
 * A utility class to help with building EditMemeDescriptor objects.
 */
public class EditMemeDescriptorBuilder {

    private EditCommand.EditMemeDescriptor descriptor;

    public EditMemeDescriptorBuilder() {
        descriptor = new EditMemeDescriptor();
    }

    public EditMemeDescriptorBuilder(EditCommand.EditMemeDescriptor descriptor) {
        this.descriptor = new EditMemeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMemeDescriptor} with fields containing {@code meme}'s details
     */
    public EditMemeDescriptorBuilder(Meme meme) {
        descriptor = new EditMemeDescriptor();
        descriptor.setName(meme.getName());
        descriptor.setDescription(meme.getDescription());
        descriptor.setTags(meme.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditMemeDescriptor} that we are building.
     */
    public EditMemeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMemeDescriptor}
     * that we are building.
     */
    public EditMemeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditMemeDescriptor build() {
        return descriptor;
    }
}
