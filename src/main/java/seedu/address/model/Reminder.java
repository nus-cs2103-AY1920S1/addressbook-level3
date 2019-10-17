package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Reminder object with description and dates remaining
 */
public class Reminder {

    public static final String MESSAGE_CONSTRAINTS = "Remaining days should be in integer format";
    public static final String VALIDATION_REGEX = "^[0-9]*$";

    // Temporary
    private static ArrayList<Reminder> reminderTable = new ArrayList<>();

    private String description;
    private int days;

    public Reminder(String description, int days) {
        requireNonNull(description);
        requireNonNull(days);
        this.description = description;
        this.days = days;
    }

    // Temporary
    public static String getReminders() {
        String tmp = "";
        for (Reminder r : reminderTable) {
            tmp += r.description + " | " + r.days + " days left\n";
        }
        return tmp;
    }

    // Temporary
    public static void addReminder(Reminder reminder) {
        reminderTable.add(reminder);
    }

    public String getDescription() {
        return this.description;
    }

    public int getDays() {
        return this.days;
    }

    @Override
    public String toString() {
        return description;
    }

    /**
     * Returns true if a given string is a valid integer for days remaining.
     */
    public static boolean isValidDaysRemaining(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reminder // instanceof handles nulls
                && description.equals(((Reminder) other).description))
                && days == (((Reminder) other).days); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, days);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDays(int days) {
        this.days = days;
    }

}

