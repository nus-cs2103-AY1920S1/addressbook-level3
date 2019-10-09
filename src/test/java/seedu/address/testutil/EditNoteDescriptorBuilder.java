package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditNoteDescriptor;
import seedu.address.model.person.Content;
import seedu.address.model.person.Note;
import seedu.address.model.person.Title;

/**
 * A utility class to help with building EditNoteDescriptor objects.
 */
public class EditNoteDescriptorBuilder {

    private EditNoteDescriptor descriptor;

    public EditNoteDescriptorBuilder() {
        descriptor = new EditNoteDescriptor();
    }

    public EditNoteDescriptorBuilder(EditCommand.EditNoteDescriptor descriptor) {
        this.descriptor = new EditCommand.EditNoteDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditNoteDescriptor} with fields containing {@code note}'s details
     */
    public EditNoteDescriptorBuilder(Note note) {
        descriptor = new EditNoteDescriptor();
        descriptor.setTitle(note.getTitle());
        descriptor.setContent(note.getContent());
    }

    /**
     * Sets the {@code Title} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withTitle(String name) {
        descriptor.setTitle(new Title(name));
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withContent(String address) {
        descriptor.setContent(new Content(address));
        return this;
    }

    public EditNoteDescriptor build() {
        return descriptor;
    }
}
