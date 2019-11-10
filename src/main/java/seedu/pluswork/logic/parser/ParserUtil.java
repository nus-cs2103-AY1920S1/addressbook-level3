package seedu.pluswork.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.commons.util.StringUtil;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.calendar.FilePath;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PRICE = "Invalid Price entered.";
    public static final String MESSAGE_INVALID_DURATION = "Duration(hours) is not a non-zero unsigned integer.";

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
     * Parses a {@code String invname} into a {@code InvName}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static InvName parseInvName(String invname) throws ParseException {
        requireNonNull(invname);
        String trimmedName = invname.trim();
        if (!InvName.isValidName(trimmedName)) {
            throw new ParseException(InvName.MESSAGE_CONSTRAINTS);
        }
        return new InvName(trimmedName);
    }

    /**
     * Parses {@code price} into an {@code Price} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Price parsePrice(String price) throws ParseException {
        String trimmedIndex = price.trim();
        if (!StringUtil.isPositiveDouble(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_PRICE);
        }
        return new Price(Double.parseDouble(trimmedIndex));
    }

    /**
     * Parses a {@code String taskStatus} into a {@code TaskStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static TaskStatus parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedAndUpperCaseStatus = status.trim().toUpperCase();
        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.valueOf(trimmedAndUpperCaseStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException(TaskStatus.MESSAGE_CONSTRAINTS);
        }
        return taskStatus;
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

    //MEMBER RELATED

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static MemberName parseMemberName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!MemberName.isValidMemberName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new MemberName(trimmedName);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static MemberId parseMemberId(String memberId) throws ParseException {
        requireNonNull(memberId);
        String trimmedId = memberId.trim();

        if (!MemberId.isValidId(trimmedId)) {
            throw new ParseException(MemberId.MESSAGE_CONSTRAINTS);
        }

        return new MemberId(trimmedId);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static String parseMemberImage(String url) throws ParseException {
        requireNonNull(url);
        String trimmedUrl = url.trim();

        return trimmedUrl;
    }

    /**
     * Parses {@code dateTime} into an {@code LocalDateTime} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws DateTimeParseException if the input string is not in the valid format.
     */
    public static LocalDateTime parseDate(String dateTime) throws DateTimeParseException {
        requireNonNull(dateTime);
        String trimmedDate = dateTime.trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(trimmedDate, formatter);
    }

    /**
     * Parses {@code filePath} into an {@code filePath} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws DateTimeParseException if the input string is not in the valid format.
     */
    public static FilePath parseFilePath(String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();

        if (!FilePath.isValidFilePath(filePath)) {
            throw new ParseException(FilePath.MESSAGE_CONSTRAINTS);
        }

        return new FilePath(trimmedFilePath);
    }

    /**
     * Parses {@code filePath} into an {@code filePath} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws DateTimeParseException if the input string is not in the valid format.
     */
    public static Calendar parseCalendar(String calendarStorageFormat) throws ParseException {
        requireNonNull(calendarStorageFormat);
        try {
            InputStream inputStream = new ByteArrayInputStream(calendarStorageFormat.getBytes());
            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar calendar = builder.build(inputStream);
            return calendar;
        } catch (IOException | ParserException e) {
            //Error when building Calendar with incorrect input stream
            throw new ParseException("Error occurred when parsing .ics file");
        }
    }

    /**
     * Parses {@code theme} into a {@code Theme} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code theme}'s code is invalid.
     */
    public static Theme parseTheme(String theme) throws ParseException {
        requireNonNull(theme);
        String trimmedAndUpperCaseTheme = theme.trim().toUpperCase();
        Theme appTheme;

        try {
            appTheme = Theme.valueOf(trimmedAndUpperCaseTheme);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Theme.MESSAGE_CONSTRAINTS);
        }

        return appTheme;
    }

    /**
     * Parses {@code clock} into a {@code ClockFormat} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the given {@code clock}'s format is invalid.
     */
    public static ClockFormat parseClock(String clock) throws ParseException {
        requireNonNull(clock);
        String trimmedAndUpperCaseClock = clock.trim().toUpperCase();
        ClockFormat clockFormat;

        try {
            clockFormat = ClockFormat.valueOf(trimmedAndUpperCaseClock);
        } catch (IllegalArgumentException e) {
            throw new ParseException(ClockFormat.MESSAGE_CONSTRAINTS);
        }

        return clockFormat;
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Duration parseHours(String hoursString) throws ParseException {
        String hoursStringTrimmed = hoursString.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(hoursStringTrimmed)) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
        return Duration.ofHours(Integer.parseInt(hoursStringTrimmed));
    }
}
