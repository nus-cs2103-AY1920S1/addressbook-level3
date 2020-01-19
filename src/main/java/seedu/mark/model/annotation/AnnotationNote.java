package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.exceptions.IllegalValueException;

/**
 * Represents a note that is part of an annotation.
 */
public class AnnotationNote {
    public static final AnnotationNote SAMPLE_NOTE = new AnnotationNote("note");

    public static final String MESSAGE_BLANK_NOTE = "Annotation note content should not be blank.";

    /** Note content.*/
    private String content;

    private AnnotationNote(String content) {
        this.content = content;
    }

    /**
     * Creates a note for annotating a paragraph.
     * //TODO: need to specify null pointer exception?
     * @param content Plaintext content of note
     * @return the note
     * @throws IllegalValueException if content is blank.
     */
    public static AnnotationNote makeNote(String content) throws IllegalValueException {
        requireNonNull(content);
        if (isEmptyString(content)) {
            throw new IllegalValueException(MESSAGE_BLANK_NOTE);
        }

        return new AnnotationNote(content);
    }

    private static boolean isEmptyString(String str) {
        return str.strip().isBlank();
    }

    /**
     * Returns a copy of this {@code AnnotationNote}.
     */
    public AnnotationNote copy() {
        return new AnnotationNote(content);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AnnotationNote)) {
            return false;
        }

        return ((AnnotationNote) other).content.equals(this.content);
    }

    @Override
    public int hashCode() {
        return this.content.hashCode();
    }

}
