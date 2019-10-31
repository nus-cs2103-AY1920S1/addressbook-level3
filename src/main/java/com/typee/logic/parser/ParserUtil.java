package com.typee.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.typee.commons.core.index.Index;
import com.typee.commons.util.StringUtil;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import com.typee.model.person.Person;
import com.typee.model.util.EngagementComparator;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_DATE_STRING = "Please stick to the DD/MM/YYYY format.";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "%s is not a valid date "
            + "in the DD/MM/YYYY/HHMM format.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_TIME_STRING = "Please stick to the DD/MM/YYYY/HHMM format.";
    public static final String MESSAGE_INVALID_TIME_FORMAT = "%s is not a valid date-time "
            + "in the DD/MM/YYYY/HHMM format.";
    public static final String MESSAGE_INVALID_DESCRIPTION = "The description cannot be empty.";
    public static final String MESSAGE_INVALID_ATTENDEES = "There can't be no attendees.";

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
    public static Name parseNameDeterministic(String name) {
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
     * @throws ParseException if the given {@code String engagementType} is invalid.
     */
    public static EngagementType parseType(String engagementType) throws ParseException {
        requireNonNull(engagementType);
        String trimmedType = engagementType.trim();
        try {
            EngagementType type = EngagementType.of(trimmedType);
            return type;
        } catch (IllegalArgumentException e) {
            throw new ParseException(EngagementType.getMessageConstraints());
        }
    }

    /**
     * Parses a {@code String location} and returns a {@code Location} object representing it.
     *
     * @param location location.
     * @return a corresponding {@code Location} object.
     * @throws ParseException if the {@code String location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        if (!Location.isValid(location)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(location);
    }

    /**
     * Parses a {@code String priority} and returns the corresponding {@code Priority}.
     *
     * @param priority {@code String} representing priority.
     * @return the corresponding {@code Priority}.
     * @throws ParseException if the {@code String priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedString = priority.trim();
        try {
            Priority parsedPriority;
            if (trimmedString.isBlank()) {
                parsedPriority = Priority.NONE;
            } else {
                parsedPriority = Priority.of(trimmedString);
            }
            return parsedPriority;
        } catch (IllegalArgumentException e) {
            throw new ParseException(Priority.getMessageConstraints());
        }
    }

    /**
     * Parses a {@code String order} and returns a {@code EngagementComparator}.
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
     * @throws ParseException if the {@code String time} is invalid.
     */
    public static LocalDateTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        try {
            LocalDateTime localDateTime = convertStringToDateTime(time);
            return localDateTime;
        } catch (DateTimeException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_TIME_FORMAT, time));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_TIME_STRING);
        }
    }

    /**
     * Converts a {@code String} to its corresponding {@code LocalDateTime} object.
     *
     * @param time time.
     * @return the corresponding {@code LocalDateTime} object.
     */
    private static LocalDateTime convertStringToDateTime(String time) {
        if (time.length() > 15) {
            throw new StringIndexOutOfBoundsException();
        }
        int year = Integer.parseInt(time.substring(6, 10));
        Month month = Month.of(Integer.parseInt(time.substring(3, 5)));
        int day = Integer.parseInt(time.substring(0, 2));
        int hours = Integer.parseInt(time.substring(11, 13));
        int minutes = Integer.parseInt(time.substring(13, 15));
        return LocalDateTime.of(year, month, day, hours, minutes);
    }

    /**
     * Parses the {@code String date} input by the user.
     * Returns a {@code LocalDate} object representing the date.
     * @param date The {@code String date} input by the user.
     * @return A {@code LocalDate} object.
     * @throws ParseException
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        try {
            LocalDate localDate = convertStringToDate(date);
            return localDate;
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DATE_STRING);
        } catch (DateTimeException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT, date));
        }
    }

    /**
     * Converts a {@code String} to its corresponding {@code LocalDate} object.
     * @param date date.
     * @return the corresponding {@code LocalDate} object.
     */
    private static LocalDate convertStringToDate(String date) {
        if (date.length() != 10) {
            throw new StringIndexOutOfBoundsException();
        }
        int year = Integer.parseInt(date.substring(6, 10));
        Month month = Month.of(Integer.parseInt(date.substring(3, 5)));
        int day = Integer.parseInt(date.substring(0, 2));
        return LocalDate.of(year, month, day);
    }

    /**
     * Validates and returns the description of an engagement.
     * @param description the description of an engagement.
     * @return the description if its valid.
     * @throws ParseException if the description is blank.
     */
    public static String parseDescription(String description) throws ParseException {
        if (description.isBlank()) {
            throw new ParseException(MESSAGE_INVALID_DESCRIPTION);
        }
        return description;
    }

    /**
     * Parses a {@code String} representing a list of attendees into an {@code AttendeeList}.
     *
     * @param attendees string representing list of attendees.
     * @return corresponding {@code AttendeeList}.
     */
    public static AttendeeList parseAttendees(String attendees) throws ParseException {
        if (attendees.isBlank()) {
            throw new ParseException(MESSAGE_INVALID_ATTENDEES);
        }
        List<Person> attendeesList = Arrays.stream(attendees.split(","))
                .map(name -> name.trim())
                .map(name -> new Person(ParserUtil.parseNameDeterministic(name)))
                .filter(name -> name != null)
                .collect(Collectors.toList());
        return new AttendeeList(attendeesList);
    }

}
