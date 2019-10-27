package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Represents the highlight colour of an annotation to a paragraph.
 * Colours supported are: yellow, orange, green and pink.
 */
public enum Highlight {
    YELLOW, ORANGE, GREEN, PINK;

    public static final String MESSAGE_INVALID_COLOUR = "Highlight colours available: yellow, orange, green and pink.";

    /**
     * Returns the lower-case name of highlight colours.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    /**
     * Parses a string of a colour into a {@code Highlight}.
     * @param arg The string of the colour; must be non-null
     * @return The highlight
     * @throws ParseException if {@code arg} is invalid.
     */
    public static Highlight strToHighlight(String arg) throws ParseException {
        requireNonNull(arg);
        switch (arg.trim().toLowerCase()) {
        case "yellow":
            return YELLOW;
        case "orange":
            return ORANGE;
        case "green":
            return GREEN;
        case "pink":
            return PINK;
        default:
            throw new ParseException(MESSAGE_INVALID_COLOUR);
        }
    }
}
