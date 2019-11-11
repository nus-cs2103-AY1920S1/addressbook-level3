package seedu.billboard.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.billboard.commons.core.Messages;
import seedu.billboard.commons.core.date.DateInterval;
import seedu.billboard.commons.core.date.DateRange;
import seedu.billboard.commons.core.index.Index;
import seedu.billboard.commons.util.StringUtil;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.expense.Amount;
import seedu.billboard.model.expense.CreatedDateTime;
import seedu.billboard.model.expense.Description;
import seedu.billboard.model.expense.Name;
import seedu.billboard.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
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
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
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
     * Parses {@code String tagName} into a valid tag name.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given tag name is invalid.
     */
    public static String parseTagName(String tagName) throws ParseException {
        requireNonNull(tagName);
        String trimmedTag = tagName.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return trimmedTag;
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
     * Parses {@code Collection<String> tagNames} into a valid {@code List<String>}
     */
    public static List<String> parseTagNames(Collection<String> tagNames) throws ParseException {
        requireNonNull(tagNames);
        List<String> parsed = new ArrayList<>();
        for (String tagName : tagNames) {
            parsed.add(parseTagName(tagName));
        }
        return parsed;
    }

    /**
     * Parses a {@code String description} into an {@code Description}.
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
     * Parses a {@code String amount} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String dateTime} into an {@code CreatedDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static CreatedDateTime parseCreatedDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!CreatedDateTime.isValidDate(trimmedDateTime)) {
            throw new ParseException(CreatedDateTime.MESSAGE_CONSTRAINTS);
        }
        return new CreatedDateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String archive} into an {@code String trimmedArchive}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code archive} is invalid.
     */
    public static String parseArchive(String archive) throws ParseException {
        requireNonNull(archive);
        String trimmedArchive = archive.trim();
        if (trimmedArchive.equals("")) {
            throw new ParseException(Messages.MESSAGE_INVALID_ARCHIVE_NAME);
        }
        return trimmedArchive;
    }

    /**
     * Parses a {@code String dateInterval} into an {@code DateInterval}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateInterval} is invalid.
     */
    public static DateInterval parseInterval(String interval) throws ParseException {
        requireNonNull(interval);
        String trimmedInterval = interval.trim().toUpperCase();
        if (!DateInterval.isValidDateInterval(trimmedInterval)) {
            throw new ParseException(DateInterval.MESSAGE_CONSTRAINTS);
        }

        return DateInterval.valueOf(trimmedInterval);
    }

    /**
     * Parses a {@code DateRange} and a {@code DateInterval} into an integer indicating number of iterations.
     *
     */
    public static int parseIterations(DateRange dateRange, DateInterval dateInterval) {
        LocalDate startDate = dateRange.getStartDate();
        LocalDate endDate = dateRange.getEndDateInclusive();
        int iteration = 0;

        while (!startDate.isAfter(endDate)) {
            startDate = startDate.plus(dateInterval.getPeriod());
            iteration++;
        }
        return iteration;
    }
}
