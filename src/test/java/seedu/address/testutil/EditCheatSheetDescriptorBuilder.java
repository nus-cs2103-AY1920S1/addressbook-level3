package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand.EditCheatSheetDescriptor;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCheatSheetDescriptor objects.
 */
public class EditCheatSheetDescriptorBuilder {
    private EditCheatSheetDescriptor descriptor;

    public EditCheatSheetDescriptorBuilder() {
        descriptor = new EditCheatSheetDescriptor();
    }

    public EditCheatSheetDescriptorBuilder(EditCheatSheetDescriptor descriptor) {
        this.descriptor = new EditCheatSheetDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCheatSheetDescriptor} with fields containing {@code cheatsheet}'s details
     */
    public EditCheatSheetDescriptorBuilder(CheatSheet cheatSheet) {
        descriptor = new EditCheatSheetDescriptor();
        descriptor.setTitle(cheatSheet.getTitle());
        descriptor.setTags(cheatSheet.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditCheatSheetDescriptor} that we are building.
     */
    public EditCheatSheetDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCheatSheetDescriptor}
     * that we are building.
     */
    public EditCheatSheetDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCheatSheetDescriptor build() {
        return descriptor;
    }
}
