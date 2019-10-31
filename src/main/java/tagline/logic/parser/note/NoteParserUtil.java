// @@author shiweing
package tagline.logic.parser.note;

import static java.util.Objects.requireNonNull;

import tagline.logic.parser.exceptions.ParseException;
import tagline.model.note.Content;
import tagline.model.note.NoteId;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class NoteParserUtil {
    public static final String MESSAGE_INVALID_INDEX = "Note index is not a non-zero unsigned long.";

    /**
     * Parses {@code noteId} into an {@code NoteId} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static NoteId parseIndex(String noteId) throws ParseException {
        String trimmedId = noteId.trim();
        if (!isNonZeroUnsignedLong(trimmedId)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return new NoteId(Long.parseLong(trimmedId));
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned long
     * e.g. 1, 2, 3, ..., {@code Long.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedLong(String s) {
        requireNonNull(s);

        try {
            long value = Long.parseLong(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Long#parseLong(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Parses a {@code String content} into an {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Content.MESSAGE_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }
}
