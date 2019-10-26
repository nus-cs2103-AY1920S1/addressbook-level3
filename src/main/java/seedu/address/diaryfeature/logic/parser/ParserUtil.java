package seedu.address.diaryfeature.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.diaryfeature.model.DateFormatter;
import seedu.address.diaryfeature.model.Title;
import seedu.address.diaryfeature.model.exceptions.TitleException;
import seedu.address.logic.parser.exceptions.ParseException;


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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Title parseTitle(String title) throws TitleException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        Title formed = new Title(trimmedTitle);
        return formed;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Date parseDate(String date) throws java.text.ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        Date requiredDate = DateFormatter.convertToDate(trimmedDate);
        return requiredDate;
    }



}
