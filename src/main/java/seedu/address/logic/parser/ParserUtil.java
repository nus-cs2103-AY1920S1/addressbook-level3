package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.Budget;
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

import seedu.address.model.timetable.TimeTable;

import static seedu.address.model.finance.Spending.DATE_FORMAT;

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
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        try {
            return new Time(trimmedTime);
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
     * Example: parseTimeRange(MONDAY TUESDAY 1100 1500)
     * @param timeRange Format "DAYSTART TIMESTART DAYEND TIMEEND"
     */
    public static TimeRange parseTimeRange(String timeRange) throws ParseException {
        requireNonNull(timeRange);
        String[] split = timeRange.trim().split(" ");
        DayOfWeek dayStart = DayOfWeek.valueOf(split[0].trim());
        LocalTime startTime = LocalTime.parse(split[1].trim(), DateTimeFormatter.ofPattern("HHmm"));
        DayOfWeek dayEnd = DayOfWeek.valueOf(split[2].trim());
        LocalTime endTime = LocalTime.parse(split[3].trim(), DateTimeFormatter.ofPattern("HHmm"));
        try {
            return new TimeRange(dayStart, startTime, dayEnd, endTime);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeRange.MESSAGE_CONSTRAINTS));
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
        String trimmedNumber = number.trim();
        Double doubleNumber;
        if (!Budget.isValidAmount(trimmedNumber)) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
        doubleNumber = Double.valueOf(trimmedNumber);
        List<Spending> spendings = new ArrayList<>();
        return new Budget(trimmedName, doubleNumber, spendings);
    }

    /**
     * Parses {@code List<String> budgets} into a {@code Set<Budget>}.
     */
    public static List<Budget> parseBudgets(List<String> budgets) throws ParseException {
        requireNonNull(budgets);
        String[] strs = budgets.get(0).split(" ");
        if ((strs.length) % 2 != 0) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
        final List<Budget> budgetSet = new ArrayList<>();
        for (int i = 0; i < strs.length / 2; i++) {
            budgetSet.add(parseBudget(strs[i], strs[i + 1]));
        }
        return budgetSet;
    }

    /**
     *
     */
    public static TimeTable parseTimeTable(String timeTableString) throws ParseException {
        String[] splitted = timeTableString.split("\n");
        List<TimeRange> timeRanges = new ArrayList<>();
        for (String s : splitted) {
            try {
                timeRanges.add(parseTimeRange(s));
            } catch (ParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimeRange.MESSAGE_CONSTRAINTS));
            }
        }
        return new TimeTable(timeRanges);
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        String trimmedDate = date.trim();
        if (!Spending.isValidDate(trimmedDate)) {
            throw new ParseException(Spending.MESSAGE_CONSTRAINTS);
        }
        Date result = new Date();
        try {
            result = DATE_FORMAT.parse(trimmedDate);
        } catch (java.text.ParseException e) {
            throw new ParseException(Spending.MESSAGE_CONSTRAINTS);
        }
        return result;
    }

    /**
     * Parses a {@code String spending} into an {@code Spending}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code spending} is invalid.
     */
    public static Double parseSpending(String spending) throws ParseException {
        String trimmedSpending = spending.trim();
        if (!Spending.isValidAmount(trimmedSpending)) {
            throw new ParseException(Spending.MESSAGE_CONSTRAINTS);
        }
        return Double.valueOf(spending);
    }
}
