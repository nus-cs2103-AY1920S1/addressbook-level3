package seedu.module.logic.parser;

import org.apache.commons.validator.routines.UrlValidator;

import seedu.module.commons.core.index.Index;
import seedu.module.commons.util.StringUtil;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Returns True if the string given is a valid URL
     * @param url
     * @return
     */
    public static boolean isValidUrl(String url) {
        String[] scheme = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(scheme);
        return urlValidator.isValid(url);
    }

    /**
     * Ensures the url entered contains a http scheme, if not, adds a scheme to it and returns it
     * @param url
     * @return
     */
    public static String checkScheme(String url) {
        String properLink = url;
        if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
            properLink = "http://" + url;
        }
        return properLink;
    }

}
