package seedu.planner.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.planner.commons.core.index.Index;
import seedu.planner.commons.util.StringUtil;
import seedu.planner.logic.parser.exceptions.ParseException;

import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.NameWithTime;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Email;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;
import seedu.planner.model.tag.TagWithTime;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DURATION = "Duration is not a non-zero unsigned positive long value";
    public static final String MESSAGE_INVALID_PRIORITY = "Value of priority "
            + "is not a non-zero unsigned positive integer.";
    private static final String DATE_FORMAT_1 = "d-M-yyyy";
    private static final String DATE_FORMAT_2 = "d-M-yy";
    private static final String TIME_FORMAT = "HHmm";
    public static final DateTimeFormatter DATE_FORMATTER_1 = DateTimeFormatter.ofPattern(DATE_FORMAT_1);
    public static final DateTimeFormatter DATE_FORMATTER_2 = DateTimeFormatter.ofPattern(DATE_FORMAT_2);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

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
     * Parses a {@code String argValue} into a {@code NameWithTime}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static NameWithTime parseNameWithTime(String argValue) throws ParseException {
        requireNonNull(argValue);
        String trimmedArgValue = argValue.trim();
        String[] args = trimmedArgValue.split(" ");
        try {
            Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            return new NameWithTime(new Name(trimmedArgValue), null);
        }
        try {
            LocalTime parsedTime = LocalTime.parse(args[args.length - 1], TIME_FORMATTER);
            String name = trimmedArgValue.substring(0, trimmedArgValue.length() - 5);
            return new NameWithTime(new Name(name), parsedTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Time format is: " + TIME_FORMAT);
        }
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
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
     * Parses a {@code String argValue} into a {@code TagWithTime}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TagWithTime parseTagWithTime(String argValue) throws ParseException {
        requireNonNull(argValue);
        String trimmedArgValue = argValue.trim();
        String[] args = trimmedArgValue.split(" ");
        try {
            Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            return new TagWithTime(new Tag(trimmedArgValue), null);
        }
        try {
            LocalTime parsedTime = LocalTime.parse(args[args.length - 1], TIME_FORMATTER);
            String tag = trimmedArgValue.substring(0, trimmedArgValue.length() - 5);
            return new TagWithTime(new Tag(tag), parsedTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Time format is: " + TIME_FORMAT);
        }
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
     * Parses a {@code String days} into an {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static int parseDays(String days) throws ParseException {
        requireNonNull(days);
        String trimmedDays = days.trim();
        if (!Day.isValidDayNumber(trimmedDays)) {
            throw new ParseException(Day.MESSAGE_CONSTRAINTS);
        }
        return Integer.parseInt(days);
    }

    /**
     * Parses a {@code String days} into an {@code List<Integer>}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static List<Index> parseDaysToSchedule(String days) throws ParseException {
        requireNonNull(days);
        String[] trimmedDays = days.trim().split(" ");
        int numOfDays = trimmedDays.length;
        List<Index> dayList = new ArrayList<>();

        for (int i = 0; i < numOfDays; i++) {
            if (!Day.isValidDayNumber(trimmedDays[i])) {
                throw new ParseException(Day.MESSAGE_CONSTRAINTS);
            }
            dayList.add(parseIndex(trimmedDays[i]));
        }
        return dayList;
    }

    /**
     * Parses a {@code String time} into an {@code LocalTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        LocalTime parsedTime = null;
        try {
            parsedTime = LocalTime.parse(trimmedTime, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ParseException("Time format is: " + TIME_FORMAT);
        }
        return parsedTime;
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        return parseByDateFormats(trimmedDate, DATE_FORMATTER_1, DATE_FORMATTER_2);
    }

    /**
     * Tries to parse {@code trimmedDate} with the provided {@code dateFormats}.
     *
     * @throws ParseException
     */
    private static LocalDate parseByDateFormats(String trimmedDate, DateTimeFormatter... dateFormats)
            throws ParseException {
        LocalDate parsedDate = null;
        for (DateTimeFormatter format : dateFormats) {
            try {
                parsedDate = LocalDate.parse(trimmedDate, format);
                break;
            } catch (DateTimeParseException ignored) {
                parsedDate = null;
            }
        }
        if (parsedDate != null) {
            return parsedDate;
        } else {
            throw new ParseException(acceptableDateFormats());
        }
    }

    /**
     * Creates a message of possible date formats with the provided {@code dateFormats}.
     */
    private static String acceptableDateFormats() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date needs to be in either of the following formats: ")
                .append(DATE_FORMAT_1)
                .append(", ")
                .append(DATE_FORMAT_2);
        return sb.toString();
    }

    /**
     * Parses a {@code String cost} into a {@code Cost}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cost} is invalid.
     */
    public static Cost parseCost(String cost) throws ParseException {
        requireNonNull(cost);
        String trimmedCost = cost.trim();
        if (!Cost.isValidCost(trimmedCost)) {
            throw new ParseException(Cost.MESSAGE_CONSTRAINTS);
        }
        return new Cost(trimmedCost);
    }

    /**
     * Parses a {@code String min} into an {@code Duration}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static Duration parseDuration(String min) throws ParseException {
        requireNonNull(min);
        try {
            String trimmedMin = min.trim();
            Integer duration = Integer.parseInt(trimmedMin);
            return new Duration(duration);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
    }

    /**
     * Parses a {@code String priorityValue} into a {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     * Ensures that priority value is a positive integer.
     *
     * @throws ParseException if the given {@code priorityValue} is invalid.
     */
    public static Priority parsePriority(String priorityValue) throws ParseException {
        requireNonNull(priorityValue);
        try {
            Integer trimmedPriorityValue = Integer.parseInt(priorityValue.trim());
            if (trimmedPriorityValue <= 0) {
                throw new ParseException(MESSAGE_INVALID_PRIORITY);
            }
            return new Priority(trimmedPriorityValue);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_PRIORITY);
        }
    }
}
