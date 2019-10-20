package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents an annotation consisting of highlight and note. Any annotation must have a highlight.
 */
public class Annotation {
    /** Paragraph highlight. */
    private Highlight colour;
    /** Paragraph notes, if any.*/
    private AnnotationNote note;

    public Annotation(Highlight colour) {
        requireNonNull(colour);
        this.colour = colour;
    }

    public Annotation(Highlight colour, AnnotationNote note) {
        this(colour);
        requireNonNull(note);
        this.note = note;
    }

    public boolean hasNote() {
        return note != null;
    }

    public Highlight getHighlight() {
        return colour;
    }

    public AnnotationNote getNote() {
        return note;
    }

    public void setHighlight(Highlight colour) {
        requireNonNull(colour);
        this.colour = colour;
    }

    public void setNote(AnnotationNote note) {
        requireNonNull(note);
        this.note = note;
    }

    public void removeNote() {
        this.note = null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Annotation)) {
            return false;
        } else {
            return this.colour.equals(((Annotation) other).colour) && this.note.equals(((Annotation) other).note);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colour, this.note);
    }
}
