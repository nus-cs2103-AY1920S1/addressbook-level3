package seedu.address.model.reminder;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Task's time in the calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskTime(String)}
 */
public class ReminderTime implements Comparable<ReminderTime> {
    public static final String MESSAGE_CONSTRAINTS =
            "ReminderTime should only contain a staring time and an ending time, "
                    + "with format: dd/MM/yyyy HH:mm, dd/MM/yyyy HH:mm, "
                    + "with the ending time after the starting time.";

    public final String fullTime;
    private final String starting;
    private final String ending;

    public ReminderTime(String reminderTime) {
        requireAllNonNull(reminderTime);
        String[] twoTime = reminderTime.split(", ");
        checkArgument(isValidReminderTime(reminderTime), MESSAGE_CONSTRAINTS);
        String startingTime = twoTime[0];
        String endingTime = twoTime[1];
        starting = startingTime;
        ending = endingTime;
        fullTime = starting + ", " + ending;
    }

    /**
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidReminderTime(String reminderTime) {
        Date dateStart;
        Date dateEnd;
        String[] twoTime = reminderTime.split(", ");
        String startingTime = twoTime[0];
        String endingTime = twoTime[1];
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            dateStart = simpleDateFormat.parse(startingTime);
            dateEnd = simpleDateFormat.parse(endingTime);
            if (dateStart.compareTo(dateEnd) >= 0
                    || !startingTime.equals(simpleDateFormat.format(dateStart))
                    || !endingTime.equals(simpleDateFormat.format(dateEnd))) {
                dateStart = null;
            }
        } catch (ParseException ex) {
            return false;
        }
        return dateStart != null;
    }

    public String getStartTime() {
        return starting;
    }
    /*
    public boolean hasTimeConflict(TaskTime otherTaskTime) throws ParseException {
        Date thisStarting = convertToDate(starting);
        Date thisEnding = convertToDate(ending);
        Date otherStarting = convertToDate(otherTaskTime.starting);
        Date otherEnding = convertToDate(otherTaskTime.ending);
        if (thisStarting.compareTo(otherEnding) >= 0 || thisEnding.compareTo(otherStarting) <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static Date convertToDate(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return simpleDateFormat.parse(time);
    }
    */
    @Override
    public String toString() {
        return "Starting:" + starting + " ,Ending:" + ending;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReminderTime // instanceof handles nulls
                && starting.equals(((ReminderTime) other).starting)
                && ending.equals(((ReminderTime) other).ending)); // state check
    }

    @Override
    public int compareTo(ReminderTime o) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date thisStarting = simpleDateFormat.parse(starting);
            Date otherStarting = simpleDateFormat.parse(o.starting);
            return thisStarting.compareTo(otherStarting);
        } catch (ParseException ex) {
            return 0;
        }
    }
}
