package seedu.address.logic.commands.util;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.classid.ClassId;
import seedu.address.model.note.ClassType;
import seedu.address.model.note.Content;

import java.util.Optional;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class EditNotesDescriptor {
    private ClassId code;
    private ClassType type;
    private Content content;

    public EditNotesDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditNotesDescriptor(EditNotesDescriptor toCopy) {
        setModuleCode(toCopy.code);
        setClassType(toCopy.type);
        setContent(toCopy.content);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(code, type, content);
    }

    public void setModuleCode(ClassId code) {
        this.code = code;
    }

    public Optional<ClassId> getCode() {
        return Optional.ofNullable(code);
    }

    public void setClassType(ClassType type) {
        this.type = type;
    }

    public Optional<ClassType> getClassType() {
        return Optional.ofNullable(type);
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Optional<Content> getContent() {
        return Optional.ofNullable(content);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditNotesDescriptor)) {
            return false;
        }

        // state check
        EditNotesDescriptor e = (EditNotesDescriptor) other;

        return getCode().equals(e.getCode())
                && getClassType().equals(e.getClassType())
                && getContent().equals(e.getContent());
    }
}
