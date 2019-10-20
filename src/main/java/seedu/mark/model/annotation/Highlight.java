package seedu.mark.model.annotation;

import seedu.mark.logic.parser.exceptions.ParseException;

/**
 * Represents the highlight colour of an annotation to a paragraph.
 */
public enum Highlight {
    YELLOW, ORANGE, GREEN, PINK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static final String MESSAGE_INVALID_COLOUR = "Highlight colours available: yellow, orange, green and pink.";

    /**
     * Parses a string of a colour into a {@code Highlight}.
     * @param arg The string of the colour
     * @return The highlight
     * @throws ParseException if {@code arg} is invalid.
     */
    public static Highlight strToHighlight(String arg) throws ParseException {
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
