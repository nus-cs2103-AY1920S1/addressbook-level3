// @@author shiweing
package tagline.logic.parser.note;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import tagline.logic.parser.ArgumentMultimap;
import tagline.logic.parser.Prefix;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.note.NoteId;
import tagline.model.note.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class NoteParserUtil {
    public static final String ERROR_INVALID_INDEX = "Note index is not a non-zero unsigned number: %1$s";
    public static final String ERROR_SINGLE_PREFIX_USAGE =
            "Please only provide a single instance of the prefix: %1$s";

    /**
     * Parses {@code noteId} into an {@code NoteId} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static NoteId parseIndex(String noteId) throws ParseException {
        String trimmedId = noteId.trim();
        if (!isNonZeroUnsignedLong(trimmedId)) {
            throw new ParseException(String.format(ERROR_INVALID_INDEX, noteId));
        }
        return new NoteId(Long.parseLong(trimmedId));
    }

    /**
     * Parses {@code title} into a {@code Title} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified title is invalid (more than 50 characters).
     */
    public static Title parseTitle(String title) throws ParseException {
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }

        return new Title(trimmedTitle);
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
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean allPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks there is at most one value in the {@code argMultimap} for the {@code prefixes} provided.
     * If more than one value is found, a {@code ParseException} is thrown.
     */
    public static void checkSinglePrefixUsage(ArgumentMultimap argMultimap, Prefix... prefixes)
            throws ParseException {
        for (Prefix prefix : prefixes) {
            if (argMultimap.getAllValues(prefix).size() > 1) {
                throw new ParseException(String.format(ERROR_SINGLE_PREFIX_USAGE, prefix));
            }
        }
    }
}
