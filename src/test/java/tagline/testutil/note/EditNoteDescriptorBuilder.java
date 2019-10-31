package tagline.testutil.note;

import static tagline.logic.commands.note.EditNoteCommand.EditNoteDescriptor;

import tagline.model.note.Content;
import tagline.model.note.Note;
import tagline.model.note.Title;

/**
 * A utility class to help with building EditNoteDescriptor objects.
 */
public class EditNoteDescriptorBuilder {

    private EditNoteDescriptor descriptor;

    public EditNoteDescriptorBuilder() {
        descriptor = new EditNoteDescriptor();
    }

    public EditNoteDescriptorBuilder(EditNoteDescriptor descriptor) {
        this.descriptor = new EditNoteDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditNoteDescriptor} with fields containing {@code note}'s details
     */
    public EditNoteDescriptorBuilder(Note note) {
        descriptor = new EditNoteDescriptor();
        descriptor.setTitle(note.getTitle());
        descriptor.setContent(note.getContent());
        descriptor.setTags(note.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withContent(String content) {
        descriptor.setContent(new Content(content));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it
     * to the {@code EditNoteDescriptor} that we are building.
     */
    // TODO withTags(String.. tags)
    public EditNoteDescriptor build() {
        return descriptor;
    }
}
