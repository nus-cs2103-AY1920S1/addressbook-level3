package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.Budget;
import seedu.address.model.finance.Money;
import seedu.address.model.finance.Spending;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.project.Description;
import seedu.address.model.project.Time;
import seedu.address.model.project.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.TimeRange;

import java.util.List;
import java.util.ArrayList;
import seedu.address.model.timetable.Timetable;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_MULTIPLE_INDEX = "Index is not a non-zero unsigned integer at position: ";

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

    public static List<Index> parseMultipleIndex(String oneBasedIndexes) throws ParseException {
        String[] oneBasedIndexesArr = oneBasedIndexes.trim().split("\\s+");
        List<Index> oneBasedIndexList = new ArrayList<>();
        int indexCount = 1;

        for (String oneBasedIndex : oneBasedIndexesArr) {
            if (!StringUtil.isNonZeroUnsignedInteger(oneBasedIndex)) {
                throw new ParseException(MESSAGE_INVALID_MULTIPLE_INDEX + indexCount);
            }
            oneBasedIndexList.add(Index.fromOneBased(Integer.parseInt(oneBasedIndex)));
            indexCount++;
        }

        return oneBasedIndexList;
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
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);

        String trimmedDateAndTime = time.trim();
        boolean checkLength = trimmedDateAndTime.split(" ").length < 2;
        if (checkLength || time == null || time.equals("")) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }

        String trimmedTime = time.trim().split(" ")[1];
        String trimmedDate = time.trim().split(" ")[0];
        if (!Time.isValidTimeAndDate(time.trim())) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        if (!Time.isValidDate(trimmedDate)) {
            throw new ParseException(Time.DATE_CONSTRAINTS);
        }
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.TIME_CONSTRAINTS);
        }
        try {
            return new Time(time.trim());
        } catch (Exception e) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
    }

    public static Description parseMeetingDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDesc = description.trim();
        if (!Description.isValidDescription(trimmedDesc)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDesc);
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
     * Parses {@code String timeRange} into a {@code TimeRange}.
     * Example: parseTimeRange(MONDAY 1100 TUESDAY 1500)
     * @param timeRange Format "DAYSTART TIMESTART DAYEND TIMEEND"
     */
    public static TimeRange parseTimeRange(String timeRange) throws ParseException {
        requireNonNull(timeRange);
        String[] split = timeRange.trim().split(" ");
        try {
            DayOfWeek dayStart = DayOfWeek.valueOf(split[0].trim().toUpperCase());
            LocalTime startTime = LocalTime.parse(split[1].trim(), DateTimeFormatter.ofPattern("HHmm"));
            DayOfWeek dayEnd = DayOfWeek.valueOf(split[2].trim().toUpperCase());
            LocalTime endTime = LocalTime.parse(split[3].trim(), DateTimeFormatter.ofPattern("HHmm"));
            return new TimeRange(dayStart, startTime, dayEnd, endTime);
        } catch (IllegalValueException | ArrayIndexOutOfBoundsException | java.lang.IllegalArgumentException | DateTimeParseException e) {
            throw new ParseException(TimeRange.MESSAGE_CONSTRAINTS);
        }
    }

    /** Parses a {@code String name, @code String number} into a {@code Budget}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code number} is invalid.
     */
    public static Budget parseBudget(String name, String number) throws ParseException {
        requireNonNull(name, number);
        String trimmedName = name.trim();
        if (!Description.isValidDescription(trimmedName)) {
            throw new ParseException("Budget name should only contain alphanumeric characters and spaces, and it should not be blank");
        }
        Money money = parseMoney(number);
        List<Spending> spendings = new ArrayList<>();
        return new Budget(trimmedName, money, spendings);
    }

    /**
     * Parse newline separated TimeRanges, return their Timetable representation
     * @param timetableString Newline separated TimeRanges
     * @return Timetable representation of the TimeRanges
     * @throws ParseException
     */
    public static Timetable parseTimetable(String timetableString) throws ParseException {
        String[] splitted = timetableString.split("\n");
        List<TimeRange> timeRanges = new ArrayList<>();
        for (String s : splitted) {
            try {
                timeRanges.add(parseTimeRange(s));
            } catch (ParseException e) {
                throw new ParseException(TimeRange.MESSAGE_CONSTRAINTS);
            }
        }
        return new Timetable(timeRanges);
    }

    /**
     * Parses a {@code String spending} into an {@code Money}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code spending} is invalid.
     */
    public static Money parseMoney(String spending) throws ParseException {
        String trimmedSpending = spending.trim();
        if (!Money.isValidAmount(trimmedSpending)) {
            throw new ParseException(Money.MESSAGE_CONSTRAINTS);
        }
        return new Money(new BigDecimal(spending));
    }
}
