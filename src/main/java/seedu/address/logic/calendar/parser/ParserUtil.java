package seedu.address.logic.calendar.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.tag.TaskTag;
import seedu.address.model.calendar.task.TaskDay;
import seedu.address.model.calendar.task.TaskDeadline;
import seedu.address.model.calendar.task.TaskDescription;
import seedu.address.model.calendar.task.TaskTime;
import seedu.address.model.calendar.task.TaskTitle;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_WEEK = "Week index to go to must be an integer from 0 to 14";
    public static final String MESSAGE_INVALID_SORT_TYPE = "Sort type must be either 'deadline' or 'title'";

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
     * Parses {@code weekNumber} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static int parseWeek(String weekNumber) throws ParseException {
        String trimmedWeekNumber = weekNumber.trim();
        try {
            int week = Integer.parseInt(trimmedWeekNumber);
            if (week < 0 || week > 14) {
                throw new ParseException(MESSAGE_INVALID_WEEK);
            }
            return week;
        } catch (ParseException | NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_WEEK);
        }
    }

    /**
     * Parses {@code sortType} into an {@code String} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified sortType is invalid.
     */
    public static String parseSortType(String sortType) throws ParseException {
        String trimmedSortType = sortType.trim();
        if (!trimmedSortType.equals("deadline") && !trimmedSortType.equals("title")
            && !trimmedSortType.equals("time")) {
            throw new ParseException(MESSAGE_INVALID_SORT_TYPE);
        }
        return trimmedSortType;
    }

    /**
     * Parses a {@code String name} into a {@code TaskTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TaskTitle parseTitle(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!TaskTitle.isValidTitle(trimmedName)) {
            throw new ParseException(TaskTitle.MESSAGE_CONSTRAINTS);
        }
        return new TaskTitle(trimmedName);
    }

    /**
     * Parses a {@code String day} into a {@code TaskDay}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code day} is invalid.
     */
    public static TaskDay parseDay(String day) throws ParseException {
        requireNonNull(day);
        String trimmedDay = day.trim();
        if (!TaskDay.isValidDay(trimmedDay)) {
            throw new ParseException(TaskDay.MESSAGE_CONSTRAINTS);
        }
        return new TaskDay(trimmedDay);
    }

    /**
     * Parses a {@code String time} into an {@code TaskTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static TaskTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedAddress = time.trim();
        if (!TaskTime.isValidTime(trimmedAddress)) {
            throw new ParseException(TaskTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskTime(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code TaskDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static TaskDescription parseDescription(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!TaskDescription.isValidDescription(trimmedEmail)) {
            throw new ParseException(TaskDescription.MESSAGE_CONSTRAINTS);
        }
        return new TaskDescription(trimmedEmail);
    }

    /**
     * Parses a {@code String deadline} into an {@code TaskDeadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static TaskDeadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!TaskDeadline.isValidDeadline(trimmedDeadline)) {
            throw new ParseException(TaskDeadline.MESSAGE_CONSTRAINTS);
        }
        return new TaskDeadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String tag} into a {@code TaskTag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static TaskTag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!TaskTag.isValidTagName(trimmedTag)) {
            throw new ParseException(TaskTag.MESSAGE_CONSTRAINTS);
        }
        return new TaskTag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<TaskTag>}.
     */
    public static Set<TaskTag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<TaskTag> taskTagSet = new HashSet<>();
        for (String tagName : tags) {
            taskTagSet.add(parseTag(tagName));
        }
        return taskTagSet;
    }
}
