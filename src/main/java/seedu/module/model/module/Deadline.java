package seedu.module.model.module;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.module.model.module.exceptions.DeadlineInvalidPriorityException;
import seedu.module.model.module.exceptions.DeadlineMarkException;
import seedu.module.model.module.exceptions.DeadlineParseException;

/**
 * Represents Priority tags in Enum
 */
enum Priority {
    HIGH, MEDIUM, LOW;
}

/**
 * Represents a Module's deadline in the module book.
 * Guarantees: immutable; is always valid
 */
public class Deadline {
    public static final String MESSAGE_CONSTRAINTS = "Not a valid Deadline";
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd MMM yyyy, hh:mm aaa");
    public static final SimpleDateFormat PARSER = new SimpleDateFormat("dd/MM/yyyy HHmm");
    protected Date date;

    private String description;
    private String time;
    private boolean isDone;
    private boolean isInProgress;
    private String tag;

    public Deadline (String description, String time, String tag) throws DeadlineParseException {
        requireNonNull(description);
        this.description = description;
        this.time = time;
        this.date = parseDate(time);
        try {
            isValidPriority(tag);
            this.tag = tag;
        } catch (IllegalArgumentException ex) {
            throw new DeadlineInvalidPriorityException("invalid priority entered. Priority can be HIGH, "
                    + "MEDIUM or LOW.");
        }
    }

    /**
     * Validates priority tag.
     * @param tag
     */
    public void isValidPriority(String tag) {
        Priority.valueOf(tag);
    }

    /**
     * Marks the deadline task as done.
     */
    public void markAsDone() {
        isDone = true;
        isInProgress = false;
    }

    /**
     * Marks the deadline task as in progress.
     */
    public void markAsInProgress() {
        isInProgress = true;
        isDone = false;
    }

    /**
     * Marks the deadline task as undone.
     * @throws DeadlineMarkException when task is already undone.
     */
    public void markAsUndone() throws DeadlineMarkException {
        if (!isInProgress && !isDone) {
            throw new DeadlineMarkException("Deadline task already undone!");
        } else {
            isInProgress = false;
            isDone = false;
        }
    }

    public String getStatus() {
        if (isDone) {
            return "" + "\u2713";
        } else if (isInProgress) {
            return "-";
        } else {
            return " ";
        }
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }

    public String getTag() {
        return tag;
    }

    public void setValue(String newValue) {
        this.description = newValue;
    }

    public void editDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Edits date and time of deadline object.
     * @param newTime new edited time.
     */
    public void editTime(String newTime) throws DeadlineParseException {
        this.time = newTime;
        this.date = parseDate(time);
    }

    /**
     * Parses string date into Date object according to our format.
     *
     * @param s Input string to be parsed.
     * @return The parsed Date object.
     */
    protected static Date parseDate(String s) throws DeadlineParseException {
        try {
            return PARSER.parse(s);
        } catch (IllegalArgumentException | java.text.ParseException e) {
            throw new DeadlineParseException("Date and time not in dd/MM/yyyy HHmm format");
        }
    }

    /**
     * Converts Date object into string of our format.
     *
     * @param d The Date object in question.
     * @return The string representation in our format.
     */
    protected static String stringifyDate(Date d) {
        return FORMATTER.format(d);
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + description + ", due by : " + stringifyDate(date) + "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && description.equals(((Deadline) other).description))
                && time.equals(((Deadline) other).time); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
