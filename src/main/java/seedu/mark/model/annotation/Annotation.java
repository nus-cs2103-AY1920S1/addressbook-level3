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

    /**
     * Constructs an {@code Annotation} with only highlight {@code colour}.
     * {@code colour} must be non-null.
     */
    public Annotation(Highlight colour) {
        requireNonNull(colour);
        this.colour = colour;
        this.note = null;
    }

    /**
     * Constructs an {@code Annotation} that has highlight {@code colour} and note {@code note}.
     * Both {@code colour} and {@code note} must be non-null.
     */
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

    /**
     * Returns a copy of this {@code Annotation}.
     */
    public Annotation copy() {
        if (hasNote()) {
            return new Annotation(colour, note.copy());
        } else {
            return new Annotation(colour);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Annotation)) {
            return false;
        }
        return this.getHighlight().equals(((Annotation) other).getHighlight())
                && (this.getNote() == ((Annotation) other).getNote()
                || this.getNote().equals(((Annotation) other).getNote()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.colour, this.note);
    }

    @Override
    public String toString() {
        if (hasNote()) {
            return colour.toString() + " highlight with note \"" + note.toString() + "\"";
        }
        return colour.toString() + "highlight";
    }
}
