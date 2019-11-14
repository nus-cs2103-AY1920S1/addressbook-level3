package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public abstract class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PROPERTY = "Expenses can only be sorted by name or amount.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses the given {@code String} representing a property of expense as sorting category.
     * It is case insensitive.
     *
     * @param prop The property string to parse.
     * @throws ParseException If the string is neither "name" nor "amount" (case-insensitive).
     */
    public static String parseProperty(String prop) throws ParseException {
        requireNonNull(prop);
        String trimmed = prop.trim();
        if (trimmed.equalsIgnoreCase("name")) {
            return "name";
        } else if (trimmed.equalsIgnoreCase("amount")) {
            return "amount";
        } else {
            throw new ParseException(MESSAGE_INVALID_PROPERTY);
        }
    }

}
