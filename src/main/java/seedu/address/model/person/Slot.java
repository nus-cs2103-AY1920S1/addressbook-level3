package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an interview timeslot an {@code Interviewee} is allocated.
 */
public class Slot {

    public static final String STRING_FORMAT = "%s %s-%s";
    public static final String MESSAGE_CONSTRAINTS =
            "A slot should follow this format: " + String.format(STRING_FORMAT, "dd/mm/yyyy", "hh:mm", "hh:mm");
    public static final String DATETIME_PARSE_PATTERN = "dd/MM/yyyy HH:mm";
    private static final Pattern SEPARATION_REGEX =
            Pattern.compile("(?<date>\\d{2}/\\d{2}/\\d{4}) (?<slot1>\\d{2}:\\d{2})-(?<slot2>\\d{2}:\\d{2})");
    private static final DateTimeFormatter parseFormatter =
            DateTimeFormatter.ofPattern(DATETIME_PARSE_PATTERN, Locale.ENGLISH).withZone(ZoneId.systemDefault());

    public final String date;
    public final String start;
    public final String end;

    public Slot(String slot) {
        requireNonNull(slot);
        checkArgument(isValidSlot(slot), MESSAGE_CONSTRAINTS);
        final Matcher matcher = SEPARATION_REGEX.matcher(slot);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.date = matcher.group("date");
        this.start = matcher.group("slot1");
        this.end = matcher.group("slot2");
    }

    /**
     * Constructs a {@code Slot} from the enforced format.
     *
     * @param date the date of the slot.
     * @param start the starting time of the slot.
     * @param end  the ending time of the slot.
     */
    public Slot(String date, String start, String end) {
        requireAllNonNull(date, start, end);
        checkArgument(isValidSlot(String.format(STRING_FORMAT, date, start, end)), MESSAGE_CONSTRAINTS);
        this.date = date;
        this.start = start;
        this.end = end;
    }

    /**
     * Returns true if the given slot timing is in valid format and start occurs earlier than end.
     */
    public static boolean isValidSlot(String test) {
        try {
            // split the periods up
            final Matcher matcher = SEPARATION_REGEX.matcher(test);
            if (!matcher.matches()) {
                throw new ParseException(MESSAGE_CONSTRAINTS);
            }
            final String date = matcher.group("date");
            final String start = String.format("%s %s", date, matcher.group("slot1"));
            final String end = String.format("%s %s", date, matcher.group("slot2"));
            ZonedDateTime t1 = ZonedDateTime.parse(start, parseFormatter);
            ZonedDateTime t2 = ZonedDateTime.parse(end, parseFormatter);
            // the start must be earlier than the end
            if (t1.compareTo(t2) > 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, date, start, end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Slot // instanceof handles nulls
                && date.equals(((Slot) other).date)
                && start.equals(((Slot) other).start)
                && end.equals(((Slot) other).end)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, start, end);
    }

}
