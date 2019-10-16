package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.exceptions.IllegalValueException;

public class AnnotationNote {

    public static String MESSAGE_BLANK_NOTE = "Annotation note content should not be blank.";

    /** Note content.*/
    private String content;

    public AnnotationNote(String content) {
        this.content = content;
    }

    public static AnnotationNote makeNote(String content) throws IllegalValueException {
        requireNonNull(content);
        if (content.isBlank()) {
            throw new IllegalValueException(MESSAGE_BLANK_NOTE);
        }

        return new AnnotationNote(content);
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AnnotationNote
                && ((AnnotationNote) other).content.equals(this.content));
    }

}
