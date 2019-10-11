package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import seedu.address.commons.core.Alias;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws ParseException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code String timestamp} into a {@code Timestamp}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code timestamp} is invalid.
     */
    public static Timestamp parseTimestamp(String timestamp) throws ParseException {
        requireNonNull(timestamp);
        String trimmedTimestamp = timestamp.trim();
        if (!Timestamp.isValidTimestamp(trimmedTimestamp)) {
            throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS_DATE);
        }
        return new Timestamp(trimmedTimestamp);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String aliasName} and {@code String input} into a user defined {@code Alias}.
     *
     * @throws ParseException if the given {@code aliasName} is invalid.
     */
    public static Alias parseAlias(String aliasName, String input) throws ParseException {
        if (!Alias.isValidAliasName(aliasName)) {
            throw new ParseException(Alias.MESSAGE_NAME_CONSTRAINTS);
        }
        if (!Alias.isValidInput(input)) {
            throw new ParseException(Alias.MESSAGE_INPUT_CONSTRAINTS);
        }
        return new Alias(aliasName, input);
    }

    /**
     * Parses {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid (not in MM-d format).
     */
    public static LocalDate parseDate(String date) throws ParseException {
        String trimmedDate = date.trim();
        try {
            int currentYear = LocalDate.now().getYear();
            DateTimeFormatter formatterWithoutYear = new DateTimeFormatterBuilder()
                    .appendPattern("dd-MM")
                    .parseDefaulting(ChronoField.YEAR, currentYear)
                    .toFormatter(Locale.ENGLISH);
            LocalDate parsedDate = LocalDate.parse(trimmedDate, formatterWithoutYear);
            return parsedDate;
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatterWithYear = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
                LocalDate parsedDate = LocalDate.parse(trimmedDate, formatterWithYear);
                return parsedDate;
            } catch (DateTimeParseException e1) {
                throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS_DATE);
            }
        }
    }

    /**
     * Parses {@code String period} into a {@code Period}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code period} is invalid.
     */
    public static Period parsePeriod(String period) throws ParseException {
        String trimmedPeriod = period.trim();
        switch (trimmedPeriod) {
        case "week":
            return Period.ofWeeks(1);
        case "month":
            return Period.ofMonths(1);
        case "year":
            return Period.ofYears(1);
        default:
            throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS_PERIOD);
        }
    }
}
