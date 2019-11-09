package seedu.address.model.note;

import java.util.Objects;

import seedu.address.model.classid.ClassId;

/**
 * Note Class
 */
public class Notes {
    private ClassId code;
    private ClassType type;
    private Content content;

    public Notes(ClassId code, ClassType type, Content content) {
        this.code = code;
        this.type = type;
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ClassId getCode() {
        return code;
    }

    public void setCode(ClassId code) {
        this.code = code;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public ClassType getType() {
        return type;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameNote(Notes otherNotes) {
        if (otherNotes == this) {
            return true;
        }

        return otherNotes != null
                && otherNotes.getCode().equals(getCode())
                && otherNotes.getType().equals(getType())
                && (otherNotes.getContent().equals(getContent()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Notes)) {
            return false;
        }

        Notes otherNotes = (Notes) other;
        return otherNotes.getCode().equals(getCode())
                && otherNotes.getType().equals(getType())
                && otherNotes.getContent().equals(getContent());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, type, content);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Module Code: ")
                .append(getCode())
                .append(" Type: ")
                .append(getType())
                .append(" Note: ")
                .append(getContent());
        return builder.toString();
    }
}
