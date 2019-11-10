package seedu.address.itinerary.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.itinerary.logic.commands.SortCommand;
import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.PageType;

import java.util.stream.Stream;

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
     * Validation check for the user input.
     * @param pageType the specified page to navigate to in TravEzy.
     * @return the specified page type and navigate to that page.
     * @throws ParseException if the specified page is invalid.
     */
    public static PageType parsePageType(String pageType) throws ParseException {
        requireNonNull(pageType);
        String trimmedPageType = pageType.trim();
        try {
            PageType requestedPage = PageType.valueOf(trimmedPageType.toUpperCase());
            return requestedPage;
        } catch (IllegalArgumentException e) {
            throw new ParseException(PageType.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Validation check for the user input.
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Title parseTitle(String name) throws ParseException {
        requireNonNull(name);
        String trimmedTitle = name.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Validation check for the user input.
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Description parseDescription(String name) throws ParseException {
        String trimmedDesc = name.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
    }

    /**
     * Validation check for the user input.
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Location parseLocation(String name) throws ParseException {
        String trimmedLocation = name.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Validation check for the user input.
     * Parses a {@code String time} into a {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Time parseTime(String name) throws ParseException {
        String trimmedTime = name.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Validation check for the user input.
     * @param date the date that is being specified by the user.
     * @return the formatted date in dd/MM/yy.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Validation check for the user input.
     * @param type the type that is being specified by the user.
     * @return the formatted type after validation.
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static String parseType(String type) throws ParseException {
        String trimmedType = type.trim();
        if (type.equals("title") || type.equals("chronological") || type.equals("completion")
            || type.equals("location") || type.equals("priority")) {
            return trimmedType;
        } else {
            throw new ParseException(SortCommand.MESSAGE_FAIL);
        }
    }

    /**
     * Validation check for the user input.
     * @param tag the tag that is being specified by the user.
     * @return the labelled tag with the priority of the event.
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        if (!Tag.isValidTag(tag)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return new Tag(tag);
    }
}
