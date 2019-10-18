package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
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
import seedu.address.model.event.EventEndDate;
import seedu.address.model.event.EventHoursNeeded;
import seedu.address.model.event.EventId;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventStartDate;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        return new EmployeeGender(trimmedGender);
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
     * Parses a {@code String phone} into a {@code EmployeePhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static EmployeeId parseEmployeeId() {
        return new EmployeeId();
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
        if (!EmployeeName.isValidName(trimmedName)) {
            throw new ParseException(EmployeeName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * returns an EventId
     *
     */
    public static EventId parseEventId() {
        return new EventId();
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
     * Parses a {@code String hoursNeeded} into a {@code EventHoursNeeded}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventHoursNeeded} is invalid.
     */
    public static EventHoursNeeded parseHoursNeeded(String hoursNeeded) throws ParseException {
        requireNonNull(hoursNeeded);
        String trimmed = hoursNeeded.trim();
        if (!EventHoursNeeded.isValidEventHours(trimmed)) {
            throw new ParseException(EventHoursNeeded.MESSAGE_CONSTRAINTS);
        }
        return new EventHoursNeeded(trimmed);
    }

    /**
     * Parses a {@code String EventStartDate} into a {@code startDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventStartDate} is invalid.
     */
    public static EventStartDate parseStartDate(String startDate) throws ParseException {
        requireNonNull(startDate);
        String trimmed = startDate.trim();
        if (!EventStartDate.isValidStartDate(trimmed)) {
            throw new ParseException(EventStartDate.MESSAGE_CONSTRAINTS);
        }
        LocalDate newStartDate = LocalDate.parse(trimmed, FORMATTER);
        return new EventStartDate(newStartDate);
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
            throw new ParseException(EmployeeName.MESSAGE_CONSTRAINTS);
        }
        LocalDate newJoinDate = LocalDate.parse(trimmed, FORMATTER);
        return new EmployeeJoinDate(newJoinDate);
    }

    /**
     * Parses a {@code Strin
     g EventEndDate} into a {@code endDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code EventEndDate} is invalid.
     */
    public static EventEndDate parseEndDate(String endDate) throws ParseException {
        requireNonNull(endDate);
        String trimmed = endDate.trim();
        if (!EventEndDate.isValidEndDate(trimmed)) {
            throw new ParseException(EventEndDate.MESSAGE_CONSTRAINTS);
        }
        LocalDate newEndDate = LocalDate.parse(trimmed, FORMATTER);
        return new EventEndDate(newEndDate);
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


}
