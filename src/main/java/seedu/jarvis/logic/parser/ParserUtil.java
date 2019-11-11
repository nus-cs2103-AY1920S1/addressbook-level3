package seedu.jarvis.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.planner.enums.Frequency.FREQ_DAILY;
import static seedu.jarvis.model.planner.enums.Frequency.FREQ_MONTHLY;
import static seedu.jarvis.model.planner.enums.Frequency.FREQ_WEEKLY;
import static seedu.jarvis.model.planner.enums.Priority.PRIORITY_HIGH;
import static seedu.jarvis.model.planner.enums.Priority.PRIORITY_MED;
import static seedu.jarvis.model.planner.tasks.Task.DEADLINE;
import static seedu.jarvis.model.planner.tasks.Task.EVENT;
import static seedu.jarvis.model.planner.tasks.Task.TODO;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    //common
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    //history
    public static final String MESSAGE_INVALID_NUMBER = "Number is not a non-zero unsigned integer.";

    //finance
    public static final String MONEY_MESSAGE_CONSTRAINTS = "Money spent cannot be equal to or less than 0.";

    //planner
    public static final String MESSAGE_INVALID_DATE = "Date is invalid. Please follow the format: d/m/yyyy.";
    public static final String MESSAGE_INVALID_TASK_TYPE = "Task type is invalid. Valid task types are: 'todo', 'event'"
                                                            + "and 'deadline' only.";
    public static final String MESSAGE_MULTIPLE_SAME_PREFIX = "Invalid command format. Only one instance of each "
                                                                + "prefix is allowed.";
    public static final String MESSAGE_MISSING_ESSENTIAL_ATTRIBUTES = "Missing task type or task description. "
                                                                + "Enter t/TASK-TYPE des/TASK-DESCRIPTION to continue.";
    public static final String MESSAGE_EMPTY_TASK_DESCRIPTION = "Task description cannot be blank";
    public static final String MESSAGE_WRONG_ORDER_DATE = "Start date for Event cannot be after end date";

    /**
     * Parses {@code number} into an integer and returns it. Leading and trailing whitespaces will be trimmed.
     * The integer must be positive and non-zero. If {@code number} is an empty string, 1 is returned.
     *
     * @param number {@code String} to be parsed into a number.
     * @return Positive integer.
     * @throws ParseException If the specified number is not a positive integer or zero.
     */
    public static int parseNonZeroUnsignedInteger(String number) throws ParseException {
        String trimmedNumber = number.trim();
        if (!trimmedNumber.isEmpty() && !StringUtil.isNonZeroUnsignedInteger(trimmedNumber)) {
            throw new ParseException(MESSAGE_INVALID_NUMBER);
        }
        return trimmedNumber.isEmpty() ? 1 : Integer.parseInt(trimmedNumber);
    }

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
     * Parses {@code String taskDes} to ensure it is a valid Task description
     * @param taskDes the task description provided by the user
     * @return a trimmed task description
     * @throws ParseException if Task Description is blank
     */
    public static String parseTaskDes(String taskDes) throws ParseException {
        String trimmedDes = taskDes.trim();
        if (trimmedDes.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_TASK_DESCRIPTION);
        }

        return trimmedDes;
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * @return the priority level of the task
     * @throws ParseException if the given {@code Priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        if (priority.equals(PRIORITY_HIGH)) {
            return Priority.HIGH;
        } else if (priority.equals(PRIORITY_MED)) {
            return Priority.MED;
        } else {
            return Priority.LOW;
        }
    }

    /**
     * Parses a {@code String frequency} into a {@code Frequency}.
     * @return the frequency level of the task
     * @throws ParseException if the given {@code Frequency} is invalid.
     */
    public static Frequency parseFrequency(String frequency) throws ParseException {
        requireNonNull(frequency);
        String trimmedFrequency = frequency.trim();
        if (!Frequency.isValidFrequency(trimmedFrequency)) {
            throw new ParseException(Frequency.MESSAGE_CONSTRAINTS);
        }

        switch (frequency) {
        case FREQ_DAILY:
            return Frequency.DAILY;
        case FREQ_WEEKLY:
            return Frequency.WEEKLY;
        case FREQ_MONTHLY:
            return Frequency.MONTHLY;
        default:
            return Frequency.YEARLY;
        }
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate} for tasks
     * @param date the date to be parsed
     * @return an array of Calendars to be added into the Task objects
     */
    public static LocalDate[] parseDate(String date) throws ParseException {
        requireNonNull(date);
        LocalDate[] res = new LocalDate[2];
        try {
            String trimmedDate = date.trim();
            String[] splitDate = trimmedDate.split("//");
            int count = 0;
            for (String d : splitDate) {
                LocalDate formattedDate = LocalDate.parse(d, Task.getDateFormat());
                res[count] = formattedDate;

                if (!formattedDate.isLeapYear()
                    && formattedDate.getMonth() == Month.FEBRUARY
                    && Integer.parseInt(d.split("/")[0]) == 29) {

                    throw new ParseException(MESSAGE_INVALID_DATE);
                }

                count++;
            }

            if (splitDate.length == 2) {
                LocalDate start = LocalDate.parse(splitDate[0], Task.getDateFormat());
                LocalDate end = LocalDate.parse(splitDate[1], Task.getDateFormat());
                if (start.compareTo(end) > 0) {
                    throw new ParseException(MESSAGE_WRONG_ORDER_DATE);
                }
            }

        } catch (DateTimeParseException | NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }

        return res;
    }

    /**
     * Builds a task for AddTaskCommandParser
     * @param taskType the type of task
     * @param taskDes description of the task
     * @param dates dates of the task, if any
     * @return returns a task
     * @throws ParseException if task type input by the user is wrong
     */
    public static Task buildTask(String taskType, String taskDes, LocalDate[] dates) throws ParseException {
        Task t;
        if (taskType.equals(EVENT)) {
            t = new Event(taskDes, dates[0], dates[1]);
        } else if (taskType.equals(DEADLINE)) {
            t = new Deadline(taskDes, dates[0]);
        } else if (taskType.equals(TODO)) {
            t = new Todo(taskDes);
        } else {
            throw new ParseException(MESSAGE_INVALID_TASK_TYPE);
        }
        return t;
    }
}
