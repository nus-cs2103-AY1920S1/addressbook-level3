package seedu.address.testutil;

import seedu.address.logic.commands.util.EditNotesDescriptor;
import seedu.address.model.classid.ClassId;
import seedu.address.model.note.ClassType;
import seedu.address.model.note.Content;
import seedu.address.model.note.Notes;

/**
 * A utility class to help with building EditNotesDescriptor objects.
 */
public class EditNotesDescriptorBuilder {
    private EditNotesDescriptor descriptor;

    public EditNotesDescriptorBuilder() {
        descriptor = new EditNotesDescriptor();
    }

    public EditNotesDescriptorBuilder(EditNotesDescriptor descriptor) {
        this.descriptor = new EditNotesDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditNotesDescriptorBuilder(Notes notes) {
        descriptor = new EditNotesDescriptor();
        descriptor.setModuleCode(notes.getCode());
        descriptor.setClassType(notes.getType());
        descriptor.setContent(notes.getContent());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditNotesDescriptorBuilder withModuleCode(String classId) {
        descriptor.setModuleCode(new ClassId(classId));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditNotesDescriptorBuilder withClassType(String type) {
        descriptor.setClassType(new ClassType(type));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditNotesDescriptorBuilder withContent(String content) {
        descriptor.setContent(new Content(content));
        return this;
    }

    public EditNotesDescriptor build() {
        return descriptor;
    }
}
