package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.ItemDescription;
import seedu.address.commons.core.item.Priority;
import seedu.address.commons.core.item.Reminder;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

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
     * Parses {@code description} into a {@code ItemDescription} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @param description given for the item.
     * @return a new item description that is processed
     * @throws ParseException if the description is invalid (empty description).
     */
    public static ItemDescription parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!ItemDescription.isValidItemDescription(trimmedDescription)) {
            throw new ParseException(ItemDescription.MESSAGE_CONSTRAINTS);
        }
        return new ItemDescription(trimmedDescription);
    }

    /**
     * Parse the {@code dateTime} into a {@code Optional<Event>} and returns it.
     * Converts a String to a LocalDateTime object and creates a new event with it.
     * @param dateTime representing the deadline of the event
     * @return Optional.of(event) if the Event created is valid, Optional.empty() otherwise
     * @throws ParseException if the format of deadline provided is incorrect
     */
    public static Optional<Event> parseDateTime(String dateTime) throws ParseException {
        if (dateTime == null) {
            return Optional.empty();
        }

        String trimmedDateTime = dateTime.trim();
        LocalDateTime formattedDateTime;

        try {
            formattedDateTime = getFormattedDateTime(trimmedDateTime); //LocalDateTime.parse(trimmedDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Date Time format given is incorrect. "
                    + "Please follow this format: \"-d 2019-09-25T23:59:50.63\""
                    + "or \"-d 25/09/2019 2359\"");
        }

        Event newEvent = new Event(formattedDateTime, null, null);

        return Optional.of(newEvent);
    }

    /**
     * Parse the {@code reminder} into a {@code Optional<Reminder>} and returns it.
     * Converts the string time into a LocalDateTime object and create a Reminder with it.
     * @param reminder representing the time of the reminder
     * @return Optional.of(reminder) if the reminder created is valid, Optional.empty() otherwise
     * @throws ParseException if the format of the reminder time is incorrect
     */
    public static Optional<Reminder> parseReminder(String reminder) throws ParseException {
        if (reminder == null) {
            return Optional.empty();
        }

        String trimmedDateTime = reminder.trim();
        LocalDateTime formattedDateTime;

        try {
            formattedDateTime = getFormattedDateTime(trimmedDateTime); //LocalDateTime.parse(trimmedDateTime);
        } catch (DateTimeParseException e) {
            throw new ParseException("Date Time format given is incorrect. "
                    + "Please follow this format: \"-r 2019-09-25T23:59:50.63\""
                    + "or \"-r 25/09/2019 2359\"");
        }

        Reminder newReminder = new Reminder(formattedDateTime);
        return Optional.of(newReminder);
    }

    /**
     * Parse the {@code priority} into a {@code Optional<Priority>} and returns it.
     * Converts the string of priority into an enumeration priority object, is case-insensitive.
     * @param priority of the task or event
     * @return Optional.of(priority) if the priority is valid, Optional.empty() otherwise
     * @throws ParseException if the priority given is not high/medium/low
     */
    public static Optional<Priority> parsePriority(String priority) throws ParseException {
        if (priority == null) {
            return Optional.empty();
        }

        String trimmedPriority = priority.trim();
        Priority processedPriority;

        if (trimmedPriority.equalsIgnoreCase("HIGH")) {
            processedPriority = Priority.HIGH;
        } else if (trimmedPriority.equalsIgnoreCase("MEDIUM")) {
            processedPriority = Priority.MEDIUM;
        } else if (trimmedPriority.equalsIgnoreCase("LOW")) {
            processedPriority = Priority.LOW;
        } else {
            throw new ParseException("Priority format given is incorrect. "
                    + "Please follow this format \"-p High\"");
        }

        return Optional.of(processedPriority); //maybe use enum here
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

    private static LocalDateTime getFormattedDateTime(String stringDateTime) throws DateTimeParseException {
        LocalDateTime processedDateTime;

        try {
            processedDateTime = LocalDateTime.parse(stringDateTime);
        } catch (DateTimeParseException e1) {
            try {
                processedDateTime = parseUsingFormatter(stringDateTime);
            }
            catch (Exception e2) {
                throw new DateTimeParseException(e2.getMessage(), stringDateTime, 0);
            }
        }

        return processedDateTime;
    }

    private static LocalDateTime parseUsingFormatter(String stringDateTime) {
        String[] splitTime = stringDateTime.split(" ");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate processedDate = LocalDate.parse(splitTime[0], dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime processedTime = LocalTime.parse(splitTime[1], timeFormatter);

        LocalDateTime processedDateTime = LocalDateTime.of(processedDate, processedTime);
        return processedDateTime;
    }
}
