package seedu.jarvis.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.person.Address;
import seedu.jarvis.model.address.person.Email;
import seedu.jarvis.model.address.person.Name;
import seedu.jarvis.model.address.person.Phone;
import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DATE = "Date is invalid. Please follow the format: dd/mm/yyyy.";
    public static final String MESSAGE_INVALID_TASK_TYPE = "Task type is invalid. Valid task types are: 'todo', 'event'"
                                                            + "and 'deadline' only.";

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

        if (priority.equals("high")) {
            return Priority.HIGH;
        } else if (priority.equals("med")) {
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
            case "daily":
                return Frequency.DAILY;
            case "weekly":
                return Frequency.WEEKLY;
            case "monthly":
                return Frequency.MONTHLY;
            default:
                return Frequency.YEARLY;
        }
    }

    /**
     * Parses a {@code String date} into a {@code Calendar date} for tasks
     * @param date the date to be parsed
     * @return an array of Calendars to be added into the Task objects
     */
    public static Calendar[] parseDate(String date) throws ParseException {
        requireNonNull(date);
        Calendar[] res = new Calendar[2];
        try {
            String trimmedDate = date.trim();
            String[] splitDate = date.split("//");
            int count = 0;
            for (String d : splitDate) {
                String[] dateAsInts = d.split("/");
                int day = Integer.parseInt(dateAsInts[0]);
                int month = Integer.parseInt(dateAsInts[1]);
                int year = Integer.parseInt(dateAsInts[2]);
                Calendar c = Calendar.getInstance();
                c.set(day, month, year);
                res[count] = c;
                count++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }

        return res;
    }

    public static Task buildTask(String taskType, String taskDes, Calendar[] dates, Priority priority,
                                 Frequency frequency, Set<Tag> tagList) throws ParseException {
        Task t;

        if (taskType.equals("event")) {
            t = new Event(taskDes, dates[0], dates[1]);
        } else if (taskType.equals("deadline")) {
            t = new Deadline(taskDes, dates[0]);
        } else if (taskType.equals("todo")) {
            t = new Todo(taskDes);
        } else {
            throw new ParseException(MESSAGE_INVALID_TASK_TYPE);
        }

        t.addPriority(priority);
        t.addFrequency(frequency);
        for (Tag tag : tagList) {
            t.addTag(tag);
        }

        return t;
    }


}
