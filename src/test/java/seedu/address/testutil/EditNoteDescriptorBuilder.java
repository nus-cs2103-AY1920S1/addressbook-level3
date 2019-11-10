package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditNoteCommand;
import seedu.address.model.card.Description;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteDescription;
import seedu.address.model.note.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditNoteDescriptorBuilder {

    private EditNoteCommand.EditNoteDescriptor descriptor;

    public EditNoteDescriptorBuilder() {
        descriptor = new EditNoteCommand.EditNoteDescriptor();
    }

    public EditNoteDescriptorBuilder(EditNoteCommand.EditNoteDescriptor descriptor) {
        this.descriptor = new EditNoteCommand.EditNoteDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditNoteDescriptor} with fields containing {@code note}'s details
     */
    public EditNoteDescriptorBuilder(Note note) {
        descriptor = new EditNoteCommand.EditNoteDescriptor();
        descriptor.setTitle(note.getTitle());
        descriptor.setDescription(note.getDescription());
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
     * Sets the {@code Description} of the {@code EditNoteDescriptor} that we are building.
     */
    public EditNoteDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new NoteDescription(description));
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditNoteDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditNoteCommand.EditNoteDescriptor build() {
        return descriptor;
    }
}
