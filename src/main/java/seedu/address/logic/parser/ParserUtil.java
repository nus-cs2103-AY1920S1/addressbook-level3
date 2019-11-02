package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeGender;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeJoinDate;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.employee.EmployeePosition;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDateTimeMap;
import seedu.address.model.event.EventDayTime;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_YEAR_MONTH = DateTimeFormatter.ofPattern("MM/yyyy");

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
     * Parses a {@code String name} into a {@code EmployeeName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static EmployeeName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EmployeeName.isValidName(trimmedName)) {
            throw new ParseException(EmployeeName.MESSAGE_CONSTRAINTS);
        }
        return new EmployeeName(trimmedName);
    }

    /**
     * Parses a {@code String position} into a {@code EmployeePosition}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code position} is invalid.
     */
    public static EmployeePosition parsePosition(String position) throws ParseException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        if (!EmployeePosition.isValidPosition(trimmedPosition)) {
            throw new ParseException(EmployeePosition.MESSAGE_CONSTRAINTS);
        }
        return new EmployeePosition(trimmedPosition);
    }

    /**
     * Parses a {@code String gender} into a {@code EmployeeGender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static EmployeeGender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!EmployeeGender.isValidGender(trimmedGender)) {
            throw new ParseException(EmployeeGender.MESSAGE_CONSTRAINTS);
        }
        return new EmployeeGender(trimmedGender.toLowerCase());
    }


    /**
     * Parses a {@code String phone} into a {@code EmployeePhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static EmployeePhone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!EmployeePhone.isValidPhone(trimmedPhone)) {
            throw new ParseException(EmployeePhone.MESSAGE_CONSTRAINTS);
        }
        return new EmployeePhone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code EmployeeAddress}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static EmployeeAddress parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!EmployeeAddress.isValidAddress(trimmedAddress)) {
            throw new ParseException(EmployeeAddress.MESSAGE_CONSTRAINTS);
        }
        return new EmployeeAddress(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code EmployeeEmail}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static EmployeeEmail parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!EmployeeEmail.isValidEmail(trimmedEmail)) {
            throw new ParseException(EmployeeEmail.MESSAGE_CONSTRAINTS);
        }
        return new EmployeeEmail(trimmedEmail);
    }

    /**
     * Parses a {@code String phone} into a {@code EmployeePhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static EmployeeId parseEmployeeId() {
        return new EmployeeId();
    }

    /**
     * Parses a {@code String EmployeeJoinDate} into a {@code joinDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EmployeeJoinDate} is invalid.
     */
    public static EmployeeJoinDate parseJoinDate(String joinDate) throws ParseException {
        requireNonNull(joinDate);
        String trimmed = joinDate.trim();
        if (!EmployeeJoinDate.isValidJoinDate(trimmed)) {
            throw new ParseException(EmployeeJoinDate.MESSAGE_CONSTRAINTS);
        }
        LocalDate newJoinDate = LocalDate.parse(trimmed, FORMATTER);
        return new EmployeeJoinDate(newJoinDate);
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
     * Parses a {@code String Eventname} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Eventname} is invalid.
     */
    public static EventName parseEventName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EventName.isValidName(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses a {@code String venueName} into a {@code EventVenue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventVenue} is invalid.
     */
    public static EventVenue parseVenue(String venueName) throws ParseException {
        requireNonNull(venueName);
        String trimmedName = venueName.trim();
        if (!EventVenue.isValidVenue(trimmedName)) {
            throw new ParseException(EventVenue.MESSAGE_CONSTRAINTS);
        }
        return new EventVenue(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code EventDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventDate} is invalid.
     */
    public static EventDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmed = date.trim();
        if (!EventDate.isValidDate(trimmed)) {
            throw new ParseException(EventDate.MESSAGE_CONSTRAINTS);
        }
        LocalDate newDate = LocalDate.parse(trimmed, FORMATTER);
        return new EventDate(newDate);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LocalDate} is invalid.
     */
    public static LocalDate parseSysDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmed = date.trim();
        LocalDate newDate;

        try {
            newDate = LocalDate.parse(trimmed, FORMATTER);
        } catch (DateTimeException e) {
            throw new ParseException("Date should be in the following format dd/MM/yyyy");
        }

        return newDate;
    }

    /**
     * Parses a {@code String date} into a {@code YearMonth}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code YearMonth} is invalid.
     */
    public static YearMonth parseYearMonth(String date) throws ParseException {
        requireNonNull(date);
        String trimmed = date.trim();
        if (!EventDate.isValidYearMonth(trimmed)) {
            throw new ParseException(EventDate.MESSAGE_CONSTRAINTS_MONTH);
        }
        return YearMonth.parse(trimmed, FORMATTER_YEAR_MONTH);
    }

    /**
     * Parses a {@code String timePeriod} into a {@code EventDayTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param timePeriod Valid String example: "1000-2000"
     * @throws ParseException if the given {@code timePeriod} format is incorrect
     */
    public static EventDayTime parseTimePeriod(String timePeriod) throws ParseException {
        requireNonNull(timePeriod);
        String trimmed = timePeriod.trim();
        if (!EventDayTime.isValidTime(trimmed)) {
            throw new ParseException(EventDayTime.MESSAGE_CONSTRAINTS);
        }
        try {
            String[] timeSplit = trimmed.split("-");
            LocalTime startTime = LocalTime.parse(timeSplit[0], EventDayTime.FORMATTER);
            LocalTime endTime = LocalTime.parse(timeSplit[1], EventDayTime.FORMATTER);
            return new EventDayTime(startTime, endTime);
        } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
            throw new ParseException(EventDayTime.MESSAGE_CONSTRAINTS);
        }

    }

    /**
     * Parses a {@code String manpowerNeeded} into a {@code EventManpowerNeeded}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventManpowerNeeded} is invalid.
     */
    public static EventManpowerNeeded parseManpowerNeeded(String manpowerNeeded) throws ParseException {
        requireNonNull(manpowerNeeded);
        String trimmed = manpowerNeeded.trim();
        if (!EventManpowerNeeded.isValidEventManpowerNeeded(trimmed)) {
            throw new ParseException(EventManpowerNeeded.MESSAGE_CONSTRAINTS);
        }
        return new EventManpowerNeeded(trimmed);
    }

    /**
     * Parses a {@code String eventDateTimeMap} into a {@code EventDateTimeMap}.
     * Leading and trailing whitespaces will be trimmed.
     * E.g. of String input: 03102019:1000-2000,05102019:1500-1700
     *
     * @throws ParseException if the given {@code EventDateTimeMap} is invalid.
     */
    public static EventDateTimeMap parseEventDateTimeMap(String eventDateTimeMap) throws ParseException {
        EventDateTimeMap map = new EventDateTimeMap();
        if (eventDateTimeMap.isEmpty()) {
            return map;
        }

        String trimmed = eventDateTimeMap.trim();
        try {
            String[] eachDateTime = trimmed.split(",");
            for (String dateTime : eachDateTime) {
                String[] dateTimeSplit = dateTime.split(":"); //[0] is date, [1] is time-period
                map.mapDateTime(parseDate(dateTimeSplit[0]), parseTimePeriod(dateTimeSplit[1]));
            }
        } catch (ArrayIndexOutOfBoundsException | ParseException e) {
            throw new ParseException(EventDateTimeMap.MESSAGE_CONSTRAINTS);
        }

        return map;
    }

    /**
     * Parses a {@code String manpowerNeeded} into a {@code Integer manpowerCount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given manpowerNeeded is invalid.
     */
    public static Integer parseManpowerToAllocate(String manpowerToAllocate) throws ParseException {
        if (manpowerToAllocate == null) {
            return null;
        }

        String trimmed = manpowerToAllocate.trim();
        Integer manpowerToAdd;
        try {
            manpowerToAdd = Integer.valueOf(trimmed);
            if (manpowerToAdd <= 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new ParseException("Number of employees to allocate must be a positive integer!");
        }

        return manpowerToAdd;
    }
}
