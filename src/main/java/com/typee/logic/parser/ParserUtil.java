package com.typee.logic.parser;

import static java.util.Objects.requireNonNull;

import com.typee.commons.core.index.Index;
import com.typee.commons.util.StringUtil;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementType;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Priority;
import com.typee.model.person.Name;
import java.time.LocalDateTime;
import java.time.Month;

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

    public static Name parseNameDeterministic(String name) {
        try {
            return parseName(name);
        } catch (ParseException e) {
            return null;
        }
    }

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

    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        return new Location(location);
    }

    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedString = priority.trim();
        try {
            Priority parsedPriority = Priority.of(trimmedString);
            return parsedPriority;
        } catch (IllegalArgumentException e) {
            throw new ParseException(Priority.getMessageConstraints());
        }
    }

    public static LocalDateTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        int year = Integer.parseInt(time.substring(6, 10));
        Month month = Month.of(Integer.parseInt(time.substring(3, 5)));
        int day = Integer.parseInt(time.substring(0, 2));
        int hours = Integer.parseInt(time.substring(11, 13));
        int minutes = Integer.parseInt(time.substring(13, 15));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hours, minutes);
        return localDateTime;
    }

}
