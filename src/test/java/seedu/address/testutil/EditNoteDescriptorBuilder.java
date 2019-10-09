package seedu.address.testutil;

import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.logic.commands.EditNoteCommand.EditNoteDescriptor;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;

/**
 * A utility class to help with building EditNoteDescriptor objects.
 */
public class EditNoteDescriptorBuilder {

    private EditNoteDescriptor descriptor;

    public EditNoteDescriptorBuilder() {
        descriptor = new EditNoteDescriptor();
    }

    public EditNoteDescriptorBuilder(EditNoteCommand.EditNoteDescriptor descriptor) {
        this.descriptor = new EditNoteCommand.EditNoteDescriptor(descriptor);
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
