package seedu.mark.model.annotation;

/**
 * Represents the highlight colour of an annotation to a paragraph.
 */
public enum Highlight {
    YELLOW, ORANGE, GREEN, PINK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
