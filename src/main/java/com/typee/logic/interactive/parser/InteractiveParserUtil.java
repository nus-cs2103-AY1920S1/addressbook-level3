package com.typee.logic.interactive.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.typee.commons.core.index.Index;
import com.typee.commons.util.StringUtil;
import com.typee.logic.interactive.parser.exceptions.ParseException;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import com.typee.model.person.Person;
import com.typee.model.util.EngagementComparator;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * The methods should be invoked only after input validation, unless specified otherwise.
 */
public class InteractiveParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String FORMAT_DATE_TIME = "dd/MM/uuuu/HHmm";
    private static final String MESSAGE_YEAR_ZERO = "The year zero is not allowed!";

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
     * Parses a {@code String name} into a {@code Name} in a deterministic manner.
     * Returns null if the name is invalid.
     * This method should ONLY be used with streams to avoid exception handling.
     *
     * @param name name of the {@code Person}.
     * @return a {@code Name} object representing the name.
     */
    private static Name parseNameDeterministic(String name) {
        try {
            return parseName(name);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Parses a {@code String engagementType} representing the type of an engagement.
     * Returns the corresponding {@code EngagementType}.
     *
     * @param engagementType {@code String} representing the type of engagement.
     * @return corresponding {@code EngagementType}.
     */
    public static EngagementType parseType(String engagementType) {
        requireNonNull(engagementType);
        String trimmedType = engagementType.trim();
        return EngagementType.of(trimmedType);
    }

    /**
     * Parses a {@code String location} and returns a {@code Location} object representing it.
     *
     * @param location location.
     * @return a corresponding {@code Location} object.
     */
    public static Location parseLocation(String location) {
        requireNonNull(location);
        return new Location(location);
    }

    /**
     * Parses a {@code String priority} and returns the corresponding {@code Priority}.
     *
     * @param priority {@code String} representing priority.
     * @return the corresponding {@code Priority}.
     */
    public static Priority parsePriority(String priority) {
        requireNonNull(priority);
        String trimmedString = priority.trim();
        Priority parsedPriority;
        if (trimmedString.isBlank()) {
            parsedPriority = Priority.NONE;
        } else {
            parsedPriority = Priority.of(trimmedString);
        }
        return parsedPriority;
    }

    /**
     * Parses a {@code String order} and returns a {@code EngagementComparator}.
     *
     * @param order the sorting order.
     * @return the PersonPropertyComparator representing the comparator for that property.
     * @throws ParseException if the given {@code personProperty} is invalid.
     */
    public static EngagementComparator parseComparator(String order) throws ParseException {
        requireNonNull(order);

        try {
            return EngagementComparator.getComparator(order);
        } catch (IllegalArgumentException e) {
            throw new ParseException(EngagementComparator.MESSAGE_PROPERTY_CONSTRAINTS);
        }
    }

    /**
     * Parses the {@code String time} input by the user.
     * Returns a {@code LocalDateTime} object representing the time.
     *
     * @param time time.
     * @return a {@code LocalDateTime} object.
     */
    public static LocalDateTime parseTime(String time) {
        requireNonNull(time);
        LocalDateTime localDateTime = convertStringToDateTime(time);
        return localDateTime;

    }

    /**
     * Converts a {@code String} to its corresponding {@code LocalDateTime} object.
     *
     * @param time time.
     * @return the corresponding {@code LocalDateTime} object.
     */
    private static LocalDateTime convertStringToDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy/HHmm");
        return LocalDateTime.parse(time, formatter);
    }

    /**
     * Validates and returns the description of an engagement.
     *
     * @param description Description of an engagement.
     * @return the description if its valid.
     */
    public static String parseDescription(String description) {
        return description;
    }

    /**
     * Parses a {@code String} representing a list of attendees into an {@code AttendeeList}.
     *
     * @param attendees String representing list of attendees.
     * @return corresponding {@code AttendeeList}.
     */
    public static AttendeeList parseAttendees(String attendees) {
        List<Person> attendeesList = Arrays.stream(attendees.split("\\|"))
                .map(String::trim)
                .map(name -> new Person(InteractiveParserUtil.parseNameDeterministic(name)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new AttendeeList(attendeesList);
    }

    /**
     * Returns true if the entered {@code String} represents a valid date-time.
     *
     * @param dateTime {@code String} that represents a date-time.
     * @return true if valid.
     */
    public static boolean isValidDateTime(String dateTime) {
        try {
            makeDateTimeFromPattern(dateTime);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns a {@code LocalDateTime} object that represents the entered {@code String}.
     *
     * @param dateTime {@code String} that represents a date-time value.
     * @return {@code LocalDateTime}.
     * @throws DateTimeException If the {@code String} is an invalid date-time value.
     */
    private static LocalDateTime makeDateTimeFromPattern(String dateTime) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME)
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        int year = localDateTime.getYear();
        enforceNonZeroYear(year);
        return localDateTime;
    }

    /**
     * Enforces that the specified year is non-zero.
     *
     * @param year The specified year.
     * @throws DateTimeException If the specified year is zero.
     */
    private static void enforceNonZeroYear(int year) throws DateTimeException {
        if (year == 0) {
            throw new DateTimeException(MESSAGE_YEAR_ZERO);
        }
    }

    /**
     * Returns true if the entered strings representing start and end date-times are valid.
     * The strings are valid if end time occurs after the start time.
     * This method should only be called if the {@code Strings} represent valid date-time values.
     *
     * @param startDate {@code String} that represents the start date-time.
     * @param endDate {@code String} that represents the end date-time.
     * @return true if the time-slot is valid.
     */
    public static boolean isValidTimeSlot(String startDate, String endDate) {
        LocalDateTime start = makeDateTimeFromPattern(startDate);
        LocalDateTime end = makeDateTimeFromPattern(endDate);
        return start.isBefore(end);
    }

    /**
     * Parses the entered {@code String} that represents a date that conforms to the entered pattern.
     *
     * @param date Date.
     * @param pattern Date-pattern.
     * @return {@code LocalDate} representing the date.
     * @throws DateTimeException If the specified date's year is 0000.
     */
    public static LocalDate parseLocalDate(String date, String pattern) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
        LocalDate localDate = LocalDate.parse(date, formatter);
        enforceNonZeroYear(localDate.getYear());
        return localDate;
    }
}
